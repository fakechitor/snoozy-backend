# Регистрация

### _POST /api/v1/auth/register_

Request (application-json)
```
{
  "username" : "test_user",
  "email" : "aassadsadasdsa@gmail.com",
  "password" : "asdasd",
  "confirmPassword" : "asdasd"
}
``` 

Response (200 OK)

```
{
"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiZW1haWwiOiJhYXNzYWRzYWRhc2RzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTE4NTIwLCJleHAiOjE3NzYwNzg1MjB9.y-gWer5G8_WT8VKXFGBoB2m5XixlAC6GsoWmvBO4Hyw"
}
```

Errors
409 CONFLICT — пользователь уже существует


# Логин

### POST /api/v1/auth/login

Request (application-json)
```
{
"email": "user@example.com",
"password": "password123"
}
```

_Response (200 OK)_

```
{
"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiZW1haWwiOiJhYXNzYWRzYWRhc2RzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTE4NTIwLCJleHAiOjE3NzYwNzg1MjB9.y-gWer5G8_WT8VKXFGBoB2m5XixlAC6GsoWmvBO4Hyw"
}
```
_Errors_
401 UNAUTHORIZED — invalid credentials
