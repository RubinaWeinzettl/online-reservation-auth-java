# Auth Service (Java)

This service is part of the **Online Reservation** project and is responsible for **authentication and user access management**.

It is implemented as a standalone Java service using **Jakarta EE / Servlets**, **Tomcat**, and **MariaDB**.

---

## Current Status

✅ **v0.1-auth-service** released as pre-release

Implemented:

- user registration
- user login
- BCrypt password hashing
- MySQL persistence
- Docker Compose local development setup

---

## Purpose

The Auth Service provides the authentication foundation for the platform.

It is responsible for:

- creating user accounts
- verifying credentials
- secure password storage
- future token-based authentication
- future authorization concepts (roles / permissions)

---

## Tech Stack

- Java 17
- Jakarta EE (Servlet API)
- Apache Tomcat 10
- Maven
- MySQL
- JDBC
- BCrypt
- Docker
- Docker Compose
- phpMyAdmin (local development)

---

## Architecture

Current structure:

- **Servlet layer** → HTTP endpoints
- **Service layer** → business logic
- **Repository layer** → database access
- **DTO layer** → request / response models
- **Model layer** → domain objects

This service is built incrementally with focus on clean separation of concerns.

---

## Implemented Endpoints

### Register

POST /auth-service/register

Creates a new user account.

### Login

POST /auth-service/auth/login

Validates user credentials.

---

## Example Requests

### Register

curl -i -X POST http://localhost:8081/auth-service/register \
-H "Content-Type: application/json" \
-d '{
  "email": "test@example.com",
  "password": "test123"
}'

### Login

curl -i -X POST http://localhost:8081/auth-service/auth/login \
-H "Content-Type: application/json" \
-d '{
  "email": "test@example.com",
  "password": "test123"
}'

---

## Planned Features

### Authentication

- JWT token generation after login
- token validation
- refresh tokens
- logout concept

### Authorization

- roles / permissions
- RBAC extension

### Account Security

- password reset
- email verification
- stronger request validation
- rate limiting

### Quality

- unit tests
- integration tests

### Future Expansion

- OAuth / OpenID Connect integration

---

## Local Development

docker compose up -d --build

docker compose down

---

## Related Repositories

### Overview

https://github.com/RubinaWeinzettl/online-reservation-overview

### Web Frontend

https://github.com/RubinaWeinzettl/online-reservation-web

---

## Development Philosophy

This project is intentionally built step by step.

Focus areas:

- learning realistic backend architecture
- clean code structure
- containerized development
- incremental delivery
- practical DevOps workflows

---

## Status

🚧 Active development  
Current milestone: **Authentication MVP completed**
