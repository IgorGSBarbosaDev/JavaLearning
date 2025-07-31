# 🍃 Spring Framework Project Ideas by Skill Level

This comprehensive guide provides structured Spring Framework project ideas organized by skill level and difficulty. Each project includes detailed architecture guidance, technology stack, implementation steps, and required knowledge.

## 📊 Project Organization System

- 🟢 **BEGINNER**: Core Spring concepts and basic applications
- 🟡 **INTERMEDIATE**: Advanced Spring features and real-world applications  
- 🔴 **ADVANCED**: Enterprise-grade, complex architectures

Each project includes:
- **🏗️ Architecture Design**
- **🛠️ Technology Stack**
- **📚 Required Knowledge**
- **🚀 Getting Started Guide**
- **📁 Project Structure**
- **⚙️ Implementation Steps**

---

## 🌱 BEGINNER LEVEL PROJECTS
*Focus: Spring Core, Spring Boot Basics, REST APIs*

### 🟢 Project 1: Personal Task Manager API

#### 📋 Project Overview
**Duration:** 2-3 weeks  
**Description:** A REST API for managing personal tasks with user authentication and CRUD operations.  
**Business Value:** Learn Spring Boot fundamentals, REST API design, and basic security.

#### 🏗️ Architecture Design
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client App    │───▶│  Spring Boot    │───▶│   H2 Database   │
│  (Postman/Web)  │    │  REST API       │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │ Spring Security │
                       │ (Basic Auth)    │
                       └─────────────────┘
```

#### 🛠️ Technology Stack
- **Core**: Spring Boot 3.x, Spring Web, Spring Data JPA
- **Security**: Spring Security (Basic Authentication)
- **Database**: H2 (embedded), JPA/Hibernate
- **Build Tool**: Maven or Gradle
- **Testing**: JUnit 5, MockMvc, TestContainers
- **Documentation**: SpringDoc OpenAPI 3

#### 📚 Required Knowledge
**Prerequisites:**
- Java 17+ fundamentals
- Basic understanding of HTTP protocols
- Maven/Gradle basics
- Git version control

**Spring Concepts to Learn:**
- Dependency Injection and IoC
- Spring Boot auto-configuration
- @RestController and @RequestMapping
- Spring Data JPA repositories
- Basic Spring Security

#### 🚀 Getting Started Guide

**Step 1: Project Setup**
```bash
# Create new Spring Boot project using Spring Initializr
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2,security,validation \
  -d bootVersion=3.1.0 \
  -d javaVersion=17 \
  -d artifactId=task-manager-api \
  -d packageName=com.yourname.taskmanager \
  -o task-manager-api.zip

# Extract and import into IDE
unzip task-manager-api.zip
cd task-manager-api
```

**Step 2: Configure Application Properties**
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:h2:mem:taskdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
  security:
    user:
      name: admin
      password: password
      roles: USER

server:
  port: 8080

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

#### 📁 Recommended Project Structure
```
src/
├── main/
│   ├── java/com/yourname/taskmanager/
│   │   ├── TaskManagerApplication.java
│   │   ├── controller/
│   │   │   └── TaskController.java
│   │   ├── service/
│   │   │   ├── TaskService.java
│   │   │   └── TaskServiceImpl.java
│   │   ├── repository/
│   │   │   └── TaskRepository.java
│   │   ├── entity/
│   │   │   └── Task.java
│   │   ├── dto/
│   │   │   ├── TaskRequestDto.java
│   │   │   └── TaskResponseDto.java
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   └── exception/
│   │       ├── TaskNotFoundException.java
│   │       └── GlobalExceptionHandler.java
│   └── resources/
│       ├── application.yml
│       └── data.sql
└── test/
    └── java/com/yourname/taskmanager/
        ├── controller/
        │   └── TaskControllerTest.java
        ├── service/
        │   └── TaskServiceTest.java
        └── repository/
            └── TaskRepositoryTest.java
