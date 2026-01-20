# 🚀 Getting Started with Trading System

Welcome! This guide will get you up and running in 5 minutes.

## Prerequisites Check

```bash
# Java
java -version  # Need 17 or higher

# Maven
mvn -version   # Need 3.8 or higher

# Docker (for local dev)
docker --version
docker-compose --version

# kubectl (for Kubernetes)
kubectl version --client
```

## Option A: Run Locally (Fastest - 2 minutes)

### Step 1: Start Application
```bash
cd /workspaces/cloud-deployments/trade-app
./scripts/deploy-local.sh
```

### Step 2: Verify It's Running
```bash
curl http://localhost:8080/actuator/health
```

You should see: `{"status":"UP"}`

### Step 3: Create Your First Product
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Apple Stock",
    "description": "Tech company stock",
    "category": "STOCKS",
    "price": 150.50,
    "quantity": 100
  }'
```

### Step 4: Get All Products
```bash
curl http://localhost:8080/api/v1/products
```

### Step 5: Stop When Done
```bash
docker-compose down
```

---

## Option B: Deploy to Kubernetes

### Step 1: Check Kubernetes Connection
```bash
kubectl cluster-info
kubectl get nodes
```

### Step 2: Deploy
```bash
./scripts/deploy-to-k8s.sh
```

### Step 3: Check Status
```bash
kubectl get pods -n trading-system
kubectl get svc -n trading-system
```

### Step 4: Access Application
```bash
kubectl port-forward svc/trade-app-service 8080:80 -n trading-system
# Then: curl http://localhost:8080/api/v1/products
```

---

## Option C: Deploy to Azure AKS

### Step 1: Set Azure Variables
```bash
export RESOURCE_GROUP="my-trading-rg"
export CLUSTER_NAME="my-trading-aks"
export REGION="eastus"
```

### Step 2: Create Infrastructure
```bash
./scripts/deploy-to-aks.sh
```
*(Takes ~15 minutes)*

### Step 3: Deploy Application
```bash
./scripts/deploy-to-k8s.sh
```

### Step 4: Access
```bash
kubectl get svc -n trading-system
# Use the EXTERNAL-IP from LoadBalancer
```

---

## 📚 Full Documentation

| File | Read Time | Purpose |
|------|-----------|---------|
| [INDEX.md](INDEX.md) | 5 min | Project overview |
| [README.md](README.md) | 15 min | Complete guide |
| [API.md](API.md) | 10 min | API reference |
| [DEPLOYMENT.md](DEPLOYMENT.md) | 5 min | Deployment options |

---

## 🔗 Quick API Examples

### Create Product
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"productName":"Stock","description":"Desc","category":"CAT","price":100,"quantity":50}'
```

### Get All
```bash
curl http://localhost:8080/api/v1/products
```

### Get One
```bash
curl http://localhost:8080/api/v1/products/1
```

### Update
```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{"productName":"Updated","description":"Desc","category":"CAT","price":120,"quantity":60}'
```

### Delete
```bash
curl -X DELETE http://localhost:8080/api/v1/products/1
```

### Filter by Status
```bash
curl http://localhost:8080/api/v1/products/status/ACTIVE
```

### Search
```bash
curl "http://localhost:8080/api/v1/products/search?name=Stock"
```

---

## ⚡ Useful Commands

### View Logs (Local)
```bash
docker-compose logs -f trade-app
```

### View Logs (Kubernetes)
```bash
kubectl logs -f deployment/trade-app -n trading-system
```

### Port Forward
```bash
kubectl port-forward svc/trade-app-service 8080:80 -n trading-system
```

### Check Metrics
```bash
kubectl top pods -n trading-system
kubectl top nodes
```

### Cleanup
```bash
# Local
docker-compose down

# Kubernetes
./scripts/cleanup.sh
```

---

## 🆘 Common Issues

### "Connection refused"
- Make sure services are running: `docker-compose ps` or `kubectl get pods -n trading-system`
- Check logs for errors

### "Port already in use"
- Stop other services: `docker-compose down`
- Or use a different port

### "Database connection failed"
- Wait a few seconds for database to start
- Check database is running

### "Kubernetes pod stuck"
```bash
kubectl describe pod <pod-name> -n trading-system
kubectl logs <pod-name> -n trading-system
```

---

## ✅ What's Included

✅ Spring Boot REST API
✅ PostgreSQL database
✅ Docker & Docker Compose
✅ Kubernetes manifests
✅ Auto-scaling (HPA)
✅ Health checks
✅ RBAC & Network policies
✅ Azure AKS ready
✅ Comprehensive documentation
✅ Deployment scripts

---

## 📖 Next Steps

1. ✅ Start with local deployment: `./scripts/deploy-local.sh`
2. ✅ Test a few API calls
3. ✅ Read [INDEX.md](INDEX.md) for overview
4. ✅ Check [API.md](API.md) for all endpoints
5. ✅ Deploy to Kubernetes when ready
6. ✅ Configure for production needs

---

## 💡 Tips

- Start with **Option A** (Local) first to understand the application
- Use **Option B** (Kubernetes) for testing deployment
- Use **Option C** (Azure AKS) for production

- All scripts are in `scripts/` directory
- All configuration in `k8s/` directory
- Full documentation in root directory (`.md` files)

---

## 📞 Need Help?

1. Check the error message in logs
2. See [Troubleshooting in README](README.md#troubleshooting)
3. Check [API reference](API.md)
4. Review [deployment options](DEPLOYMENT.md)

---

**You're all set! 🎉 Start with:**
```bash
cd /workspaces/cloud-deployments/trade-app
./scripts/deploy-local.sh
curl http://localhost:8080/api/v1/products
```

Happy coding! 🚀
