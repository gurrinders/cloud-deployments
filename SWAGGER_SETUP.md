# Swagger/OpenAPI Setup Guide

## What Was Added

### 1. **Dependencies**
- `springdoc-openapi-starter-webmvc-ui` (v2.2.0) - Provides Swagger UI and OpenAPI support

### 2. **Configuration Class**
- `SwaggerConfig.java` - Configures OpenAPI specification with:
  - API title, description, and version
  - Contact information
  - License information
  - Server configurations (local, staging, production)

### 3. **API Documentation Annotations**
- Controller endpoints annotated with `@Operation`, `@ApiResponses`, `@Parameter`
- DTO fields annotated with `@Schema` for detailed field descriptions
- Complete request/response documentation

## Accessing Swagger UI

### After Starting the Application
```bash
mvn spring-boot:run
```

### Open in Browser
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:8080/v3/api-docs.yaml`

## Features Available

### Interactive API Testing
1. Click on any endpoint in Swagger UI
2. Click "Try it out"
3. Enter request parameters/body
4. Click "Execute"
5. See response in real-time

### Schema Documentation
- Complete field descriptions
- Validation rules displayed
- Example values shown
- Required/optional indicators

### Response Examples
- HTTP status codes documented
- Error responses explained
- Response body schema shown

## Files Modified/Created

```
src/main/java/com/tradingsystem/
├── config/
│   └── SwaggerConfig.java              (NEW)
├── controller/
│   └── TradingProductController.java   (UPDATED - added @Operation, @ApiResponse annotations)
└── dto/
    └── TradingProductDTO.java          (UPDATED - added @Schema annotations)
```

## POM.xml Changes
Added dependency:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

## Integration with Tools

### Postman
1. Go to `http://localhost:8080/v3/api-docs`
2. Copy the JSON content
3. In Postman: Import → Paste Raw Text → Import

### Insomnia
1. Import → From URL
2. Enter: `http://localhost:8080/v3/api-docs`

### Other Tools
- APIdog
- Swagger Editor
- AWS API Gateway
- Azure API Management

## Customization

To modify Swagger UI configuration, edit `SwaggerConfig.java`:
- Change API title/description
- Add security schemes
- Modify server URLs
- Add tags for endpoint grouping

## Documentation Best Practices

### For New Endpoints
Always add:
1. `@Operation` - Describe what the endpoint does
2. `@ApiResponse` - Document response codes
3. `@Parameter` - Describe path/query parameters
4. `@RequestBody` with description

### For DTOs
Always add `@Schema` annotations with:
- Description
- Example value
- Whether it's read-only or write-only