```

#### ⚙️ Implementation Steps

**Phase 1: Core Setup (Week 1)**
1. Create Spring Boot application
2. Set up H2 database connection
3. Create Task entity with JPA annotations
4. Implement TaskRepository interface
5. Create basic REST endpoints

**Phase 2: Business Logic (Week 1-2)**
1. Implement TaskService with business logic
2. Add validation using Bean Validation
3. Create DTOs for request/response
4. Add proper exception handling
5. Implement CRUD operations

**Phase 3: Security & Testing (Week 2-3)**
1. Configure Spring Security
2. Add authentication endpoints
3. Write unit tests for all layers
4. Add integration tests
5. Document API with OpenAPI

**Key Code Examples:**

```java
// Task Entity
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String title;
    
    @Size(max = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;
    
    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime dueDate;
    
    // Constructors, getters, setters
}

// Repository
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByDueDateBefore(LocalDateTime date);
    
    @Query("SELECT t FROM Task t WHERE t.title LIKE %:keyword% OR t.description LIKE %:keyword%")
    List<Task> findByKeyword(@Param("keyword") String keyword);
}

// Service
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    
    @Override
    public TaskResponseDto createTask(TaskRequestDto request) {
        Task task = taskMapper.toEntity(request);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    
    // Other CRUD methods...
}

// Controller
@RestController
@RequestMapping("/api/v1/tasks")
@Validated
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto request) {
        TaskResponseDto response = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        List<TaskResponseDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    // Other endpoints...
}
```

---

### 🟢 Project 2: Library Management System

#### 📋 Project Overview
**Duration:** 3-4 weeks  
**Description:** A web application for managing library books, members, and borrowing operations with a simple web interface.  
**Business Value:** Learn Spring MVC, Thymeleaf templating, and relationship mapping.

#### 🏗️ Architecture Design
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Web Browser   │───▶│  Spring Boot    │───▶│   MySQL/H2     │
│   (Thymeleaf)   │    │  MVC + REST     │    │   Database      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │ Spring Security │
                       │ (Form Login)    │
                       └─────────────────┘
```

#### 🛠️ Technology Stack
- **Core**: Spring Boot, Spring MVC, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, HTML/CSS
- **Security**: Spring Security (Form-based authentication)
- **Database**: MySQL (production), H2 (testing)
- **Validation**: Bean Validation (JSR-303)
- **Build**: Maven
- **Testing**: JUnit 5, TestContainers, Selenium

#### 📚 Required Knowledge
**Prerequisites:**
- Completion of Project 1 concepts
- Basic HTML/CSS knowledge
- Understanding of relational databases
- SQL fundamentals

**Spring Concepts to Learn:**
- Spring MVC architecture
- Thymeleaf template engine
- Form handling and validation
- JPA relationships (@OneToMany, @ManyToOne)
- Spring Security with form login

#### 🚀 Getting Started Guide

**Step 1: Database Setup**
```sql
-- MySQL setup script
CREATE DATABASE library_db;
CREATE USER 'library_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON library_db.* TO 'library_user'@'localhost';
```

**Step 2: Application Configuration**
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db
    username: library_user
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  thymeleaf:
    cache: false
    prefix: classpath:/templates/
    suffix: .html

server:
  port: 8080

logging:
  level:
    com.yourname.library: DEBUG
    org.springframework.security: DEBUG
```

#### 📁 Project Structure
```
src/
├── main/
│   ├── java/com/yourname/library/
│   │   ├── LibraryApplication.java
│   │   ├── controller/
│   │   │   ├── BookController.java
│   │   │   ├── MemberController.java
│   │   │   ├── BorrowingController.java
│   │   │   └── HomeController.java
│   │   ├── service/
│   │   │   ├── BookService.java
│   │   │   ├── MemberService.java
│   │   │   └── BorrowingService.java
│   │   ├── repository/
│   │   │   ├── BookRepository.java
│   │   │   ├── MemberRepository.java
│   │   │   └── BorrowingRepository.java
│   │   ├── entity/
│   │   │   ├── Book.java
│   │   │   ├── Member.java
│   │   │   ├── Borrowing.java
│   │   │   └── Category.java
│   │   ├── dto/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   └── WebConfig.java
│   │   └── exception/
│   └── resources/
│       ├── application.yml
│       ├── templates/
│       │   ├── layout/
│       │   │   ├── base.html
│       │   │   └── navbar.html
│       │   ├── book/
│       │   │   ├── list.html
│       │   │   ├── form.html
│       │   │   └── detail.html
│       │   ├── member/
│       │   ├── borrowing/
│       │   ├── login.html
│       │   └── index.html
│       ├── static/
│       │   ├── css/
│       │   ├── js/
│       │   └── images/
│       └── data.sql
```

#### ⚙️ Implementation Steps

**Phase 1: Entity Design (Week 1)**
```java
// Book Entity with relationships
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @NotBlank
    @Size(max = 100)
    private String author;
    
    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String isbn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings = new ArrayList<>();
    
    private boolean available = true;
    
    // Constructors, getters, setters
}

