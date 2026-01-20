# API Documentation

## Trading System API

Base URL: `http://localhost:8080/api/v1`

### Products Endpoints

#### 1. Create Product
- **Method**: `POST`
- **Endpoint**: `/products`
- **Description**: Create a new trading product
- **Request Body**:
  ```json
  {
    "productName": "Premium Trading Stock",
    "description": "High-value trading instrument",
    "category": "STOCKS",
    "price": 150.50,
    "quantity": 100
  }
  ```
- **Response** (201 Created):
  ```json
  {
    "id": 1,
    "productName": "Premium Trading Stock",
    "description": "High-value trading instrument",
    "category": "STOCKS",
    "price": 150.50,
    "quantity": 100,
    "status": "ACTIVE"
  }
  ```

#### 2. Get All Products
- **Method**: `GET`
- **Endpoint**: `/products`
- **Description**: Retrieve all trading products
- **Response** (200 OK):
  ```json
  [
    {
      "id": 1,
      "productName": "Premium Trading Stock",
      "description": "High-value trading instrument",
      "category": "STOCKS",
      "price": 150.50,
      "quantity": 100,
      "status": "ACTIVE"
    }
  ]
  ```

#### 3. Get Product by ID
- **Method**: `GET`
- **Endpoint**: `/products/{id}`
- **Description**: Retrieve a specific product by ID
- **Path Parameters**:
  - `id` (required): Product ID
- **Response** (200 OK):
  ```json
  {
    "id": 1,
    "productName": "Premium Trading Stock",
    "description": "High-value trading instrument",
    "category": "STOCKS",
    "price": 150.50,
    "quantity": 100,
    "status": "ACTIVE"
  }
  ```

#### 4. Update Product
- **Method**: `PUT`
- **Endpoint**: `/products/{id}`
- **Description**: Update a trading product
- **Path Parameters**:
  - `id` (required): Product ID
- **Request Body**:
  ```json
  {
    "productName": "Updated Trading Stock",
    "description": "Updated description",
    "category": "STOCKS",
    "price": 160.75,
    "quantity": 150,
    "status": "ACTIVE"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "id": 1,
    "productName": "Updated Trading Stock",
    "description": "Updated description",
    "category": "STOCKS",
    "price": 160.75,
    "quantity": 150,
    "status": "ACTIVE"
  }
  ```

#### 5. Delete Product
- **Method**: `DELETE`
- **Endpoint**: `/products/{id}`
- **Description**: Delete a trading product
- **Path Parameters**:
  - `id` (required): Product ID
- **Response** (204 No Content)

#### 6. Get Products by Status
- **Method**: `GET`
- **Endpoint**: `/products/status/{status}`
- **Description**: Filter products by status
- **Path Parameters**:
  - `status` (required): Product status (ACTIVE, INACTIVE, DISCONTINUED)
- **Response** (200 OK): Array of products

#### 7. Get Products by Category
- **Method**: `GET`
- **Endpoint**: `/products/category/{category}`
- **Description**: Filter products by category
- **Path Parameters**:
  - `category` (required): Product category
- **Response** (200 OK): Array of products

#### 8. Search Products by Name
- **Method**: `GET`
- **Endpoint**: `/products/search`
- **Description**: Search products by name (case-insensitive)
- **Query Parameters**:
  - `name` (required): Search term
- **Response** (200 OK): Array of matching products

### Health and Monitoring Endpoints

#### Health Check
- **Method**: `GET`
- **Endpoint**: `/actuator/health`
- **Response**:
  ```json
  {
    "status": "UP",
    "components": {
      "db": {
        "status": "UP"
      }
    }
  }
  ```

#### Liveness Probe
- **Method**: `GET`
- **Endpoint**: `/actuator/health/liveness`
- **Response**:
  ```json
  {
    "status": "UP"
  }
  ```

#### Readiness Probe
- **Method**: `GET`
- **Endpoint**: `/actuator/health/readiness`
- **Response**:
  ```json
  {
    "status": "UP"
  }
  ```

#### Metrics
- **Method**: `GET`
- **Endpoint**: `/actuator/metrics`
- **Response**: Available metrics list

### Error Responses

#### 400 Bad Request
```json
{
  "timestamp": "2024-01-20T10:30:45.123456",
  "status": 400,
  "error": "Validation Error",
  "message": "...",
  "path": "/api/v1/products"
}
```

#### 404 Not Found
```json
{
  "timestamp": "2024-01-20T10:30:45.123456",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with id: 999",
  "path": "/api/v1/products/999"
}
```

#### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-20T10:30:45.123456",
  "status": 500,
  "error": "Internal Server Error",
  "message": "...",
  "path": "/api/v1/products"
}
```

### Example CURL Requests

```bash
# Create product
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Trading Stock ABC",
    "description": "High-value trading instrument",
    "category": "STOCKS",
    "price": 150.50,
    "quantity": 100
  }'

# Get all products
curl http://localhost:8080/api/v1/products

# Get product by ID
curl http://localhost:8080/api/v1/products/1

# Update product
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Updated Stock ABC",
    "description": "Updated description",
    "category": "STOCKS",
    "price": 160.75,
    "quantity": 150
  }'

# Delete product
curl -X DELETE http://localhost:8080/api/v1/products/1

# Search by status
curl http://localhost:8080/api/v1/products/status/ACTIVE

# Search by category
curl http://localhost:8080/api/v1/products/category/STOCKS

# Search by name
curl "http://localhost:8080/api/v1/products/search?name=ABC"

# Health check
curl http://localhost:8080/actuator/health
```

### Request/Response Headers

**Required Headers**:
- `Content-Type: application/json` (for POST/PUT requests)

**Response Headers**:
- `Content-Type: application/json`
- `Content-Length: <size>`
- `Date: <timestamp>`

### Status Codes

- `200 OK`: Successful GET request
- `201 Created`: Successful POST request (resource created)
- `204 No Content`: Successful DELETE request
- `400 Bad Request`: Invalid request parameters or validation error
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

### Field Validation

- `productName`: Required, max 100 characters
- `description`: Required, max 500 characters
- `category`: Required, max 50 characters
- `price`: Required, must be positive
- `quantity`: Required, must be positive
- `status`: Auto-set to "ACTIVE" on creation, allowed values: ACTIVE, INACTIVE, DISCONTINUED

### Rate Limiting

Currently, there is no rate limiting implemented. Rate limiting can be added using Spring Cloud Gateway or similar tools if needed.

### API Versioning

Current version: `v1`
Base path: `/api/v1`
