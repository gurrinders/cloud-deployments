# Trading System - Complete Project Overview

## 🎯 Project Summary

A production-ready Spring Boot CRUD application for managing trading products, fully containerized with Docker, orchestrated with Kubernetes, and ready for deployment on Azure AKS with enterprise-grade features including auto-scaling, monitoring, and security controls.

## 📁 Project Contents

### Core Application Files
- **pom.xml** - Maven build configuration with all dependencies
- **Dockerfile** - Multi-stage Docker build for optimized container images
- **docker-compose.yml** - Local development environment setup
- **.dockerignore** - Files to exclude from Docker build
- **.gitignore** - Git ignore rules

### Spring Boot Application
```
src/main/java/com/trading/
├── TradeAppApplication.java              # Main Spring Boot application
├── controller/
│   └── TradingProductController.java      # REST API endpoints
├── service/
│   └── TradingProductService.java         # Business logic
├── entity/
│   └── TradingProduct.java                # JPA entity
├── repository/
│   └── TradingProductRepository.java      # Data access layer
├── dto/
│   └── TradingProductDTO.java             # Data transfer object
└── exception/
    ├── GlobalExceptionHandler.java        # Exception handling
    └── ErrorResponse.java                 # Error response model
```

### Kubernetes Manifests (k8s/)
- **namespace.yaml** - Kubernetes namespace and labels
- **configmap.yaml** - Application configuration
- **secret.yaml** - Sensitive credentials (database, passwords)
- **postgres-statefulset.yaml** - PostgreSQL database deployment
- **trade-app-deployment.yaml** - Application deployment (3 replicas)
- **trade-app-service.yaml** - LoadBalancer and ClusterIP services
- **hpa.yaml** - Horizontal Pod Autoscaler (2-5 replicas)
- **rbac.yaml** - Role-Based Access Control
- **network-policy.yaml** - Network security policies
- **ingress.yaml** - Ingress configuration (optional, for domains)

### Deployment Scripts (scripts/)
- **deploy-local.sh** - Deploy using Docker Compose locally
- **deploy-to-k8s.sh** - Deploy to existing Kubernetes cluster
- **deploy-to-aks.sh** - Setup Azure AKS infrastructure
- **setup-azure-postgresql.sh** - Create Azure Database for PostgreSQL
- **setup-helm.sh** - Initialize Helm chart structure
- **cleanup.sh** - Remove all Kubernetes resources

### Documentation
- **README.md** - Complete project documentation
- **DEPLOYMENT.md** - Quick deployment guide
- **API.md** - REST API reference documentation
- **CONTRIBUTING.md** - Contributing guidelines
- **.env.example** - Environment variables template

## 🚀 Quick Start

### 1. Local Development (Fastest)
```bash
cd /workspaces/cloud-deployments/trade-app
./scripts/deploy-local.sh
# Access: http://localhost:8080
```

### 2. Kubernetes Deployment
```bash
./scripts/deploy-to-k8s.sh
kubectl get pods -n trading-system
```

### 3. Azure AKS Deployment
```bash
export RESOURCE_GROUP="trading-system-rg"
export CLUSTER_NAME="trading-system-aks"
export REGION="eastus"
./scripts/deploy-to-aks.sh
./scripts/deploy-to-k8s.sh
```

## 📊 Architecture

### Local Development
```
┌─────────────────────────────────────┐
│   Spring Boot Application (8080)    │
├─────────────────────────────────────┤
│   PostgreSQL Database (5432)        │
└─────────────────────────────────────┘
   Docker Compose Network
```