// Member Entity
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String name;
    
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    
    @NotBlank
    @Size(max = 15)
    private String phone;
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings = new ArrayList<>();
    
    private LocalDate membershipDate = LocalDate.now();
    
    // Constructors, getters, setters
}
```

**Phase 2: Web Interface (Week 2-3)**
```html
<!-- Thymeleaf template example: book list -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Library Management - Books</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav th:replace="~{layout/navbar :: navbar}"></nav>
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Books</h2>
            <a th:href="@{/books/new}" class="btn btn-primary">Add New Book</a>
        </div>
        
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>ISBN</th>
                            <th>Category</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr th:each="book : ${books}">
                            <td th:text="${book.title}"></td>
                            <td th:text="${book.author}"></td>
                            <td th:text="${book.isbn}"></td>
                            <td th:text="${book.category.name}"></td>
                            <td>
                                <span th:class="${book.available} ? 'badge bg-success' : 'badge bg-danger'"
                                      th:text="${book.available} ? 'Available' : 'Borrowed'"></span>
                            </td>
                            <td>
                                <a th:href="@{/books/{id}(id=${book.id})}" class="btn btn-sm btn-info">View</a>
                                <a th:href="@{/books/{id}/edit(id=${book.id})}" class="btn btn-sm btn-warning">Edit</a>
                                <a th:href="@{/books/{id}/delete(id=${book.id})}" class="btn btn-sm btn-danger"
                                   onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
```

---

### 🟢 Project 3: Weather Dashboard API

#### 📋 Project Overview
**Duration:** 2-3 weeks  
**Description:** An API that integrates with external weather services, provides caching, and stores historical data.  
**Business Value:** Learn external API integration, caching strategies, and scheduled tasks.

#### 🛠️ Technology Stack
- **Core**: Spring Boot, Spring Web, Spring Data JPA
- **Caching**: Redis, Spring Cache
- **Scheduling**: Spring Scheduler
- **External APIs**: OpenWeatherMap API, RestTemplate/WebClient
- **Database**: PostgreSQL
- **Build**: Maven
- **Testing**: WireMock, TestContainers

#### 📚 Required Knowledge
**Prerequisites:**
- Previous projects completed
- Understanding of REST clients
- Basic caching concepts
- JSON processing

**Spring Concepts to Learn:**
- RestTemplate and WebClient
- Spring Cache abstraction
- @Scheduled tasks
- External configuration
- Async processing with @Async

---

## 🚀 INTERMEDIATE LEVEL PROJECTS
*Focus: Advanced Spring Features, Microservices Basics, Real-world Applications*

### 🟡 Project 4: E-commerce Microservices Platform

#### 📋 Project Overview
**Duration:** 6-8 weeks  
**Description:** A complete e-commerce platform built with microservices architecture, including product catalog, order management, and payment processing.  
**Business Value:** Learn microservices patterns, inter-service communication, and distributed system challenges.

#### 🏗️ Architecture Design
```
                     ┌─────────────────┐
                     │   API Gateway   │
                     │  (Spring Cloud) │
                     └─────────┬───────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
┌───────▼───────┐    ┌─────────▼────────┐    ┌───────▼────────┐
│   Product      │    │   Order          │    │   Payment      │
│   Service      │    │   Service        │    │   Service      │
│   (Port 8081)  │    │   (Port 8082)    │    │   (Port 8083)  │
└───────┬───────┘    └─────────┬────────┘    └───────┬────────┘
        │                      │                      │
┌───────▼───────┐    ┌─────────▼────────┐    ┌───────▼────────┐
│   PostgreSQL  │    │   PostgreSQL     │    │   PostgreSQL   │
│   (Products)  │    │   (Orders)       │    │   (Payments)   │
└───────────────┘    └──────────────────┘    └────────────────┘
                               │
                     ┌─────────▼────────┐
                     │   Message Queue  │
                     │   (RabbitMQ)     │
                     └──────────────────┘
