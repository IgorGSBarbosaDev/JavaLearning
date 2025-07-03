package Spring;

/*
=== COMPREHENSIVE SPRING FRAMEWORK GUIDE ===
A complete guide to Spring Framework with examples, usage patterns, and best practices

Spring Framework is a comprehensive programming and configuration model for modern Java-based enterprise applications.
It provides infrastructure support for developing Java applications with features like:
- Dependency Injection (DI)
- Aspect-Oriented Programming (AOP)
- Data Access Integration
- Web MVC Framework
- Security
- Testing Support

RECOMMENDED SCENARIOS TO USE SPRING:
1. Enterprise Applications - Large-scale business applications
2. Web Applications - REST APIs, web services, microservices
3. Data-heavy Applications - Database operations, data processing
4. Integration Projects - Connecting different systems and services
5. Testing-heavy Projects - Applications requiring extensive unit/integration testing
6. Cloud-native Applications - Microservices, containerized applications
*/

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

// ====================================
// 1. SPRING CORE - DEPENDENCY INJECTION
// ====================================

/**
 * WHAT IS DEPENDENCY INJECTION?
 * DI is a design pattern where objects don't create their dependencies directly.
 * Instead, dependencies are provided (injected) by an external source (Spring Container).
 * 
 * BENEFITS:
 * - Loose coupling between classes
 * - Easy testing (mock dependencies)
 * - Better maintainability
 * - Separation of concerns
 * 
 * WHEN TO USE:
 * - When you have classes that depend on other classes
 * - When you want to make your code testable
 * - When building layered architectures (Controller -> Service -> Repository)
 */

// Example: Without Dependency Injection (Tight Coupling)
class BadExample {
    private DatabaseService dbService;
    
    public BadExample() {
        // Tight coupling - hard to test, hard to change
        this.dbService = new DatabaseService();
    }
}

// Example: With Dependency Injection (Loose Coupling)
@Component
class GoodExample {
    private final DatabaseService dbService;
    
    // Constructor injection (recommended)
    @Autowired
    public GoodExample(DatabaseService dbService) {
        this.dbService = dbService;
    }
    
    public void doSomething() {
        dbService.save("data");
    }
}

// ====================================
// 2. SPRING ANNOTATIONS EXPLAINED
// ====================================

/**
 * @Component - Generic stereotype for any Spring-managed component
 * WHEN TO USE: For general-purpose beans that don't fit specific stereotypes
 */
@Component
class GenericComponent {
    public void doWork() {
        System.out.println("Generic component doing work");
    }
}

/**
 * @Service - Indicates that this class contains business logic
 * WHEN TO USE: For service layer classes that contain business logic
 */
@Service
class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(String name, String email) {
        // Business logic here
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        User user = new User(name, email);
        return userRepository.save(user);
    }
    
    public User findUser(Long id) {
        return userRepository.findById(id);
    }
}

/**
 * @Repository - Indicates that this class is responsible for data access
 * WHEN TO USE: For data access layer classes (DAOs)
 */
@Repository
class UserRepository {
    
    public User save(User user) {
        // Database save logic here
        System.out.println("Saving user: " + user.getName());
        return user;
    }
    
    public User findById(Long id) {
        // Database query logic here
        System.out.println("Finding user with id: " + id);
        return new User("Found User", "found@example.com");
    }
}

/**
 * @Controller - Indicates that this class handles web requests
 * WHEN TO USE: For web layer classes that handle HTTP requests
 */
// Note: This would typically be in a web application
// @Controller
class UserController {
    
    @Autowired
    private UserService userService;
    
    // @GetMapping("/users/{id}")
    public User getUser(Long id) {
        return userService.findUser(id);
    }
    
    // @PostMapping("/users")
    public User createUser(String name, String email) {
        return userService.createUser(name, email);
    }
}

// ====================================
// 3. CONFIGURATION CLASSES
// ====================================

/**
 * @Configuration - Indicates that this class contains @Bean definitions
 * WHEN TO USE: 
 * - When you need to create beans programmatically
 * - When configuring third-party libraries
 * - When you need complex bean creation logic
 */
@Configuration
@ComponentScan(basePackages = "Spring") // Scan for components in this package
class AppConfig {
    
    /**
     * @Bean - Creates a Spring-managed bean
     * WHEN TO USE:
     * - For third-party classes you can't annotate
     * - When you need custom initialization logic
     * - For configuration objects
     */
    @Bean
    public DatabaseService databaseService() {
        DatabaseService service = new DatabaseService();
        service.setConnectionTimeout(30);
        return service;
    }
    
    @Bean
    public EmailService emailService() {
        return new EmailService("smtp.example.com", 587);
    }
}

// ====================================
// 4. PROPERTY INJECTION
// ====================================

/**
 * @Value - Injects values from properties files
 * WHEN TO USE:
 * - For configuration values
 * - For environment-specific settings
 * - For external API keys, URLs, etc.
 */
@Service
class ConfigurationService {
    
