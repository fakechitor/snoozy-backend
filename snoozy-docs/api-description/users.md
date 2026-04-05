# Как совершать запрос
#### Все эндпоинты требуют header `Authorization: Bearer <token>`
#### Например 
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJhYXNzYWRzYWRzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTI2NDE0LCJleHAiOjE3NzYwODY0MTR9.3FilXDth0Zc2TwzeQlgkAmLjSvoc4JEvEYsSkVce3us
```
#### Под token подразумевается JWT accessToken, который выдается при авторизации

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

#### Response (404 Not found)
```
{
  "message": "User doesn't exist"
}
```


# Получение информации о пользователе по номеру телефона
### GET `/api/v1/users/phone`

#### Request требует header Authorization

#### Request (application-json)
```
{
  "phoneNumber" : "79998881234"
}
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

#### Response (404 Not found)
```
{
  "message": "User doesn't exist"
}
```


# Загрузка аватарки пользователя
### GET `/api/v1/users/avatar`

#### Request требует header Authorization
#### Загрузка аватарки через MULTIPART_FORM
##### key = file
##### value = avatar.png

#### Response (200 OK)
```
{
  "url": "http://localhost:8080/api/v1/files/avatar?key=users/avatars/4/0e69bf86-f650-4785-92e2-123b6dfe4f61.png"
}
```