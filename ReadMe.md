## Overview
A Spring Boot API for managing translations, supporting multiple locales (en, fr, es), context tags (mobile, desktop), full CRUD, search, and JSON export.

## Features
- Token-based (JWT) authentication
- PSR-12-like code style and SOLID design
- Optimized queries with indexes
- Bulk data loader (100k records) added upon running
- Docker & Docker Compose
- Swagger UI: `/swagger-ui.html`
- Test coverage
- CDN support (Using docker)

## Setup
1. **Build**: `mvn clean package`
2. **Run locally**: `java -jar target/*.jar`
3. **Docker**: `docker-compose up --build`
4. **Swagger**: http://localhost:8080/swagger-ui.html

## Endpoints
- `POST /auth/login` – get JWT
- CRUD `/api/translations`
- `GET /api/translations/export` – JSON export

## Design Notes
- **Entities** normalized for index performance
- **Service** layer transforms to/from DTOs
- **Security** via `JwtFilter` & stateless sessions
- **DataLoader** for scalability tests Add 100k records  when application boots up
- For testing the Export Endpoint kindly use postman or other sources since Swagger does not support huge data.
- For now H2 InMemory DB (Due to Less Time) is utilized But any other SQL DB preferably PostgreSQL Should be Used.

---
```