# Public Note Platform – Cloud Computing Project

## Overview

This project is a distributed web application for managing public notes. It uses Java Spring Boot for the backend, Redis for caching, and HAProxy for load balancing. The infrastructure is containerized using Docker and orchestrated with Docker Compose and Kubernetes (Minikube). All images are custom-built using OpenSUSE Leap as the base.

---

## Project Structure

```
cloud_computing_project_2025_summer/
├── docker/
│   ├── app/
│   │   └── Dockerfile
│   └── redis/
│       ├── Dockerfile
│       └── redis.conf
├── docker-compose.yml
├── haproxy.cfg
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── pom.xml
└── README.md
```

---

## Technologies Used

- Java 17, Spring Boot
- Redis
- HAProxy
- Docker & Docker Compose
- Kubernetes (Minikube)
- OpenSUSE Leap (base image)
- Maven

---

## Prerequisites

- Docker & Docker Compose
- Java 17 & Maven
- Minikube & kubectl (for Kubernetes)
- Git

---

## Building Docker Images

```bash
# Build the app image
cd docker/app
docker build -t app-image:opensuse .

# Build the redis image
cd ../redis
docker build -t redis-image:opensuse .
```

---

## Running with Docker Compose

```bash
cd ../../
docker-compose up --build
```

- The HAProxy load balancer will be available on `http://localhost:80`
- Redis will be available on `localhost:6379` (only accessible from your machine)
- Two instances of the Spring Boot app will be started and load-balanced

---

## Docker Compose Architecture

- **haproxy**: Load balancer, exposed on all interfaces (port 80)
- **app1/app2**: Two Spring Boot web servers, connected to both frontend and backend networks
- **redis**: Database/cache, only accessible on the backend network and from localhost

---

## Running with Kubernetes (Minikube)

1. **Load your custom images into Minikube:**
    ```bash
    minikube start
    minikube image load app-image:opensuse
    minikube image load redis-image:opensuse
    minikube image load haproxy-image:opensuse
    ```

2. **Apply Kubernetes manifests:**
    ```bash
    kubectl apply -f k8s/
    ```

3. **Access the services:**
    ```bash
    minikube service haproxy-service
    # For Redis (from your laptop):
    kubectl port-forward service/redis-service 6379:6379
    ```

---

## Notes

- All Docker images are built from the provided Dockerfiles using OpenSUSE Leap.
- The backend network isolates Redis from the load balancer.
- The database port is only bound to localhost for security.
- For Kubernetes, `imagePullPolicy: Never` is used to ensure local images are used.

---
