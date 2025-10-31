# PharmaChain 🏥

**A Secure Platform for End-to-End Pharmaceutical Product Tracking**

PharmaChain is an enterprise-grade pharmaceutical supply chain management system built with Spring Boot, featuring robust security through Keycloak authentication and HashiCorp Vault for secrets management.

## 🌟 Features

- **End-to-End Pharmaceutical Tracking**: Complete visibility across the pharmaceutical supply chain
- **Multi-Role Access Control**: Support for Admin, Manager, and Driver roles
- **OAuth2 & OIDC Authentication**: Powered by Keycloak for enterprise-grade security
- **Secrets Management**: HashiCorp Vault integration for secure credential storage
- **RESTful API**: Comprehensive API for seamless integration
- **CORS Configured**: Ready for frontend integration with React, Angular, or Vue
- **PostgreSQL Database**: Reliable and scalable data persistence

## 🏗️ Architecture

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐
│   Frontend  │────▶│  Spring Boot │────▶│ PostgreSQL  │
│  (Port 3000)│     │  (Port 8081) │     │ (Port 5432) │
└─────────────┘     └──────────────┘     └─────────────┘
                           │
                           ├────▶ Keycloak (Port 8080)
                           │
                           └────▶ Vault (Port 8200)
```

## 📋 Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6 or higher
- **Docker**: 20.10 or higher
- **Docker Compose**: 1.29 or higher

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/pharmachain.git
cd pharmachain
```

### 2. Start Infrastructure Services

```bash
docker-compose up -d
```

This will start:
- PostgreSQL on `localhost:5432`
- HashiCorp Vault on `localhost:8200`

### 3. Configure Vault

Access Vault UI at `http://localhost:8200`
- **Root Token**: `myroot`

Create secrets in Vault:

```bash
vault secrets enable -path=secret kv-v2

# Store database credentials
vault kv put secret/pharmachain/admin \
  spring.datasource.username=postgres \
  spring.datasource.password=postgres
```

### 4. Set Up Keycloak

If you plan to use Keycloak authentication:

1. Uncomment the Keycloak service in `docker-compose.yml`
2. Start Keycloak: `docker-compose up -d keycloak`
3. Access Keycloak Admin Console at `http://localhost:8080`
   - Username: `admin`
   - Password: `admin123`
4. Create a realm named `pharmachain-realm`
5. Configure clients, users, and roles as needed

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## 🔧 Configuration

### Application Properties

Key configuration in `application.properties`:

```properties
# Server
server.port=8081

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/pharmachain_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# Vault
spring.cloud.vault.uri=http://localhost:8200
spring.cloud.vault.token=myroot
spring.cloud.vault.kv.backend=secret

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### CORS Configuration

By default, the application allows requests from:
- `http://localhost:3000`
- `http://127.0.0.1:3000`

To modify CORS settings for production, update the `SecurityConfig.java` file.

## 🔐 Security

### Authentication Flow

1. **OAuth2/OIDC**: Users authenticate through Keycloak
2. **JWT Tokens**: Access tokens are issued for API requests
3. **Role-Based Access**: Endpoints are protected by role (ADMIN, MANAGER, DRIVER)

### Roles & Permissions

| Role    | Permissions                                    |
|---------|-----------------------------------------------|
| ADMIN   | Full system access, user management           |
| MANAGER | Manage drivers, view reports, track shipments |
| DRIVER  | Update delivery status, view assigned routes  |

### Vault Integration

All sensitive credentials are stored in HashiCorp Vault:
- Database passwords
- API keys
- OAuth2 client secrets
- Third-party service credentials


## 🐳 Docker Compose Services

| Service   | Port | Purpose                          |
|-----------|------|----------------------------------|
| vault     | 8200 | Secrets management               |
| postgres  | 5432 | Primary database                 |
| keycloak  | 8080 | Authentication (optional)        |

## 🛠️ Development

### Build the Project

```bash
mvn clean install
```

### Run Tests

```bash
mvn test
```

### Package for Production

```bash
mvn clean package -DskipTests
```

## 📦 Deployment

### Using Docker

Uncomment the `pharmachain-app` service in `docker-compose.yml`:

```yaml
pharmachain-app:
  build: .
  container_name: pharmachain-app
  ports:
    - "8081:8081"
  environment:
    SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/pharmachain_db
    SPRING_CLOUD_VAULT_URI: http://vault:8200
    SPRING_CLOUD_VAULT_TOKEN: myroot
  depends_on:
    - vault
    - postgres
  networks:
    - pharmachain-network
```

Then run:
```bash
docker-compose up -d
```

## 🔍 Monitoring & Logging

Enable detailed logging for debugging:

```properties
logging.level.org.springframework.security=DEBUG
logging.level.org.keycloak=DEBUG
logging.level.root=INFO
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 🙏 Acknowledgments

- Spring Boot & Spring Security teams
- Keycloak community
- HashiCorp Vault team
- PostgreSQL contributors

## 📞 Support

For support,open an issue in the GitHub repository.

---

**Built with ❤️ for pharmaceutical supply chain transparency and security**
