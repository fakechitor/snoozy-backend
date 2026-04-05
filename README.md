# Project description
Backend part of Snoozy app.

Stack:
- Java
- Spring
- PostgreSQL
- Apache Kafka
- Minio
- Docker

Required .env file:
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
# Project API documentation:
### [Auth docs](https://github.com/fakechitor/snoozy-backend/blob/master/snoozy-docs/api-description/authorization.md)
***
### [User docs](https://github.com/fakechitor/snoozy-backend/blob/master/snoozy-docs/api-description/users.md)
***
### [Group docs](https://github.com/fakechitor/snoozy-backend/blob/master/snoozy-docs/api-description/groups.md)
***
### [Group alarm docs](https://github.com/fakechitor/snoozy-backend/blob/groupAction/snoozy-docs/api-description/groupInteraction.md)
