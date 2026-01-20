#!/bin/bash

# Azure AKS Deployment Script
# This script deploys the Trading System application to Azure AKS

set -e

# Configuration
RESOURCE_GROUP=${RESOURCE_GROUP:-"trading-system-rg"}
CLUSTER_NAME=${CLUSTER_NAME:-"trading-system-aks"}
REGION=${REGION:-"eastus"}
ACR_NAME=${ACR_NAME:-"tradingacr"}
APP_NAME="trade-app"
DOCKER_IMAGE_NAME="${ACR_NAME}.azurecr.io/${APP_NAME}"
DOCKER_IMAGE_TAG="${DOCKER_IMAGE_TAG:-"latest"}"

echo "======================================"
echo "Azure AKS Deployment Script"
echo "======================================"
echo "Resource Group: $RESOURCE_GROUP"
echo "Cluster Name: $CLUSTER_NAME"
echo "Region: $REGION"
echo "ACR Name: $ACR_NAME"
echo "======================================\n"

# Check if Azure CLI is installed
if ! command -v az &> /dev/null; then
    echo "Error: Azure CLI is not installed. Please install it first."
    exit 1
fi

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null; then
    echo "Error: kubectl is not installed. Please install it first."
    exit 1
fi

# Create Resource Group
echo "Creating Resource Group: $RESOURCE_GROUP..."
az group create \
    --name $RESOURCE_GROUP \
    --location $REGION || echo "Resource group already exists"

# Create Container Registry
echo "Creating Azure Container Registry: $ACR_NAME..."
az acr create \
    --resource-group $RESOURCE_GROUP \
    --name $ACR_NAME \
    --sku Basic || echo "Container registry already exists"

# Create AKS Cluster
echo "Creating AKS Cluster: $CLUSTER_NAME..."
az aks create \
    --resource-group $RESOURCE_GROUP \
    --name $CLUSTER_NAME \
    --node-count 3 \
    --vm-set-type VirtualMachineScaleSets \
    --load-balancer-sku standard \
    --enable-managed-identity \
    --network-plugin azure \
    --network-policy azure \
    --attach-acr $ACR_NAME \
    --zones 1 2 3 \
    --enable-cluster-autoscaling \
    --min-count 2 \
    --max-count 5 \
    --enable-pod-identity \
    --docker-bridge-address 172.17.0.1/16 \
    --enable-addons monitoring \
    --kubernetes-version 1.27 || echo "AKS cluster already exists"

# Get AKS Credentials
echo "Getting AKS credentials..."
az aks get-credentials \
    --resource-group $RESOURCE_GROUP \
    --name $CLUSTER_NAME \
    --overwrite-existing

# Build and Push Docker Image
echo "Building Docker image: $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG..."
az acr build \
    --registry $ACR_NAME \
    --image ${APP_NAME}:${DOCKER_IMAGE_TAG} \
    --file Dockerfile .

echo "=================================================="
echo "✓ Azure infrastructure setup completed!"
echo "=================================================="
echo "Next steps:"
echo "1. Update the image name in k8s/trade-app-deployment.yaml:"
echo "   image: $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
echo "2. Run: kubectl apply -f k8s/"
echo "3. Check deployment status: kubectl get pods -n trading-system"
echo "=================================================="
