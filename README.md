# Spring Boot Internship Assignment

## ✅ Overview
This is a RESTful Spring Boot application implementing:
- User Authentication (JWT + BCrypt)
- CRUD on Countries
- External API integration (REST Countries API)
- Postgresql  database
- Docker & Docker Compose
- Swagger for API docs
- Postman for testing

---

## 📦 Technologies
- Java 17
- Spring Boot
- Spring Security
- JWT
- Postgresql
- Docker
- Swagger (OpenAPI)
- Postman
- Maven

---

## 🚀 Run the Project

### Prerequisites:
- Docker + Docker Compose
- Java 17
- Maven

### 🔧 Steps:
```bash
# Step 1: Build the project
mvn clean install

# Step 2: Start containers (Spring Boot + Postgresql)
docker-compose up --build
