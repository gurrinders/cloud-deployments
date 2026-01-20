#!/bin/bash

# Local Docker Compose Deployment Script
# Deploy the trading system locally using Docker Compose

set -e

echo "======================================"
echo "Local Docker Compose Deployment"
echo "======================================"
echo ""

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "Error: Docker Compose is not installed. Please install it first."
    exit 1
fi

# Build and start containers
echo "Building Docker image..."
docker-compose build

echo "Starting services..."
docker-compose up -d

# Wait for services to be healthy
echo "Waiting for services to be healthy..."
sleep 10

# Check service health
echo ""
echo "Checking service status..."
docker-compose ps

echo ""
echo "=================================================="
echo "✓ Application is running!"
echo "=================================================="
echo ""
echo "Services running:"
echo "  - Trading App: http://localhost:8080"
echo "  - PostgreSQL: localhost:5432"
echo "  - H2 Console: http://localhost:8080/h2-console"
echo ""
echo "API Endpoints:"
echo "  - GET    /api/v1/products           - Get all products"
echo "  - POST   /api/v1/products           - Create new product"
echo "  - GET    /api/v1/products/{id}      - Get product by ID"
echo "  - PUT    /api/v1/products/{id}      - Update product"
echo "  - DELETE /api/v1/products/{id}      - Delete product"
echo "  - GET    /api/v1/products/status/{status}     - Get products by status"
echo "  - GET    /api/v1/products/category/{category} - Get products by category"
echo "  - GET    /api/v1/products/search?name=...     - Search products"
echo ""
echo "Health Checks:"
echo "  - Health: http://localhost:8080/actuator/health"
echo "  - Metrics: http://localhost:8080/actuator/metrics"
echo ""
echo "To stop services:"
echo "  docker-compose down"
echo ""
echo "To view logs:"
echo "  docker-compose logs -f trade-app"
echo "=================================================="
