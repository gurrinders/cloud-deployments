#!/bin/bash

# Kubernetes Deployment Script
# Deploy the trading system to Kubernetes cluster

set -e

NAMESPACE="trading-system"

echo "======================================"
echo "Kubernetes Deployment Script"
echo "======================================"
echo "Namespace: $NAMESPACE"
echo "======================================\n"

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null; then
    echo "Error: kubectl is not installed. Please install it first."
    exit 1
fi

# Create namespace
echo "Creating namespace: $NAMESPACE..."
kubectl create namespace $NAMESPACE --dry-run=client -o yaml | kubectl apply -f -

# Apply ConfigMap and Secrets
echo "Applying ConfigMap and Secrets..."
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml

# Apply RBAC
echo "Applying RBAC..."
kubectl apply -f k8s/rbac.yaml

# Apply Network Policies
echo "Applying Network Policies..."
kubectl apply -f k8s/network-policy.yaml

# Deploy Database
echo "Deploying PostgreSQL..."
kubectl apply -f k8s/postgres-statefulset.yaml

# Wait for database to be ready
echo "Waiting for PostgreSQL to be ready..."
kubectl wait --for=condition=Ready pod -l app=postgres -n $NAMESPACE --timeout=300s || echo "Warning: Timeout waiting for PostgreSQL"

# Deploy Application
echo "Deploying Trade App..."
kubectl apply -f k8s/trade-app-deployment.yaml
kubectl apply -f k8s/trade-app-service.yaml

# Apply HPA
echo "Applying Horizontal Pod Autoscaler..."
kubectl apply -f k8s/hpa.yaml

# Wait for deployment to be ready
echo "Waiting for Trade App deployment to be ready..."
kubectl wait --for=condition=available --timeout=300s deployment/trade-app -n $NAMESPACE || echo "Warning: Timeout waiting for deployment"

echo ""
echo "=================================================="
echo "✓ Deployment completed!"
echo "=================================================="
echo ""
echo "Checking deployment status:"
kubectl get pods -n $NAMESPACE
echo ""
echo "Get service details:"
kubectl get svc -n $NAMESPACE
echo ""
echo "Get application logs:"
echo "  kubectl logs -f deployment/trade-app -n $NAMESPACE"
echo ""
echo "Port forward to access application locally:"
echo "  kubectl port-forward svc/trade-app-service 8080:80 -n $NAMESPACE"
echo ""
echo "Access application:"
echo "  http://localhost:8080"
echo "  API Documentation: http://localhost:8080/swagger-ui.html"
echo "=================================================="
