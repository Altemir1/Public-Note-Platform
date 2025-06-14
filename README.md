# Public Note Platform

## Overview
A distributed web application for managing public notes, leveraging Spring Boot, Redis, and HAProxy for high availability.

## Technologies
- **Backend**: Java 17, Spring Boot
- **Cache**: Redis
- **Load Balancer**: HAProxy
- **Containerization**: Docker
- **Build Tool**: Maven

## Architecture
```
[Client] → [HAProxy] → [Spring Boot Apps] ↔ [Redis]
```

## Prerequisites
- Docker & Docker Compose
- Java 17
- Maven 3.8+
- Git

## Quick Start
```bash
# Clone repository
git clone <repository-url>
cd cloud_computing_project_2025_summer

# Build application
mvn clean package

# Start services
docker-compose up -d
```

## Project Structure
```
project/
├── docker/
│   ├── app/
│   │   └── Dockerfile
│   └── redis/
│       ├── Dockerfile
│       └── redis.conf
├── src/
├── haproxy.cfg
├── docker-compose.yml
└── README.md
```

## Configuration

### HAProxy
- Frontend Port: 80
- Stats Dashboard: http://localhost:8404
- Admin Credentials: admin/admin

### Redis
- Port: 6379
- Persistence: enabled
- Max Memory: 2GB

### Application
- Port: 8080
- Spring Profile: prod

## Development

### Build Commands
```bash
# Build app image
docker build -t app-image ./docker/app

# Build redis image
docker build -t redis-image ./docker/redis
```

### Testing
```bash
mvn test
```

### Endpoints
- Main application: `http://localhost:80`
- HAProxy stats: `http://localhost:8404`

## Monitoring
Monitor application health and performance through HAProxy statistics dashboard:
- URL: `http://localhost:8404`
- Username: `admin`
- Password: `admin`

## Contributing
1. Fork repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Submit pull request
