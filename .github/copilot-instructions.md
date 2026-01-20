# Trading System CRUD Application - Setup Instructions

## Project Overview
Spring Boot CRUD application for a trading system that manages trading products (buy/sell operations). Designed for deployment to Azure with Docker containerization.

## Building the Workspace

### Prerequisites
- Java 17 or higher
- Maven 3.9.0 or higher
- Docker (for containerization)
- Azure CLI (for Azure deployment)

### Build Commands

1. **Clean and compile the project:**
   ```bash
   mvn clean compile
   ```

2. **Run all tests:**
   ```bash
   mvn test
   ```

3. **Build the application (creates JAR):**
   ```bash
   mvn clean package
   ```

4. **Run the application locally:**
   ```bash
   mvn spring-boot:run
   ```

### Docker Build

1. **Build the Docker image:**
   ```bash
   docker build -t trading-system:latest .
   ```

2. **Run the container:**
   ```bash
   docker run -p 8080:8080 trading-system:latest
   ```

### Azure Deployment

1. **Build and push to Azure Container Registry:**
   ```bash
   docker tag trading-system:latest <YOUR_REGISTRY>.azurecr.io/trading-system:latest
   docker push <YOUR_REGISTRY>.azurecr.io/trading-system:latest
   ```

2. **Deploy to Azure Container Instances or Kubernetes:**
   - Update the image URL in `azure-app-service.yaml`
   - Deploy using kubectl or Azure CLI

## API Endpoints

### Base URL
`http://localhost:8080/api`

### Trading Products CRUD Operations
- `POST /products` - Create a new trading product
- `GET /products` - Get all products
- `GET /products/{id}` - Get product by ID
- `GET /products/symbol/{symbol}` - Get product by symbol
- `GET /products/category/{category}` - Get products by category
- `PUT /products/{id}` - Update a product
- `DELETE /products/{id}` - Delete a product
- `GET /products/health` - Health check

### Example POST Request
```json
{
  "name": "Apple Stock",
  "description": "Apple Inc. Common Stock",
  "price": 150.25,
  "quantity": 100,
  "category": "STOCKS",
  "symbol": "AAPL"
}
```

## Database

- **Development**: H2 in-memory database
- **Production (Azure)**: PostgreSQL

Database console accessible at: `http://localhost:8080/h2-console`

## Profiles

- `default` (dev) - Uses H2 in-memory database
- `azure` - Uses PostgreSQL, configured for Azure

## Project Structure

```
├── src/main/java/com/tradingsystem/
│   ├── TradingSystemApplication.java
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── dto/
├── src/main/resources/
│   ├── application.yml
│   └── application-azure.yml
├── Dockerfile
├── pom.xml
└── azure-app-service.yaml
```

## Development Features

- Lombok for reducing boilerplate
- Spring Data JPA for easy database operations
- Spring Validation for input validation
- Spring DevTools for hot reload
- PostgreSQL support for production
