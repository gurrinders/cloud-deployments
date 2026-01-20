#!/bin/bash

# Cleanup Script
# Remove all deployed resources from AKS cluster

set -e

NAMESPACE="trading-system"
RESOURCE_GROUP=${RESOURCE_GROUP:-"trading-system-rg"}
CLUSTER_NAME=${CLUSTER_NAME:-"trading-system-aks"}

echo "======================================"
echo "Cleanup Script"
echo "======================================"
echo "This will delete all resources from namespace: $NAMESPACE"
echo ""

# Ask for confirmation
read -p "Are you sure you want to proceed? (yes/no): " -r confirmation
if [[ ! $confirmation =~ ^[Yy][Ee][Ss]$ ]]; then
    echo "Cleanup cancelled."
    exit 0
fi

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null; then
    echo "Error: kubectl is not installed."
    exit 1
fi

# Delete namespace and all resources within it
echo "Deleting namespace: $NAMESPACE..."
kubectl delete namespace $NAMESPACE --ignore-not-found

echo ""
echo "=================================================="
echo "✓ Cleanup completed!"
echo "=================================================="
echo ""
echo "To delete the entire AKS cluster:"
echo "  az aks delete --resource-group $RESOURCE_GROUP --name $CLUSTER_NAME"
echo ""
echo "To delete the resource group:"
echo "  az group delete --name $RESOURCE_GROUP"
echo "=================================================="