```

#### 🛠️ Technology Stack
- **Core**: Spring Boot, Spring Cloud (Gateway, Eureka, Config)
- **Security**: Spring Security, JWT, OAuth2
- **Database**: PostgreSQL (per service), Spring Data JPA
- **Messaging**: RabbitMQ, Spring Cloud Stream
- **Caching**: Redis
- **Monitoring**: Spring Boot Actuator, Micrometer
- **Testing**: TestContainers, Contract Testing
- **Build**: Maven (multi-module)
- **Containerization**: Docker, Docker Compose

#### 📚 Required Knowledge
**Prerequisites:**
- Solid understanding of Spring Boot
- Microservices concepts
- Database design
- REST API design
- Basic Docker knowledge

**Spring Concepts to Learn:**
- Spring Cloud ecosystem
- Service discovery (Eureka)
- API Gateway patterns
- Circuit breaker (Resilience4j)
- Event-driven architecture
- Configuration management

#### 🚀 Getting Started Guide

**Step 1: Multi-Module Maven Setup**
```xml
<!-- Parent pom.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.yourname.ecommerce</groupId>
    <artifactId>ecommerce-platform</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    
    <modules>
        <module>eureka-server</module>
        <module>api-gateway</module>
        <module>product-service</module>
        <module>order-service</module>
        <module>payment-service</module>
        <module>common</module>
    </modules>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <spring-boot.version>3.1.0</spring-boot.version>
        <spring-cloud.version>2022.0.3</spring-cloud.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

**Step 2: Service Discovery Setup**
```java
// Eureka Server
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

```yaml
# eureka-server/application.yml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

#### 📁 Project Structure
```
ecommerce-platform/
├── pom.xml
├── docker-compose.yml
├── eureka-server/
│   ├── pom.xml
│   └── src/main/java/.../EurekaServerApplication.java
├── api-gateway/
│   ├── pom.xml
│   └── src/main/java/.../ApiGatewayApplication.java
├── product-service/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/yourname/product/
│       │   ├── ProductServiceApplication.java
│       │   ├── controller/ProductController.java
│       │   ├── service/ProductService.java
│       │   ├── repository/ProductRepository.java
│       │   ├── entity/Product.java
│       │   └── config/
│       └── test/
├── order-service/
├── payment-service/
└── common/
    └── src/main/java/com/yourname/common/
        ├── dto/
        ├── exception/
        └── util/
```

#### ⚙️ Implementation Steps

**Phase 1: Infrastructure Setup (Week 1-2)**
1. Set up Eureka service discovery
2. Configure API Gateway with Spring Cloud Gateway
3. Create basic service templates
4. Set up Docker containers for databases
5. Configure inter-service communication

**Phase 2: Core Services (Week 3-5)**
1. Implement Product Service with full CRUD
2. Implement Order Service with business logic
3. Implement Payment Service with external integration
4. Add service-to-service communication
5. Implement event-driven communication with RabbitMQ

**Phase 3: Advanced Features (Week 6-8)**
1. Add security with JWT tokens
2. Implement distributed tracing
3. Add circuit breakers and resilience patterns
4. Create comprehensive testing strategy
5. Add monitoring and observability

**Key Code Examples:**

```java
// Product Service Controller
@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.getAllProducts(pageable, category);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductDto product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    
    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestBody UpdateStockRequest request) {
        productService.updateStock(id, request.getQuantity());
        return ResponseEntity.ok().build();
    }
}

// Order Service with Event Publishing
@Service
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final ApplicationEventPublisher eventPublisher;
    
    public OrderDto createOrder(CreateOrderRequest request) {
        // Validate product availability
        List<ProductDto> products = productServiceClient.getProducts(request.getProductIds());
        
        // Create order
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatus.PENDING);
        
        BigDecimal totalAmount = calculateTotalAmount(request.getItems(), products);
        order.setTotalAmount(totalAmount);
        
        Order savedOrder = orderRepository.save(order);
        
        // Publish order created event
        eventPublisher.publishEvent(new OrderCreatedEvent(savedOrder.getId(), savedOrder.getCustomerId()));
        
        return OrderMapper.toDto(savedOrder);
    }
    
    @EventListener
    public void handlePaymentProcessedEvent(PaymentProcessedEvent event) {
        if (event.isSuccessful()) {
            updateOrderStatus(event.getOrderId(), OrderStatus.CONFIRMED);
        } else {
            updateOrderStatus(event.getOrderId(), OrderStatus.CANCELLED);
        }
    }
}

// API Gateway Configuration
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .filters(f -> f.stripPrefix(0)
                                .circuitBreaker(config -> config.setName("product-service-cb")))
                        .uri("lb://product-service"))
                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .filters(f -> f.stripPrefix(0)
                                .circuitBreaker(config -> config.setName("order-service-cb")))
                        .uri("lb://order-service"))
                .route("payment-service", r -> r.path("/api/v1/payments/**")
                        .filters(f -> f.stripPrefix(0)
                                .circuitBreaker(config -> config.setName("payment-service-cb")))
                        .uri("lb://payment-service"))
                .build();
    }
}
```

