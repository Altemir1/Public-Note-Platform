# Public Note Platform – Cloud Computing Project 2025 Summer

## Overview
This is a distributed web application for managing public notes, developed as part of a cloud computing course project. The backend is built with Java Spring Boot, Redis is used as a cache/database, and HAProxy acts as a load balancer. The system is containerized with Docker, orchestrated via Docker Compose for local testing and Kubernetes (Minikube) for deployment. All services run on custom-built images based on the OpenSUSE Leap Linux distribution.


## Technologies Used
- Java 17, Spring Boot
- Redis (custom-configured)
- HAProxy (HTTP load balancing)
- Docker & Docker Compose
- Kubernetes with Minikube
- OpenSUSE Leap (base image)
- Maven

## Prerequisites
- Docker & Docker Compose
- Java 17 & Maven
- Minikube & kubectl
- Git

## Building Docker Images
1. **Build the Spring Boot app image:**
    ```sh
    cd docker/app
    docker build -t cloud_computing_project_2025_summer-app .
    ```
2. **Build the Redis image:**
    ```sh
    cd ../redis
    docker build -t redis-image .
    ```

## Running Locally with Docker Compose
```sh
cd ../../
docker-compose up --build
```

### Access Points
- HAProxy load balancer: [http://localhost:80](http://localhost:80)
- HAProxy dashboard: http://localhost:8404
- Redis: internal only (`redis:6379`), not exposed to host

## Architecture
- **app1** and **app2**: Two Spring Boot containers behind HAProxy
- **redis**: Single Redis instance, used by both apps
- **haproxy**: Balances HTTP traffic across both app instances

## Deploying with Kubernetes (Minikube)
1. **Start Minikube and load custom images:**
    ```sh
    minikube start
    minikube image load cloud_computing_project_2025_summer-app
    minikube image load redis-image
    minikube image load haproxy-image
    ```
2. **Apply Kubernetes manifests:**
    ```sh
    kubectl apply -f k8s/
    ```
    This will:
    - Create a namespace `public-notes`
    - Deploy:
        - Redis with persistent storage (`redis-pvc`)
        - Spring Boot app with 2 replicas (`note-app`)
        - Expose `note-app` via NodePort
3. **Access the Spring Boot application:**
    ```sh
    minikube service note-app -n public-notes
    ```
4. **(Optional) Access Redis for testing:**
    ```sh
    kubectl port-forward service/redis 6379:6379 -n public-notes
    ```

## Notes
- All containers are built using OpenSUSE Leap base images.
- app1 and app2 are identical deployments for horizontal scaling.
- Redis is isolated on the backend network (or within Kubernetes cluster).
- HAProxy listens on port 80 and optionally 8404 for stats (if configured).
- Kubernetes uses `imagePullPolicy: Never` to run local images loaded into Minikube.

## Summary
| Component   | Description                                 |
|-------------|---------------------------------------------|
| app1, app2  | Java Spring Boot note app replicas          |
| redis       | Redis with custom config & persistent volume|
| haproxy     | Load balances requests to app1 and app2     |
| Minikube    | Local Kubernetes cluster for deployment     |
