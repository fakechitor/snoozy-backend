# Регистрация

### POST `/api/v1/auth/basic/register`

#### Request (application-json)
```
{
  "username" : "test_user",
  "email" : "aassadsadasdsa@gmail.com",
  "password" : "asdasd",
  "confirmPassword" : "asdasd"
}
``` 

#### Response (200 OK)

```
{
"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiZW1haWwiOiJhYXNzYWRzYWRhc2RzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTE4NTIwLCJleHAiOjE3NzYwNzg1MjB9.y-gWer5G8_WT8VKXFGBoB2m5XixlAC6GsoWmvBO4Hyw"
}
```

Errors
409 CONFLICT — пользователь уже существует


# Логин

### POST `/api/v1/auth/basic/login`

#### Request (application-json)
```
{
"email": "user@example.com",
"password": "password123"
}
```

#### _Response (200 OK)_

```
{
"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiZW1haWwiOiJhYXNzYWRzYWRhc2RzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTE4NTIwLCJleHAiOjE3NzYwNzg1MjB9.y-gWer5G8_WT8VKXFGBoB2m5XixlAC6GsoWmvBO4Hyw"
}
```
_Errors_
401 UNAUTHORIZED — неверные данные


# Логин через Google

### POST `/api/v1/auth/google`

#### Request (application-json)
```
{
  "idToken" : "eyJhbGciOiJSUzI1NiIsImtpZCI6ImNjZTRlMDI0YTUxYWEwYzFjNDFjMWE0NTE1YTQxZGQ3ZTk2MTkzNmIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1NzIxMTczOTQwOS1nNGduaWVhdTA1Y211bGFtZDBoc25ka2YzczJ2N2JhaS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImF1ZCI6IjU3MjExNzM5NDA5LWc0Z25pZWF1MDVjbXVsYW1kMGhzbmRrZjNzMnY3YmFpLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA2NDM0MDY0OTE0NTg5NzQ4OTA5IiwiZW1haWwiOiJsYWRhc2VkYW5iYWtsYWphbjA2QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3NzUwNjUzODAsIm5hbWUiOiJEbWl0cnkgKHVzZXIg4oCiX-KAoikiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jTFF3aTBjOVJlbURDby1Od3YtQnNpaFlOZmdPSzJ3ZzVLUU9ZWlJtYWoySFNPRzVJeEg9czk2LWMiLCJnaXZlbl9uYW1lIjoiRG1pdHJ5IiwiaWF0IjoxNzc1MDY1NjgwLCJleHAiOjE3NzUwNjkyODAsImp0aSI6ImFlZGMyOTllNGI2NjkxOTNlYzA5ODViNDg4ZmFhZmFkM2RhZDUyMmEifQ.Wgvps0GSWrHbtHNkgPnXVPy_NFQCrA2Hp8TNSo4Xb0kL4jwKO68lvRXDQug1BHmfjNML5438aKOyBIeOugINn-YKeNgcvFAsXG0NV08HbqFzLx4HbInFnJgsVq6GUxzzfS8j6FlPIUtTTPUDTB6VHjzAhRf5zKFy3cwlnR4xmximEKUk165w2UJ7f5i7K-rqf3Zs7owIxRelG_ec0XiWAwKwKQda2X3WqyYdLUsQhfT3GOy3iJcgwq5_lJOth3niiXjYubwNdIGR2h8YJNxVP7BRliRmOMve_rh6e1P8nsCe-uYG9prlOLPGaNxwwywLNFSg4BDZKzMr3K7gZqpvwA"
}
```

#### _Response (200 OK)_

```
{
"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiZW1haWwiOiJhYXNzYWRzYWRhc2RzYUBnbWFpbC5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiYXV0aCI6IkJBU0lDIiwiaWF0IjoxNzc1MTE4NTIwLCJleHAiOjE3NzYwNzg1MjB9.y-gWer5G8_WT8VKXFGBoB2m5XixlAC6GsoWmvBO4Hyw"
}
```
_Errors_
401 UNAUTHORIZED — неверные данные
