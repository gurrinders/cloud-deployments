#!/bin/bash

# Azure Database for PostgreSQL Setup
# This script creates an Azure Database for PostgreSQL instead of using K8s StatefulSet

set -e

RESOURCE_GROUP=${RESOURCE_GROUP:-"trading-system-rg"}
DB_SERVER_NAME=${DB_SERVER_NAME:-"trade-db-server-$(date +%s)"}
DB_NAME="tradedb"
DB_ADMIN_USER="tradeadmin"
DB_ADMIN_PASSWORD=$(openssl rand -base64 32)
LOCATION=${REGION:-"eastus"}

echo "======================================"
echo "Azure Database for PostgreSQL Setup"
echo "======================================"
echo ""

# Check if Azure CLI is installed
if ! command -v az &> /dev/null; then
    echo "Error: Azure CLI is not installed."
    exit 1
fi

# Create PostgreSQL Server
echo "Creating Azure Database for PostgreSQL Server: $DB_SERVER_NAME..."
az postgres server create \
    --resource-group $RESOURCE_GROUP \
    --name $DB_SERVER_NAME \
    --location $LOCATION \
    --admin-user $DB_ADMIN_USER \
    --admin-password "$DB_ADMIN_PASSWORD" \
    --sku-name B_Gen5_2 \
    --storage-size 51200 \
    --backup-retention 7 \
    --geo-redundant-backup Enabled \
    --enable-storage-autogrow

# Create Database
echo "Creating database: $DB_NAME..."
az postgres db create \
    --resource-group $RESOURCE_GROUP \
    --server-name $DB_SERVER_NAME \
    --name $DB_NAME

# Configure Firewall Rules
echo "Configuring firewall rules..."
az postgres server firewall-rule create \
    --resource-group $RESOURCE_GROUP \
    --server-name $DB_SERVER_NAME \
    --name AllowAzureIPs \
    --start-ip-address 0.0.0.0 \
    --end-ip-address 255.255.255.255

# Get Server Details
SERVER_FQDN=$(az postgres server show \
    --resource-group $RESOURCE_GROUP \
    --name $DB_SERVER_NAME \
    --query fullyQualifiedDomainName \
    --output tsv)

echo ""
echo "=================================================="
echo "✓ Azure Database for PostgreSQL created!"
echo "=================================================="
echo ""
echo "Connection Details:"
echo "  Server: $DB_SERVER_NAME"
echo "  FQDN: $SERVER_FQDN"
echo "  Database: $DB_NAME"
echo "  Username: ${DB_ADMIN_USER}@${DB_SERVER_NAME}"
echo "  Password: (saved during creation)"
echo ""
echo "Connection String:"
echo "  jdbc:postgresql://${SERVER_FQDN}:5432/${DB_NAME}?user=${DB_ADMIN_USER}@${DB_SERVER_NAME}&password=${DB_ADMIN_PASSWORD}&sslmode=require"
echo ""
echo "Update k8s/secret.yaml with the new credentials:"
echo "  SPRING_DATASOURCE_URL: jdbc:postgresql://${SERVER_FQDN}:5432/${DB_NAME}"
echo "  SPRING_DATASOURCE_USERNAME: ${DB_ADMIN_USER}@${DB_SERVER_NAME}"
echo "  SPRING_DATASOURCE_PASSWORD: <use the password generated>"
echo ""
echo "=================================================="
