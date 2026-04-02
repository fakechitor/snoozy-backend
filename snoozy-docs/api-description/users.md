# Как совершать запрос
#### Все эндпоинты требуют header `Authorization: Bearer <token>`
#### Например 
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJhYXNzYWRzYWRzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTI2NDE0LCJleHAiOjE3NzYwODY0MTR9.3FilXDth0Zc2TwzeQlgkAmLjSvoc4JEvEYsSkVce3us
```
#### Под token подразумевается JWT accessToken, который выдается при авторизации


# Получение аватарки
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
  "username": "test_user",
  "email": "aassadsadsa@gmail.com",
  "phoneNumber": "79998881234"
}
```