---

### 🟡 Project 5: Real-time Chat Application

#### 📋 Project Overview
**Duration:** 4-5 weeks  
**Description:** A real-time chat application with WebSocket support, user presence, file sharing, and message history.  
**Business Value:** Learn WebSocket communication, real-time features, and scalable chat architecture.

#### 🛠️ Technology Stack
- **Core**: Spring Boot, Spring WebSocket, Spring Security
- **Real-time**: WebSocket, STOMP, SockJS
- **Database**: MongoDB (messages), Redis (sessions, presence)
- **File Storage**: AWS S3 or local file system
- **Frontend**: React/Vue.js (optional) or vanilla JavaScript
- **Testing**: WebSocket testing, integration tests

---

### 🟡 Project 6: Content Management System (CMS)

#### 📋 Project Overview
**Duration:** 5-6 weeks  
**Description:** A full-featured CMS with article management, user roles, media handling, and SEO optimization.  
**Business Value:** Learn advanced Spring Security, file handling, and content management patterns.

---

## 🔥 ADVANCED LEVEL PROJECTS
*Focus: Enterprise Architecture, Performance, Scalability, Production-Ready Applications*

### 🔴 Project 7: Multi-tenant SaaS Platform

#### 📋 Project Overview
**Duration:** 8-10 weeks  
**Description:** An enterprise-grade SaaS platform supporting multiple tenants with isolated data, custom configurations, and billing integration.  
**Business Value:** Learn multi-tenancy patterns, enterprise security, and SaaS architecture.

#### 🏗️ Architecture Design
```
                    ┌─────────────────┐
                    │   Load Balancer │
                    │   (Nginx/AWS)   │
                    └─────────┬───────┘
                              │
                    ┌─────────▼───────┐
                    │   API Gateway   │
                    │  + Tenant       │
                    │  Resolution     │
                    └─────────┬───────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
┌───────▼────────┐   ┌────────▼────────┐   ┌───────▼────────┐
│   Auth Service │   │  Tenant Service │   │  Core Service  │
│   (Multi-tenant│   │  (Metadata)     │   │  (Business)    │
│   + SSO)       │   │                 │   │                │
└───────┬────────┘   └────────┬────────┘   └───────┬────────┘
        │                     │                     │
        └─────────────────────┼─────────────────────┘
                              │
              ┌───────────────▼───────────────┐
              │      Database Cluster         │
              │  ┌─────────┬─────────────────┐│
              │  │Tenant A │ Tenant B │ ... ││
              │  │Schema   │ Schema   │     ││
              │  └─────────┴─────────────────┘│
              └───────────────────────────────┘
```

#### 🛠️ Technology Stack
- **Core**: Spring Boot, Spring Cloud, Spring Security
- **Multi-tenancy**: Hibernate Multi-tenancy, Schema per tenant
- **Database**: PostgreSQL with schema separation
- **Caching**: Redis with tenant isolation
- **Search**: Elasticsearch with tenant indexing
- **Monitoring**: Prometheus, Grafana, ELK Stack
- **Infrastructure**: Kubernetes, Helm charts
- **CI/CD**: Jenkins/GitHub Actions, Docker

#### 📚 Required Knowledge
**Prerequisites:**
- Advanced Spring Security knowledge
- Database administration skills
- Kubernetes basics
- Performance optimization
- Enterprise integration patterns

