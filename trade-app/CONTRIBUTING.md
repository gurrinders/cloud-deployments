# Contributing to Trading System

Thank you for your interest in contributing to the Trading System project!

## Development Setup

1. Install prerequisites:
   - Java 17
   - Maven 3.8+
   - Docker
   - Git

2. Clone and build:
   ```bash
   git clone https://github.com/gurrinders/cloud-deployments.git
   cd cloud-deployments/trade-app
   mvn clean install
   ```

3. Start local environment:
   ```bash
   docker-compose up
   ```

## Code Standards

- Follow Google Java Style Guide
- Add unit tests for new features
- Ensure all tests pass: `mvn test`
- Add JavaDoc comments for public methods
- Keep methods focused and under 30 lines

## Commit Messages

Use clear, descriptive commit messages:
```
feat: Add product search endpoint
fix: Correct database connection timeout
docs: Update API documentation
```

## Pull Request Process

1. Create a feature branch
2. Make your changes
3. Write/update tests
4. Run: `mvn clean verify`
5. Submit PR with clear description

## Issues

- Use descriptive titles
- Provide reproduction steps
- Include expected vs actual behavior
- Attach relevant logs or screenshots

## Testing

Run tests:
```bash
mvn test
```

Integration tests:
```bash
mvn verify
```

## Questions?

Contact the development team or create a discussion on GitHub.