### Kubernetes/Azure
```
┌────────────────────────────────────────────────────┐
│        Azure Kubernetes Service (AKS)              │
├────────────────────────────────────────────────────┤
│  Namespace: trading-system                         │
│  ┌──────────────────────────────────────────────┐  │
│  │ Deployment: trade-app (3 replicas, HPA 2-5) │  │
│  │  └─ Pod 1 (trade-app)                        │  │
│  │  └─ Pod 2 (trade-app)                        │  │
│  │  └─ Pod 3 (trade-app)                        │  │
│  ├──────────────────────────────────────────────┤  │
│  │ StatefulSet: postgres (1 replica)           │  │
│  │  └─ Pod postgres-0 with persistent volume   │  │
│  ├──────────────────────────────────────────────┤  │
│  │ Service: trade-app-service (LoadBalancer)   │  │
│  │ Service: trade-app-internal (ClusterIP)     │  │
│  ├──────────────────────────────────────────────┤  │
│  │ RBAC: ServiceAccount, Role, RoleBinding     │  │
│  │ NetworkPolicies: Ingress/Egress rules       │  │
│  └──────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────┘
```

## 🔧 Features

### Application Features
- ✅ Complete CRUD REST API
- ✅ Product management with status and categories
- ✅ Advanced search and filtering
- ✅ Validation and error handling
- ✅ Transaction support
- ✅ Actuator endpoints for monitoring

### Infrastructure Features
- ✅ Docker multi-stage builds
- ✅ Kubernetes orchestration
- ✅ Horizontal Pod Autoscaler
- ✅ Health checks (liveness & readiness)
- ✅ Resource limits and requests
- ✅ RBAC configuration
- ✅ Network policies
- ✅ Persistent volumes for database
- ✅ Pod anti-affinity for HA
- ✅ Azure AKS integration

### Security Features
- ✅ Secrets management
- ✅ Network policies
- ✅ RBAC permissions
- ✅ Input validation
- ✅ Exception handling
- ✅ Non-root container execution

## 📝 API Endpoints