**Advanced Concepts:**
- Multi-tenancy strategies
- Data isolation patterns
- Horizontal scaling
- Performance monitoring
- Security compliance (SOC2, GDPR)

#### ⚙️ Implementation Steps

**Phase 1: Multi-tenant Foundation (Week 1-2)**
```java
// Tenant Context
@Component
public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    
    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }
    
    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }
    
    public static void clear() {
        CURRENT_TENANT.remove();
    }
}

// Multi-tenant Data Source Configuration
@Configuration
public class MultiTenantConfiguration {
    
    @Bean
    @Primary
    public DataSource dataSource() {
        return new MultiTenantDataSource();
    }
    
    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new SchemaBasedMultiTenantConnectionProvider();
    }
    
    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new TenantIdentifierResolver();
    }
}

// Tenant Interceptor
@Component
public class TenantInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = extractTenantFromRequest(request);
        if (tenantId != null) {
            TenantContext.setCurrentTenant(tenantId);
        }
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
    
    private String extractTenantFromRequest(HttpServletRequest request) {
        // Extract from subdomain, header, or JWT token
        String host = request.getServerName();
        return host.split("\\.")[0]; // subdomain approach
    }
}
```

**Phase 2: Business Logic & APIs (Week 3-6)**
**Phase 3: Security & Compliance (Week 7-8)**
**Phase 4: Monitoring & Deployment (Week 9-10)**

---

### 🔴 Project 8: High-Performance Trading System

#### 📋 Project Overview
**Duration:** 10-12 weeks  
**Description:** Ultra-low latency trading system with real-time market data processing, order matching, and risk management.  
**Business Value:** Learn performance optimization, concurrent programming, and financial system design.

#### 🛠️ Technology Stack
- **Core**: Spring Boot with custom optimizations
- **Performance**: Chronicle Map, Disruptor, Aeron
- **Real-time**: WebSocket, UDP multicast
- **Database**: TimescaleDB, Redis for hot data
- **Messaging**: Apache Kafka, custom binary protocols
- **Monitoring**: Low-latency monitoring stack

---

### 🔴 Project 9: IoT Data Processing Platform

#### 📋 Project Overview
**Duration:** 8-10 weeks  
**Description:** Platform for processing millions of IoT device messages with real-time analytics, alerting, and device management.  
**Business Value:** Learn stream processing, time-series data, and IoT protocols.

---

## 📋 Project Development Best Practices

### 🎯 Architecture Design Principles

#### **1. Layered Architecture**
```
┌─────────────────────────────────┐
│         Presentation Layer       │ ← Controllers, REST endpoints
├─────────────────────────────────┤
│         Service Layer           │ ← Business logic, transactions
├─────────────────────────────────┤
│         Repository Layer        │ ← Data access, JPA repositories
├─────────────────────────────────┤
│         Domain Layer            │ ← Entities, value objects
└─────────────────────────────────┘
```

#### **2. Clean Code Practices**
- Use meaningful naming conventions
- Keep methods small and focused (< 20 lines)
- Apply SOLID principles
- Use dependency injection consistently
- Implement proper exception handling

#### **3. Testing Strategy**
```
┌─────────────────┐   70%  │ Unit Tests
├─────────────────┤   20%  │ Integration Tests  
├─────────────────┤   10%  │ End-to-End Tests
└─────────────────┘
```

### 📊 Performance Guidelines

#### **Database Optimization**
- Use connection pooling (HikariCP)
- Implement proper indexing
- Use lazy loading for associations
- Implement caching strategies
- Monitor query performance

#### **Caching Strategy**
```java
@Service
public class ProductService {
    
    @Cacheable(value = "products", key = "#id")
    public Product findById(Long id) {
        return productRepository.findById(id);
    }
    
    @CacheEvict(value = "products", key = "#product.id")
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
```

### 🔐 Security Best Practices

#### **Authentication & Authorization**
- Use JWT tokens for stateless authentication
- Implement role-based access control (RBAC)
- Apply method-level security
- Use HTTPS everywhere
- Implement CSRF protection

#### **Data Protection**
- Hash passwords with BCrypt
- Sanitize input data
- Use parameterized queries
- Implement audit logging
- Follow OWASP guidelines

### 📈 Monitoring & Observability

