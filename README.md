# Teachgram Backend

A Spring Boot application that serves as the backend for Teachgram, a social media platform.

## Overview

Teachgram is a social media platform that allows users to:
- Create and manage user profiles
- Share posts with text, photos, and videos
- Connect with friends
- Interact with posts (likes)
- Control privacy settings for posts

## Technologies

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for database operations
- **PostgreSQL** database
- **Flyway** for database migrations
- **Lombok** for reducing boilerplate code
- **SpringDoc OpenAPI** for API documentation

## Project Structure

```
src/main/java/com/teach3035/teachgram_back/
├── config/
│   └── security/          # Security configurations
├── controller/            # REST API controllers
├── dto/                   # Data Transfer Objects
│   ├── req/               # Request DTOs
│   └── res/               # Response DTOs
├── exception/             # Exception handling
├── model/                 # Entity models
├── repository/            # Data repositories
├── service/               # Business logic
└── util/                  # Utility classes
```

## Features

- **User Management**: Registration, authentication, profile updates
- **Post Management**: Create, read, update, delete posts
- **Friendship System**: Connect with other users
- **Security**: JWT-based authentication and authorization
- **API Documentation**: OpenAPI/Swagger UI

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL database

### Environment Variables

Create a `.env` file in the `src/main/resources` directory with the following variables:

```
DB_URL=jdbc:postgresql://localhost:5432/teachgram
DB_USER=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret_key
JWT_ISSUER=teachgram
JWT_EXPIRATION=86400000
JWT_TOKEN_PREFIX=Bearer
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

### Building and Running

1. Clone the repository
2. Set up the environment variables
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start on the default port 8080.

## API Documentation

Once the application is running, you can access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Database Migrations

The application uses Flyway for database migrations. Migration scripts are located in:
```
src/main/resources/db/migration/V0/
```