### Product Operations
| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/api/v1/products` | Get all products |
| POST | `/api/v1/products` | Create new product |
| GET | `/api/v1/products/{id}` | Get product by ID |
| PUT | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |
| GET | `/api/v1/products/status/{status}` | Filter by status |
| GET | `/api/v1/products/category/{category}` | Filter by category |
| GET | `/api/v1/products/search?name=...` | Search by name |

### Monitoring
| Endpoint | Purpose |
|----------|---------|
| `/actuator/health` | Overall health status |
| `/actuator/health/liveness` | Kubernetes liveness probe |
| `/actuator/health/readiness` | Kubernetes readiness probe |
| `/actuator/metrics` | Application metrics |

## 🗄️ Database Schema

### Trading Products Table
```sql
CREATE TABLE trading_products (
    id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## 🔐 Database Credentials

### Development (H2)
- URL: `jdbc:h2:mem:tradedb`
- User: `sa`
- Password: (empty)
- Console: http://localhost:8080/h2-console

### Docker Compose (PostgreSQL)
- Host: `postgres`
- Port: `5432`
- Database: `tradedb`
- User: `tradeuser`
- Password: `tradepass123`

### Kubernetes (PostgreSQL)
- Host: `postgres`
- Port: `5432`
- Database: `tradedb`
- User: `tradeuser`
- Password: `tradepass123` (from secrets)

## 📦 Docker Image

### Build Locally
```bash
docker build -t trade-app:latest .
docker run -p 8080:8080 trade-app:latest
```

### Push to Azure Container Registry
```bash
az acr build --registry myacr --image trade-app:latest .
```

## 🎛️ Kubernetes Customization

### Scaling
- Edit `k8s/trade-app-deployment.yaml`: Change `replicas: 3`
- Or configure HPA in `k8s/hpa.yaml`

### Resource Limits
- Edit `k8s/trade-app-deployment.yaml`: Adjust `resources` section

### Database
- Use embedded: `k8s/postgres-statefulset.yaml`
- Use Azure: Run `scripts/setup-azure-postgresql.sh`

### Ingress/DNS
- Uncomment and configure `k8s/ingress.yaml`
- Update domain name and TLS certificate

## 🧪 Testing

### Build
```bash
mvn clean compile
```

### Test
```bash
mvn test
```

### Package
```bash
mvn package
```

### Full Build
```bash
mvn clean package
```

## 📊 Monitoring Commands

### Local Docker Compose
```bash
docker-compose ps
docker-compose logs -f trade-app
docker-compose stats
```

### Kubernetes
```bash
# Pods
kubectl get pods -n trading-system
kubectl describe pod <pod-name> -n trading-system
kubectl logs -f <pod-name> -n trading-system

# Services
kubectl get svc -n trading-system
kubectl get endpoints -n trading-system

# Deployments
kubectl get deployment -n trading-system
kubectl describe deployment trade-app -n trading-system
kubectl rollout status deployment/trade-app -n trading-system

# HPA
kubectl get hpa -n trading-system
kubectl describe hpa trade-app-hpa -n trading-system

# Metrics
kubectl top nodes
kubectl top pods -n trading-system

# Events
kubectl get events -n trading-system
```

## 🔄 Continuous Integration/Deployment

### GitHub Actions Example
```yaml
name: Build and Deploy
on:
  push:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - run: mvn clean package
      - name: Build image
        run: docker build -t myacr.azurecr.io/trade-app:${{ github.sha }} .
      - name: Deploy to AKS
        run: |
          az aks get-credentials -g ${{ secrets.RG }} -n ${{ secrets.CLUSTER }}
          kubectl apply -f k8s/
```

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| README.md | Complete project documentation |
| DEPLOYMENT.md | Quick deployment guide |
| API.md | REST API reference |
| CONTRIBUTING.md | Contribution guidelines |
| INDEX.md | This file - Project overview |

## 🐛 Troubleshooting

### Common Issues

**Port Already in Use**
```bash
docker-compose down
docker ps  # Check for running containers
```

**Database Connection Failed**
```bash
# Check if PostgreSQL is running
docker-compose ps

# Check logs
docker-compose logs postgres
```

**Pod won't start in Kubernetes**
```bash
kubectl describe pod <pod-name> -n trading-system
kubectl logs <pod-name> -n trading-system
```

See [README.md](README.md) for detailed troubleshooting.

## 🎓 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Azure AKS Guide](https://learn.microsoft.com/en-us/azure/aks/)
- [Docker Documentation](https://docs.docker.com/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

## 📋 Deployment Checklist

- [ ] Clone repository
- [ ] Install prerequisites
- [ ] Configure environment variables (.env)
- [ ] Choose deployment method
- [ ] Run deployment script
- [ ] Verify services are running
- [ ] Test API endpoints
- [ ] Configure monitoring
- [ ] Set up backups
- [ ] Document changes

## 🔗 Quick Links

- **Source Code**: `/workspaces/cloud-deployments/trade-app`
- **Local Access**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **API Docs**: See [API.md](API.md)
- **Full Docs**: See [README.md](README.md)

## 📞 Support

For issues, questions, or contributions:
1. Check [README.md](README.md) documentation
2. Review [API.md](API.md) for API details
3. See [CONTRIBUTING.md](CONTRIBUTING.md) to contribute
4. Check [DEPLOYMENT.md](DEPLOYMENT.md) for deployment help

## 📄 License

MIT License - See LICENSE file for details

---

**Project Version**: 1.0.0
**Last Updated**: January 2026
**Status**: ✅ Production Ready

---

## Next Steps

1. **Start Local Development**
   ```bash
   ./scripts/deploy-local.sh
   ```

2. **Test the API**
   - Create, Read, Update, Delete products
   - See [API.md](API.md) for examples

3. **Explore Kubernetes**
   ```bash
   ./scripts/deploy-to-k8s.sh
   ```

4. **Deploy to Azure**
   ```bash
   ./scripts/deploy-to-aks.sh
   ```

5. **Customize for Your Needs**
   - Update database credentials
   - Configure domain and TLS
   - Adjust resource limits
   - Set up monitoring alerts

Happy coding! 🚀
