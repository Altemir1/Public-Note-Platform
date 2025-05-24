# PublicNotes

A Spring Boot web application for public note-taking with Redis integration.

## Features

- Create and share public notes
- Tag notes for easy categorization
- Search notes by tags
- View notes by specific users
- User authentication
- Responsive design with Bootstrap

## Technologies

- Java 17+
- Spring Boot
- Spring Web
- Redis (Lettuce)
- Thymeleaf
- Bootstrap 5
- Bootstrap Icons

## Prerequisites

- Java 17 or higher
- Redis server running on localhost:6379
- Maven

## Running the Application

1. Make sure Redis is running:
```bash
redis-cli ping
```

2. Build and run the application:
```bash
mvn spring-boot:run
```

3. Access the application at http://localhost:8080

## Project Structure

- `src/main/java/com/example/publicnotes/`
  - `controller/` - Web controllers
  - `model/` - Data models
  - `service/` - Business logic
  - `config/` - Configuration classes
- `src/main/resources/`
  - `templates/` - Thymeleaf templates
  - `application.properties` - Application configuration

## Features

### User Management
- User registration
- User authentication
- Session management

### Note Management
- Create new notes
- Add tags to notes
- View notes in a grid layout
- Delete own notes

### Search and Discovery
- Search notes by tags
- View notes by specific users
- Responsive grid layout for search results