    @Value("${app.name:DefaultApp}")
    private String applicationName;
    
    @Value("${database.url:jdbc:mysql://localhost:3306/defaultdb}")
    private String databaseUrl;
    
    @Value("${email.enabled:false}")
    private boolean emailEnabled;
    
    public void printConfiguration() {
        System.out.println("App Name: " + applicationName);
        System.out.println("Database URL: " + databaseUrl);
        System.out.println("Email Enabled: " + emailEnabled);
    }
}

// ====================================
// 5. REAL-WORLD EXAMPLE: E-COMMERCE SYSTEM
// ====================================

/**
 * SCENARIO: Building an e-commerce order processing system
 * LAYERS: Controller -> Service -> Repository
 * BENEFITS: Each layer has single responsibility, easy to test, maintainable
 */

// Domain Model
class Order {
    private Long id;
    private String customerName;
    private double totalAmount;
    private String status;
    
    public Order(String customerName, double totalAmount) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = "PENDING";
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// Repository Layer - Data Access
@Repository
class OrderRepository {
    
    public Order save(Order order) {
        // In real app: save to database
        System.out.println("Saving order to database: " + order.getCustomerName());
        order.setId(System.currentTimeMillis()); // Simulate ID generation
        return order;
    }
    
    public Order findById(Long id) {
        // In real app: query database
        System.out.println("Finding order by ID: " + id);
        return new Order("John Doe", 99.99);
    }
}

// Service Layer - Business Logic
@Service
class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private EmailService emailService;
    
    /**
     * Process order with business logic
     * WHEN TO USE: When you have multi-step business processes
     */
    public Order processOrder(String customerName, double amount) {
        // 1. Validate order
        if (amount <= 0) {
            throw new IllegalArgumentException("Order amount must be positive");
        }
        
        // 2. Create order
        Order order = new Order(customerName, amount);
        
        // 3. Process payment
        boolean paymentSuccess = paymentService.processPayment(amount);
        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }
        
        // 4. Save order
        order.setStatus("PAID");
        order = orderRepository.save(order);
        
        // 5. Send confirmation email
        emailService.sendOrderConfirmation(customerName, order.getId());
        
        return order;
    }
    
    public Order getOrder(Long id) {
        return orderRepository.findById(id);
    }
}

// Supporting Services
@Service
class PaymentService {
    
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount);
        // In real app: integrate with payment gateway
        return amount > 0; // Simulate success
    }
}

@Service
class EmailService {
    private String smtpHost;
    private int smtpPort;
    
    public EmailService() {}
    
    public EmailService(String smtpHost, int smtpPort) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }
    
    public void sendOrderConfirmation(String customerEmail, Long orderId) {
        System.out.println("Sending confirmation email for order " + orderId + " to " + customerEmail);
        // In real app: send actual email
    }
}

// Supporting classes
class DatabaseService {
    private int connectionTimeout = 10;
    
    public void save(String data) {
        System.out.println("Saving to database: " + data);
    }
    
    public void setConnectionTimeout(int timeout) {
        this.connectionTimeout = timeout;
    }
}

class User {
    private String name;
    private String email;
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public String getName() { return name; }
    public String getEmail() { return email; }
}

// ====================================
// 6. MAIN CLASS - DEMONSTRATION
// ====================================

public class test {
    
    public static void main(String[] args) {
        System.out.println("=== SPRING FRAMEWORK DEMONSTRATION ===\n");
        
        // Create Spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        System.out.println("1. DEPENDENCY INJECTION EXAMPLE:");
        // Get beans from Spring container
        UserService userService = context.getBean(UserService.class);
        User user = userService.createUser("John Doe", "john@example.com");
        System.out.println("Created user: " + user.getName() + "\n");
        
        System.out.println("2. CONFIGURATION SERVICE EXAMPLE:");
        ConfigurationService configService = context.getBean(ConfigurationService.class);
        configService.printConfiguration();
        System.out.println();
        
        System.out.println("3. E-COMMERCE ORDER PROCESSING EXAMPLE:");
        OrderService orderService = context.getBean(OrderService.class);
        try {
            Order order = orderService.processOrder("Alice Smith", 149.99);
            System.out.println("Order processed successfully! Order ID: " + order.getId());
            System.out.println("Order Status: " + order.getStatus());
        } catch (Exception e) {
            System.out.println("Order processing failed: " + e.getMessage());
        }
        
        System.out.println("\n=== SPRING BENEFITS DEMONSTRATED ===");
        System.out.println("✓ Dependency Injection - Objects created and wired automatically");
        System.out.println("✓ Separation of Concerns - Each class has single responsibility");
        System.out.println("✓ Loose Coupling - Services can be easily replaced/mocked");
        System.out.println("✓ Configuration Management - External properties injection");
        System.out.println("✓ Transaction Management - Can be added with @Transactional");
        System.out.println("✓ Testing Support - Easy to unit test with mocked dependencies");
    }
}
