# Trading System - Spring Boot CRUD Application

A comprehensive Spring Boot CRUD application for managing trading products with complete Azure deployment configuration including Docker containerization and Kubernetes orchestration.

## Overview

The Trading System application provides a RESTful API for managing trading products. It's built with Spring Boot and designed for cloud-native deployment on Azure AKS (Azure Kubernetes Service) with support for local development using Docker Compose.

### Features

- **Complete CRUD Operations**: Create, Read, Update, and Delete trading products
- **Advanced Filtering**: Filter products by status, category, or search by name
- **Database Persistence**: PostgreSQL for production, H2 for development
- **Health Checks**: Kubernetes-ready liveness and readiness probes
- **Auto-scaling**: Horizontal Pod Autoscaler for dynamic scaling
- **Security**: RBAC, Network Policies, and secrets management
- **Monitoring**: Actuator endpoints for metrics and health monitoring
- **Multi-stage Docker Build**: Optimized container images with minimal size
- **High Availability**: Multi-replica deployments with pod anti-affinity

## Project Structure

```
trade-app/
├── src/
│   ├── main/
│   │   ├── java/com/trading/
│   │   │   ├── TradeAppApplication.java      # Main application class
│   │   │   ├── controller/
│   │   │   │   └── TradingProductController.java
│   │   │   ├── service/
│   │   │   │   └── TradingProductService.java
│   │   │   ├── entity/
│   │   │   │   └── TradingProduct.java
│   │   │   ├── repository/
│   │   │   │   └── TradingProductRepository.java
│   │   │   ├── dto/
│   │   │   │   └── TradingProductDTO.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── ErrorResponse.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── k8s/                                     # Kubernetes manifests
│   ├── namespace.yaml
│   ├── configmap.yaml
│   ├── secret.yaml
│   ├── postgres-statefulset.yaml
│   ├── trade-app-deployment.yaml
│   ├── trade-app-service.yaml
│   ├── hpa.yaml
│   ├── rbac.yaml
│   └── network-policy.yaml
├── scripts/                                 # Deployment scripts
│   ├── deploy-to-aks.sh
│   ├── deploy-to-k8s.sh
│   ├── deploy-local.sh
│   └── cleanup.sh
├── Dockerfile                               # Multi-stage Docker build
├── docker-compose.yml                       # Local development setup
└── pom.xml                                  # Maven configuration

```

## Prerequisites

### For Local Development
- Java 17 or higher
- Maven 3.8 or higher
- Docker and Docker Compose
- Git

