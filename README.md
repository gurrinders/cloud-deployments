# cloud-deployments
Small apps showing deployments to cloud

## Swagger API Documentation

### Access Swagger UI

Once the application is running, you can explore the interactive API documentation:

**Swagger UI (Interactive):**
```
http://localhost:8080/swagger-ui.html
```

**OpenAPI JSON Specification:**
```
http://localhost:8080/v3/api-docs
```

**OpenAPI YAML Specification:**
```
http://localhost:8080/v3/api-docs.yaml
```

### Features of Swagger UI

- **Interactive API Explorer** - Try out endpoints directly from your browser
- **Request/Response Examples** - See sample data for each endpoint
- **Schema Documentation** - View detailed information about data models
- **Error Responses** - Understanding what can go wrong and why
- **Authorization** - Test authenticated endpoints (if configured)

### Example API Calls via Swagger

1. Navigate to `http://localhost:8080/swagger-ui.html`
2. Click on any endpoint (e.g., POST /products)
3. Click "Try it out"
4. Enter sample data in the request body
5. Click "Execute" to test the endpoint

### OpenAPI Specification

The application generates an OpenAPI 3.0 specification that can be used with tools like:
- Postman
- Insomnia
- APIdog
- Other API testing tools

**Import OpenAPI specification:**
- Use the JSON URL: `http://localhost:8080/v3/api-docs`
- Or YAML URL: `http://localhost:8080/v3/api-docs.yaml`
