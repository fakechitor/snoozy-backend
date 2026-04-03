# Как совершать запрос
#### Все эндпоинты требуют header `Authorization: Bearer <token>`
#### Например 
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJhYXNzYWRzYWRzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTI2NDE0LCJleHAiOjE3NzYwODY0MTR9.3FilXDth0Zc2TwzeQlgkAmLjSvoc4JEvEYsSkVce3us
```
#### Под token подразумевается JWT accessToken, который выдается при авторизации


# Получение аватарки (DEPRECATED)
!!! Аватарка доступна по ссылке в запросе /api/v1/users/me 
### GET `/api/v1/users/avatar`

#### Request требует header Authorization
```
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
http://localhost:8080/api/v1/users/avatar
```

#### Response (200 OK)
Возвращает аватарку в base64 формате


# Получение информации о пользователе
### GET `/api/v1/users/me`

#### Request требует header Authorization
```
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
http://localhost:8080/api/v1/users/me
```

#### Response (200 OK)
```
{
  "username": "test_user1233",
  "email": null,
  "phoneNumber": "7123123123",
  "avatarLink": "https://sun9-18.userapi.com/s/v1/ig2/WssxJmCQhqXVZBBGTcLaQTxGphlPs846gAOjFcwq5A6aXYGe5lcXFA6sCSGiZswZTQW0BKcy6tOR-OmhWog1yc8T.jpg?quality=95&as=32x32,48x48,72x72,96x96&from=bu&u=ceXmspzLIAkkdVKk0ZHoLYQ53jdGBwYgy8DpZH6IRDg&cs=96x0"
}
```