#### **Application Metrics**
```java
@RestController
public class ProductController {
    
    private final MeterRegistry meterRegistry;
    
    @GetMapping("/products")
    @Timed(name = "product.get.time", description = "Product retrieval time")
    public ResponseEntity<List<Product>> getProducts() {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            List<Product> products = productService.findAll();
            meterRegistry.counter("product.get.success").increment();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            meterRegistry.counter("product.get.error").increment();
            throw e;
        } finally {
            sample.stop(Timer.builder("product.get.duration").register(meterRegistry));
        }
    }
}
```

### 🚀 Deployment Strategies

#### **Containerization with Docker**
```dockerfile
FROM openjdk:17-jre-slim

COPY target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
```

#### **Kubernetes Deployment**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: spring-app
  template:
    metadata:
      labels:
        app: spring-app
    spec:
      containers:
      - name: spring-app
        image: your-registry/spring-app:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "production"
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
```

---

## 🎓 Portfolio & Career Development

### 📈 Skill Progression Matrix

| Level | Duration | Skills Developed | Portfolio Projects |
|-------|----------|------------------|-------------------|
| **Beginner** | 2-3 months | Spring Core, REST APIs, Basic Security | 3 projects |
| **Intermediate** | 4-6 months | Microservices, Advanced Security, Real-time | 3 projects |
| **Advanced** | 6-8 months | Enterprise Architecture, Performance, Production | 3 projects |

### 🎯 Graduation Requirements

#### **Technical Mastery**
- [ ] Complete 9 projects across all difficulty levels
- [ ] Deploy 3+ projects to cloud platforms
- [ ] Achieve >90% test coverage in all projects
- [ ] Implement CI/CD pipelines
- [ ] Document architecture decisions (ADRs)

#### **Professional Skills**
- [ ] Create technical blog with 10+ Spring articles
- [ ] Contribute to 2+ open source Spring projects
- [ ] Present at local meetup or conference
- [ ] Build professional network in Spring community
- [ ] Mentor junior developers

#### **Industry Recognition**
- [ ] Obtain Spring Professional certification
- [ ] Achieve 1000+ GitHub stars across projects
- [ ] Receive positive feedback from code reviews
- [ ] Land senior Spring developer position
- [ ] Build reputation as Spring expert

### 💼 Career Opportunities

#### **Role Progression**
```
Spring Developer ($70K-90K)
        ↓
Senior Spring Developer ($90K-120K)
        ↓
Spring Architect ($120K-160K)
        ↓
Principal Engineer ($160K-220K+)
```

#### **Specialization Paths**
- **Enterprise Architect**: Focus on large-scale system design
- **Cloud Architect**: Specialize in cloud-native Spring applications
- **Security Specialist**: Expert in Spring Security and application security
- **Performance Engineer**: Optimize high-throughput Spring applications

---

## 🚀 Success Strategies

### 📚 Continuous Learning
- Follow Spring team blogs and releases
- Participate in Spring community forums
- Attend SpringOne conference (virtual/in-person)
- Practice with Spring Boot kata exercises
- Study Spring Framework source code

### 🤝 Community Engagement
- Join Spring Slack communities
- Contribute to Spring projects on GitHub
- Answer questions on Stack Overflow
- Share knowledge through blogging
- Speak at local Java/Spring meetups

### 🎯 Next Steps After Completion
1. **Specialize** in a specific domain (security, cloud, performance)
2. **Contribute** to Spring ecosystem projects
3. **Teach** others through content creation
4. **Lead** technical initiatives at work
5. **Innovate** by building novel Spring-based solutions

---

## 🎉 Conclusion

This comprehensive guide provides a structured path to mastering Spring Framework through hands-on projects. Each project builds upon previous knowledge while introducing new concepts and real-world challenges.

### 🌟 Key Takeaways
- **Practice consistently** with progressively complex projects
- **Focus on quality** over quantity in your implementations
- **Document everything** for future reference and portfolio
- **Test thoroughly** with comprehensive test suites
- **Deploy publicly** to demonstrate production readiness
- **Share knowledge** to contribute to the community

### 💡 Remember
The journey to Spring mastery is iterative. Each project teaches valuable lessons that compound over time. Stay curious, keep building, and don't hesitate to experiment with new features and patterns.

**Good luck on your journey to becoming a Spring Framework expert!** 🍃✨

---

*Happy coding with Spring!* 🌱
