# Взаимодействие в группе

## Содержание

- [Модель данных](#модель-данных)
- [Эндпоинты](#эндпоинты)
- [Права доступа](#права-доступа)
- [Действия друга над чужим будильником](#действия-друга-над-чужим-будильником)
- [История входящих действий](#история-входящих-действий)
- [Сценарий использования](#сценарий-использования)
- [Коды ответов](#коды-ответов)
- [Локальная разработка](#локальная-разработка)

---

## Модель данных

### `Alarm` — Будильник пользователя

| Поле | Тип | Описание |
|---|---|---|
| `id` | Long | ID будильника |
| `ownerId` | Long | ID владельца |
| `title` | String | Название будильника |
| `alarmTime` | DateTime | Дата и время срабатывания |
| `enabled` | Boolean | Включён ли будильник |
| `repeatPattern` | Enum | Режим повтора |
| `soundName` | String | Название звука |
| `difficultyLevel` | Int | Уровень сложности выключения |

**`repeatPattern`** — допустимые значения:

| Значение | Описание |
|---|---|
| `ONCE` | Однократно |
| `DAILY` | Каждый день |
| `WEEKDAYS` | По будням |
| `WEEKENDS` | По выходным |

---

### `AlarmPermission` — Право другого пользователя взаимодействовать с будильниками

| Поле | Тип | Описание |
|---|---|---|
| `id` | Long | ID записи |
| `ownerId` | Long | ID владельца |
| `targetUserId` | Long | ID пользователя, которому выдано право |
| `permissionType` | Enum | Тип права |
| `active` | Boolean | Активно ли право |
| `createdAt` | DateTime | Дата выдачи |

**`permissionType`** — допустимые значения:

| Значение | Описание |
|---|---|
| `TRIGGER` | Удалённо запустить будильник |
| `ENABLE_DISABLE` | Включать/выключать будильник |
| `CREATE_ALARM` | Создавать будильники |
| `UPDATE_ALARM` | Обновлять будильники |

---

### `AlarmAction` — История действий над будильником

| Поле | Тип | Описание |
|---|---|---|
| `id` | Long | ID записи |
| `alarmId` | Long | ID будильника |
| `actorUserId` | Long | Кто выполнил действие |
| `targetUserId` | Long | Над чьим будильником |
| `actionType` | Enum | Тип действия |
| `status` | Enum | Статус выполнения |
| `executedAt` | DateTime | Время выполнения |
| `messageText` | String | Сообщение (опционально) |

**`actionType`** — допустимые значения: `TRIGGER_NOW`, `ENABLE`, `DISABLE`

**`status`** — допустимые значения: `CREATED`, `EXECUTED`, `REJECTED`, `FAILED`

---

## Эндпоинты

### `GET /api/v1/alarms/health` — Проверка состояния сервиса

Проверяет, что сервис запущен.

Request требует header Authorization

**Ответ `200 OK`**
```json
{
  "status": "ok",
  "module": "snoozy-alarm"
}
```

---

### `GET /api/v1/alarms` — Получить свои будильники

Возвращает список будильников текущего пользователя.

Request требует header Authorization

**Ответ `200 OK`**
```json
[
  {
    "id": 1,
    "ownerId": 1,
    "title": "Подъем",
    "alarmTime": "2026-04-05T07:30:00",
    "enabled": true,
    "repeatPattern": "DAILY",
    "soundName": "classic",
    "difficultyLevel": 2
  }
]
```

---

### `POST /api/v1/alarms` — Создать будильник

Создаёт новый будильник текущего пользователя.

Request требует header Authorization

**Тело запроса**
```json
{
  "title": "Подъем",
  "alarmTime": "2026-04-05T07:30:00",
  "enabled": true,
  "repeatPattern": "DAILY",
  "soundName": "classic",
  "difficultyLevel": 2
}
```

> **Обязательные поля:** `title`, `alarmTime`

**Ответ `201 Created`**
```json
{
  "id": 1,
  "ownerId": 1,
  "title": "Подъем",
  "alarmTime": "2026-04-05T07:30:00",
  "enabled": true,
  "repeatPattern": "DAILY",
  "soundName": "classic",
  "difficultyLevel": 2
}
```

**Ошибки**

| Код | Описание |
|---|---|
| `400 Bad Request` | Не передан `title` или `alarmTime` |

---

### `PATCH /api/v1/alarms/{alarmId}` — Обновить будильник

Обновляет будильник текущего пользователя. Можно передавать только те поля, которые нужно изменить.

Request требует header Authorization

**Тело запроса**
```json
{
  "title": "Подъем на работу",
  "enabled": false
}
```

**Ответ `200 OK`**
```json
{
  "id": 1,
  "ownerId": 1,
  "title": "Подъем на работу",
  "alarmTime": "2026-04-05T07:30:00",
  "enabled": false,
  "repeatPattern": "DAILY",
  "soundName": "classic",
  "difficultyLevel": 2
}
```

**Ошибки**

| Код | Описание |
|---|---|
| `404 Not Found` | Будильник не найден |
| `403 Forbidden` | Будильник не принадлежит текущему пользователю |

---

### `DELETE /api/v1/alarms/{alarmId}` — Удалить будильник

Удаляет будильник текущего пользователя.

Request требует header Authorization

**Ответ `204 No Content`**

**Ошибки**

| Код | Описание |
|---|---|
| `404 Not Found` | Будильник не найден |
| `403 Forbidden` | Нет прав на удаление |

---

## Права доступа

### `POST /api/v1/alarms/permissions` — Выдать право пользователю

Выдаёт другому пользователю право взаимодействовать с будильниками владельца.

Request требует header Authorization

**Тело запроса**
```json
{
  "targetUserId": 2,
  "permissionType": "TRIGGER"
}
```

**Ответ `201 Created`**
```json
{
  "id": 1,
  "ownerId": 1,
  "targetUserId": 2,
  "permissionType": "TRIGGER",
  "active": true,
  "createdAt": "2026-04-04T20:00:00"
}
```

**Ошибки**

| Код | Описание |
|---|---|
| `400 Bad Request` | Не передан `targetUserId` или `permissionType` |

---

### `GET /api/v1/alarms/permissions` — Получить список выданных прав

Возвращает права, которые текущий пользователь выдал другим.

Request требует header Authorization

**Ответ `200 OK`**
```json
[
  {
    "id": 1,
    "ownerId": 1,
    "targetUserId": 2,
    "permissionType": "TRIGGER",
    "active": true,
    "createdAt": "2026-04-04T20:00:00"
  }
]
```

---

## Действия друга над чужим будильником

### `POST /api/v1/alarms/{alarmId}/trigger` — Удалённо запустить будильник

Позволяет пользователю с правом `TRIGGER` удалённо запустить будильник владельца.

Request требует header Authorization

**Тело запроса**
```json
{
  "messageText": "Вставай, опоздаешь"
}
```

**Ответ `200 OK`**
```json
{
  "id": 1,
  "alarmId": 1,
  "actorUserId": 2,
  "targetUserId": 1,
  "actionType": "TRIGGER_NOW",
  "status": "EXECUTED",
  "executedAt": "2026-04-04T20:10:00",
  "messageText": "Вставай, опоздаешь"
}
```

**Ошибки**

| Код | Описание |
|---|---|
| `404 Not Found` | Будильник не найден |
| `403 Forbidden` | Право `TRIGGER` не выдано |

---

### `POST /api/v1/alarms/{alarmId}/enable` — Включить чужой будильник

Требует право `ENABLE_DISABLE`.
Request требует header Authorization

**Ответ `200 OK`**
```json
{
  "id": 2,
  "alarmId": 1,
  "actorUserId": 2,
  "targetUserId": 1,
  "actionType": "ENABLE",
  "status": "EXECUTED",
  "executedAt": "2026-04-04T20:12:00",
  "messageText": null
}
```

**Ошибки**

| Код | Описание |
|---|---|
| `404 Not Found` | Будильник не найден |
| `403 Forbidden` | Право `ENABLE_DISABLE` не выдано |

---

### `POST /api/v1/alarms/{alarmId}/disable` — Выключить чужой будильник

Требует право `ENABLE_DISABLE`.
Request требует header Authorization

**Ответ `200 OK`**
```json
{
  "id": 3,
  "alarmId": 1,
  "actorUserId": 2,
  "targetUserId": 1,
  "actionType": "DISABLE",
  "status": "EXECUTED",
  "executedAt": "2026-04-04T20:13:00",
  "messageText": null
}
```

**Ошибки**

| Код | Описание |
|---|---|
| `404 Not Found` | Будильник не найден |
| `403 Forbidden` | Право `ENABLE_DISABLE` не выдано |

---

## История входящих действий

### `GET /api/v1/alarms/actions/incoming` — История действий над своими будильниками

Возвращает историю действий, совершённых над будильниками текущего пользователя.

Request требует header Authorization

**Ответ `200 OK`**
```json
[
  {
    "id": 1,
    "alarmId": 1,
    "actorUserId": 2,
    "targetUserId": 1,
    "actionType": "TRIGGER_NOW",
    "status": "EXECUTED",
    "executedAt": "2026-04-04T20:10:00",
    "messageText": "Вставай, опоздаешь"
  }
]
```

---

## Сценарий использования

### 👥 Друг будит пользователя

**Шаг 1.** Пользователь 1 создаёт будильник:
```http
POST /api/v1/alarms
```
```json
{
  "title": "Подъем",
  "alarmTime": "2026-04-05T07:30:00"
}
```

**Шаг 2.** Пользователь 1 выдаёт пользователю 2 право `TRIGGER`:
```http
POST /api/v1/alarms/permissions
```
```json
{
  "targetUserId": 2,
  "permissionType": "TRIGGER"
}
```

**Шаг 3.** Пользователь 2 удалённо запускает будильник:
```http
POST /api/v1/alarms/1/trigger
```
```json
{
  "messageText": "Вставай"
}
```

**Шаг 4.** Пользователь 1 проверяет историю действий:
```http
GET /api/v1/alarms/actions/incoming
```

---

## Коды ответов

| Код | Описание |
|---|---|
| `200 OK` | Успешный запрос |
| `201 Created` | Успешное создание |
| `204 No Content` | Успешное удаление |
| `400 Bad Request` | Невалидный запрос |
| `401 Unauthorized` | Нет текущего пользователя |
| `403 Forbidden` | Нет прав на действие |
| `404 Not Found` | Сущность не найдена |
| `500 Internal Server Error` | Внутренняя ошибка сервера |

---

## Локальная разработка

Для локальной проверки можно временно использовать заглушку текущего пользователя:

```java
public Long getCurrentUserId() {
    return 1L;
}
```

Для проверки сценария с другом:

1. Установить `1L` — выполнить действия от имени владельца
2. Сменить на `2L` — выполнить `trigger`
3. Вернуть `1L` — проверить `GET /actions/incoming`