### For Azure Deployment
- Azure CLI
- kubectl
- Helm (optional, for advanced deployments)
- An active Azure subscription

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/gurrinders/cloud-deployments.git
cd cloud-deployments/trade-app
```

### 2. Build the Application

```bash
mvn clean package
```

### 3. Make Scripts Executable

```bash
chmod +x scripts/*.sh
```

## Deployment Options

### Option 1: Local Development with Docker Compose

Perfect for local development and testing.

```bash
./scripts/deploy-local.sh
```

This will:
- Build the Docker image
- Start the Spring Boot application and PostgreSQL database
- Make the application available at `http://localhost:8080`

**Useful Commands:**
```bash
# View logs
docker-compose logs -f trade-app

# Stop services
docker-compose down

# Remove volumes
docker-compose down -v
```

### Option 2: Deploy to Azure AKS

For production deployment on Azure Kubernetes Service.

**Step 1: Configure Azure Variables**
```bash
export RESOURCE_GROUP="trading-system-rg"
export CLUSTER_NAME="trading-system-aks"
export REGION="eastus"
export ACR_NAME="tradingacr$(date +%s)"
```

**Step 2: Run Deployment Script**
```bash
./scripts/deploy-to-aks.sh
```

This will:
- Create an Azure Resource Group
- Create an Azure Container Registry (ACR)
- Create an AKS cluster with 3 nodes (auto-scaling 2-5)
- Build and push the Docker image to ACR
- Configure kubectl to connect to the cluster

**Step 3: Deploy Kubernetes Resources**
```bash
# Update the image in k8s/trade-app-deployment.yaml with your ACR URL
sed -i "s|gurrinders/trade-app|${ACR_NAME}.azurecr.io/trade-app|g" k8s/trade-app-deployment.yaml

# Deploy to Kubernetes
./scripts/deploy-to-k8s.sh
```

**Step 4: Access the Application**
```bash
# Get the external IP
kubectl get svc -n trading-system

# Port forward for local testing
kubectl port-forward svc/trade-app-service 8080:80 -n trading-system
```

### Option 3: Manual Kubernetes Deployment

If you already have a Kubernetes cluster configured:

```bash
# Create namespace and deploy
kubectl create namespace trading-system
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/rbac.yaml
kubectl apply -f k8s/network-policy.yaml
kubectl apply -f k8s/postgres-statefulset.yaml
kubectl apply -f k8s/trade-app-deployment.yaml
kubectl apply -f k8s/trade-app-service.yaml
kubectl apply -f k8s/hpa.yaml
```

## API Endpoints

### Product Management

#### Create a Product
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Premium Trading Stock",
    "description": "High-value trading instrument",
    "category": "STOCKS",
    "price": 150.50,
    "quantity": 100
  }'
```

#### Get All Products
```bash
curl http://localhost:8080/api/v1/products
```

#### Get Product by ID
```bash
curl http://localhost:8080/api/v1/products/1
```

#### Update Product
```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Updated Trading Stock",
    "description": "Updated description",
    "category": "STOCKS",
    "price": 160.75,
    "quantity": 150
  }'
```

#### Delete Product
```bash
curl -X DELETE http://localhost:8080/api/v1/products/1
```

#### Filter by Status
```bash
curl http://localhost:8080/api/v1/products/status/ACTIVE
```

#### Filter by Category
```bash
curl http://localhost:8080/api/v1/products/category/STOCKS
```

#### Search by Product Name
```bash
curl "http://localhost:8080/api/v1/products/search?name=Trading"
```

### Health and Monitoring

#### Health Check
```bash
curl http://localhost:8080/actuator/health
```

#### Application Metrics
```bash
curl http://localhost:8080/actuator/metrics
```

#### Liveness Probe
```bash
curl http://localhost:8080/actuator/health/liveness
```

#### Readiness Probe
```bash
curl http://localhost:8080/actuator/health/readiness
```

## Database Configuration

### Development (H2)
- URL: jdbc:h2:mem:tradedb
- Console: http://localhost:8080/h2-console
- User: sa
- Password: (empty)

### Production (PostgreSQL)
- Host: postgres:5432
- Database: tradedb
- User: tradeuser
- Password: tradepass123

### Environment Variables for PostgreSQL
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/tradedb
SPRING_DATASOURCE_USERNAME=tradeuser
SPRING_DATASOURCE_PASSWORD=tradepass123
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQL10Dialect
```

## Kubernetes Features

### Auto-scaling Configuration
The application includes Horizontal Pod Autoscaler (HPA) that scales based on:
- CPU Utilization: Target 70%
- Memory Utilization: Target 80%
- Min Replicas: 2
- Max Replicas: 5

### Health Checks
- **Liveness Probe**: Checks every 10 seconds, restarts pod if unhealthy
- **Readiness Probe**: Checks every 5 seconds, removes from service if unhealthy
- **Startup Grace Period**: 30 seconds for application startup

### Resource Requests and Limits
```yaml
requests:
  cpu: "250m"
  memory: "512Mi"
limits:
  cpu: "500m"
  memory: "1Gi"
```

### Pod Anti-affinity
Ensures pods are distributed across different nodes for high availability.

### Security
- ServiceAccount with minimal RBAC permissions
- Network Policies for ingress/egress traffic control
- Secrets for sensitive configuration

## Monitoring and Logging

### View Logs
```bash
# Local Docker Compose
docker-compose logs -f trade-app

# Kubernetes
kubectl logs -f deployment/trade-app -n trading-system

# Specific pod
kubectl logs -f pod-name -n trading-system
```

### View Resource Usage
```bash
kubectl top nodes -n trading-system
kubectl top pods -n trading-system
```

### View Events
```bash
kubectl get events -n trading-system --sort-by='.lastTimestamp'
```

## Cleanup

### Stop Local Services
```bash
docker-compose down
```

### Remove All Kubernetes Resources
```bash
./scripts/cleanup.sh
```

### Delete Azure Resources
```bash
# Delete the namespace (this will delete all related resources)
kubectl delete namespace trading-system

# Delete the AKS cluster
az aks delete --resource-group trading-system-rg --name trading-system-aks

# Delete the resource group
az group delete --name trading-system-rg
```

## Configuration and Customization

### Change Database Credentials
Update the following files:
1. `k8s/secret.yaml` - Kubernetes secrets
2. `docker-compose.yml` - Docker Compose environment variables
3. `src/main/resources/application.properties` - Spring Boot configuration

### Change Replica Count
Edit `k8s/trade-app-deployment.yaml` and modify:
```yaml
spec:
  replicas: 3  # Change this value
```

### Change Resource Limits
Edit `k8s/trade-app-deployment.yaml` and modify the `resources` section.

### Change Auto-scaling Parameters
Edit `k8s/hpa.yaml` to adjust:
- `minReplicas` and `maxReplicas`
- CPU and memory target utilization
- Scale-up/down behavior

## Troubleshooting

### Application Won't Start

**Docker Compose:**
```bash
# Check logs
docker-compose logs trade-app

# Verify database is running
docker-compose ps

# Recreate containers
docker-compose down
docker-compose up --build
```

**Kubernetes:**
```bash
# Check pod status
kubectl describe pod trade-app-xxx -n trading-system

# Check logs
kubectl logs trade-app-xxx -n trading-system

# Check events
kubectl get events -n trading-system
```

### Database Connection Issues

```bash
# Test database connectivity
kubectl run -it --rm debug --image=postgres:15-alpine --restart=Never -n trading-system -- \
  psql -h postgres -U tradeuser -d tradedb

# Password: tradepass123
```

### Cannot Access the Service

```bash
# Check service
kubectl get svc -n trading-system

# Check endpoints
kubectl get endpoints -n trading-system

# Port forward for debugging
kubectl port-forward svc/trade-app-service 8080:80 -n trading-system
```

## Performance Tuning

### JVM Options
Modify `JAVA_OPTS` in Docker files or Kubernetes deployment:
```bash
JAVA_OPTS="-Xmx1g -Xms512m -XX:+UseG1GC"
```

### Database Connection Pool
Update in `application.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

## Security Best Practices

1. **Rotate Secrets Regularly**
   - Update credentials in `k8s/secret.yaml`
   - Redeploy the application

2. **Use Azure Key Vault**
   - Store secrets in Azure Key Vault
   - Use Azure Pod Identity to access secrets

3. **Enable Network Policies**
   - Implemented in `k8s/network-policy.yaml`
   - Restricts traffic between pods

4. **Use HTTPS**
   - Configure Ingress with TLS certificates
   - Use Azure Application Gateway

5. **Implement RBAC**
   - ServiceAccount with minimal permissions
   - Implemented in `k8s/rbac.yaml`

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues, questions, or suggestions, please create an issue on GitHub or contact the development team.

## Useful Links

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Azure AKS Documentation](https://learn.microsoft.com/en-us/azure/aks/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Docker Documentation](https://docs.docker.com/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

**Last Updated**: January 2026
**Version**: 1.0.0
