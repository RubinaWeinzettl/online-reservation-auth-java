# Auth Service (Java)

This service is part of the **Online Reservation** project and is responsible for **authentication and authorization**.

It is implemented using a **Java / Jakarta EE stack** and designed as a standalone service within a service-oriented architecture.

---

## Purpose

The Auth Service handles:

- user authentication (login)
- password verification (hashed passwords)
- issuing **JWT tokens**
- role-based access control (**RBAC**)
- providing a foundation for secure communication between services

---

## Tech Stack

- Java 21
- Jakarta EE (JAX-RS, CDI)
- JPA / Hibernate
- Maven
- WildFly
- PostgreSQL
- Docker

---

## Architecture (high-level)

- exposes a **REST API** for authentication
- separates concerns into:
  - API layer (JAX-RS endpoints)
  - service layer (business logic)
  - persistence layer (JPA entities & repositories)
- uses **JWT** for stateless authentication
- integrates with a relational database for user management

---

## Features (MVP)

- login with username and password
- password hashing (e.g. BCrypt)
- JWT token generation
- basic role model (e.g. `USER`, `ADMIN`)
- protected endpoints (token required)

---

## Planned Extensions

- refresh tokens
- OAuth / OIDC integration
- user registration
- password reset
- fine-grained role & permission system

---

## Project Structure (planned)

```text
auth-service-java/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ... (packages)
│   │   │       ├── api/        # REST endpoints
│   │   │       ├── service/    # business logic
│   │   │       ├── model/      # entities
│   │   │       ├── repository/ # data access
│   │   │       └── security/   # JWT, auth logic
│   │   │
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml
│
├── pom.xml
└── Dockerfile
```

## Getting Started (planned)

### Prerequisites

- Java 21
- Maven
- Docker
- PostgreSQL

---

### Run locally (planned)

1. Build the project:
    mvn clean package

2. Start with Docker (later via docker compose)

3. Deploy to WildFly

---

## API (example)

### Login

POST /auth/login

Request:
{
  "username": "user",
  "password": "password"
}

Response:
{
  "access_token": "jwt-token",
  "token_type": "Bearer"
}

---

## Development Notes

- The project is built incrementally
- focus is on:
  - clean architecture
  - clear separation of concerns
  - realistic DevOps setup
- not all features are implemented yet

---

## CI/CD (planned)

- Jenkins pipeline with:
  - mvn test
  - mvn package
  - Docker image build
- later integration into Kubernetes deployment

---

## Related Repositories

- Overview:  
  https://github.com/RubinaWeinzettl/online-reservation-overview

- Web Frontend:  
  https://github.com/RubinaWeinzettl/online-reservation-web

---

## Status

🚧 Work in progress – currently being rebuilt with Java / Jakarta EE
