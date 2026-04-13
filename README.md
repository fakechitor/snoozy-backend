# Описание проекта
Бэкенд для приложения Snoozy.

Стек:
- Java
- Spring
- PostgreSQL
- Apache Kafka
- Minio
- Docker

Необходимый для запуска .env файл:
```
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_DB=
POSTGRES_URL=
MINIO_ROOT_USER=
MINIO_ROOT_PASSWORD=
MINIO_URL=
OAUTH_WEB_CLIENT=
KAFKA_BOOTSTRAP_SERVERS=
JWT_SECRET_TOKEN=
AUTH_URI=
USER_URI=
GROUP_URI=
FILE_URI=
ALARM_URI=
BASE_URI=
```
# API документация:
### [Auth docs](https://github.com/fakechitor/snoozy-backend/blob/master/snoozy-docs/api-description/authorization.md)
***
### [User docs](https://github.com/fakechitor/snoozy-backend/blob/master/snoozy-docs/api-description/users.md)
***
### [Group docs](https://github.com/fakechitor/snoozy-backend/blob/master/snoozy-docs/api-description/groups.md)
***
### [Group alarm docs](https://github.com/fakechitor/snoozy-backend/blob/groupAction/snoozy-docs/api-description/groupInteraction.md)
