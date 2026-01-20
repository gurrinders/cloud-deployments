# Project Deployment Guide

## Quick Start

### Prerequisites
- Java 17
- Maven 3.8+
- Docker & Docker Compose (for local development)
- Azure CLI & kubectl (for Azure deployment)

### Quick Links
- [README](README.md) - Full documentation
- [API Documentation](API.md) - API endpoints reference
- [Contributing Guide](CONTRIBUTING.md) - How to contribute

## Three Ways to Deploy

### 1. Local Development (Fastest)
```bash
./scripts/deploy-local.sh
```
**Access**: http://localhost:8080

### 2. Kubernetes (Recommended for Production)
```bash
./scripts/deploy-to-k8s.sh
```

### 3. Azure AKS (Full Cloud Deployment)
```bash
export RESOURCE_GROUP="trading-system-rg"
./scripts/deploy-to-aks.sh
./scripts/deploy-to-k8s.sh
```

## Project Structure

```
trade-app/
├── src/                      # Spring Boot application source
├── k8s/                      # Kubernetes manifests
├── scripts/                  # Deployment scripts
├── Dockerfile               # Container image definition
├── docker-compose.yml       # Local development setup
├── pom.xml                  # Maven configuration
├── README.md                # Full documentation
├── API.md                   # API reference
└── CONTRIBUTING.md          # Contribution guidelines
```

## Key Features

✅ Complete CRUD REST API
✅ PostgreSQL database support
✅ Docker containerization
✅ Kubernetes orchestration
✅ Azure AKS ready
✅ Auto-scaling with HPA
✅ Health checks & monitoring
✅ Network policies & RBAC
✅ Multi-stage Docker builds
✅ High availability setup

## API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/api/v1/products` | Get all products |
| POST | `/api/v1/products` | Create product |
| GET | `/api/v1/products/{id}` | Get product by ID |
| PUT | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |
| GET | `/api/v1/products/status/{status}` | Filter by status |
| GET | `/api/v1/products/category/{category}` | Filter by category |
| GET | `/api/v1/products/search?name=...` | Search by name |

## Configuration Files

### Local Development
- `docker-compose.yml` - Local services setup
- `src/main/resources/application.properties` - Spring Boot config

### Kubernetes
- `k8s/namespace.yaml` - Kubernetes namespace
- `k8s/configmap.yaml` - Configuration data
- `k8s/secret.yaml` - Sensitive credentials
- `k8s/trade-app-deployment.yaml` - Application deployment
- `k8s/postgres-statefulset.yaml` - Database deployment
- `k8s/trade-app-service.yaml` - Network services
- `k8s/hpa.yaml` - Auto-scaling rules
- `k8s/rbac.yaml` - Access control
- `k8s/network-policy.yaml` - Network security
- `k8s/ingress.yaml` - Ingress configuration (optional)

### Deployment Scripts
- `scripts/deploy-local.sh` - Local Docker Compose deployment
- `scripts/deploy-to-k8s.sh` - Kubernetes deployment
- `scripts/deploy-to-aks.sh` - Azure AKS setup
- `scripts/setup-azure-postgresql.sh` - Azure Database setup
- `scripts/cleanup.sh` - Resource cleanup

## Database Options

### Development (H2 in-memory)
- URL: `jdbc:h2:mem:tradedb`
- Console: http://localhost:8080/h2-console
- No setup required

### Production (PostgreSQL)
**Docker Compose**:
- Automatically started by docker-compose
- User: `tradeuser`
- Password: `tradepass123`

**Kubernetes**:
- StatefulSet in k8s/postgres-statefulset.yaml
- Or use Azure Database for PostgreSQL via `scripts/setup-azure-postgresql.sh`

## Scaling

### Local Development
```bash
docker-compose up --scale trade-app=3
```

### Kubernetes (Auto-scaling)
Configured in `k8s/hpa.yaml`:
- Min replicas: 2
- Max replicas: 5
- CPU target: 70%
- Memory target: 80%

## Monitoring

### Application Metrics
```bash
curl http://localhost:8080/actuator/metrics
```

### Health Status
```bash
curl http://localhost:8080/actuator/health
```

### Kubernetes Monitoring
```bash
# View pod metrics
kubectl top pods -n trading-system

# View node metrics
kubectl top nodes

# View logs
kubectl logs -f deployment/trade-app -n trading-system
```

## Security Features

- ✅ RBAC configuration
- ✅ Network policies
- ✅ Secrets management
- ✅ Non-root container
- ✅ Read-only root filesystem (optional)
- ✅ Resource limits
- ✅ Health probes

## Performance Tuning

### JVM Heap Size
Edit `Dockerfile` or k8s deployment:
```
JAVA_OPTS="-Xmx1g -Xms512m"
```

### Database Connection Pool
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=20
```

### Kubernetes Resources
Edit `k8s/trade-app-deployment.yaml`:
```yaml
resources:
  requests:
    cpu: "250m"
    memory: "512Mi"
  limits:
    cpu: "500m"
    memory: "1Gi"
```

## Troubleshooting

### Application Won't Start
```bash
# Check logs
docker-compose logs trade-app
# or
kubectl logs -f deployment/trade-app -n trading-system
```

### Database Connection Issues
- Check if PostgreSQL is running
- Verify credentials in secrets
- Check network connectivity

### Cannot Access Service
```bash
# Port forward
kubectl port-forward svc/trade-app-service 8080:80 -n trading-system
```

## Environment Variables

Copy `.env.example` to `.env` and customize:
```bash
cp .env.example .env
```

Available variables:
- `RESOURCE_GROUP` - Azure resource group
- `CLUSTER_NAME` - AKS cluster name
- `REGION` - Azure region
- `ACR_NAME` - Container registry name
- `DOCKER_IMAGE_TAG` - Container image tag

## CI/CD Integration

### GitHub Actions Example
```yaml
- name: Build with Maven
  run: mvn clean package

- name: Build Docker image
  run: docker build -t myregistry.azurecr.io/trade-app:${{ github.sha }} .

- name: Deploy to AKS
  run: kubectl apply -f k8s/
```

## Next Steps

1. **Local Testing**: `./scripts/deploy-local.sh`
2. **API Testing**: Check [API.md](API.md)
3. **Kubernetes**: `./scripts/deploy-to-k8s.sh`
4. **Azure**: Follow [Azure Deployment Guide](README.md#option-2-deploy-to-azure-aks)
5. **Customize**: Update configurations as needed

## Support

See [README.md](README.md) for full documentation and [CONTRIBUTING.md](CONTRIBUTING.md) for contribution guidelines.

---
**Version**: 1.0.0 | **Last Updated**: January 2026
