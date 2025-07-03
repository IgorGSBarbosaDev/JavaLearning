package Spring;

/*
=== SPRING BOOT - MODERN SPRING DEVELOPMENT ===
Spring Boot makes it easy to create stand-alone, production-grade Spring applications

KEY FEATURES:
- Auto-configuration - Automatically configures Spring application based on dependencies
- Embedded servers - No need for external Tomcat/Jetty
- Starter dependencies - Pre-configured dependency groups
- Production-ready features - Health checks, metrics, monitoring
- No XML configuration - All configuration via Java annotations

WHEN TO USE SPRING BOOT:
1. Microservices - Quick setup of independent services
2. REST APIs - Easy creation of web services
3. Web Applications - Full-stack applications with minimal configuration
4. Rapid Prototyping - Quick development of proof-of-concepts
5. Cloud Applications - Applications deployed to cloud platforms
*/

// Note: These imports would be available with Spring Boot starter dependencies
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.data.jpa.repository.JpaRepository;
// import javax.persistence.*;

/**
 * SPRING BOOT APPLICATION STRUCTURE
 * 
 * @SpringBootApplication combines:
 * - @Configuration: Marks class as configuration source
 * - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration
 * - @ComponentScan: Enables component scanning
 */
// @SpringBootApplication
public class SpringBootExample {
    
    /*
    public static void main(String[] args) {
        SpringApplication.run(SpringBootExample.class, args);
    }
    */
    
    public static void main(String[] args) {
        System.out.println("=== SPRING BOOT CONCEPTS AND EXAMPLES ===\n");
        demonstrateSpringBootConcepts();
    }
    
    public static void demonstrateSpringBootConcepts() {
        System.out.println("1. SPRING BOOT AUTO-CONFIGURATION:");
        System.out.println("   - Automatically configures DataSource if database driver found");
        System.out.println("   - Sets up JPA repositories if Spring Data JPA on classpath");
        System.out.println("   - Configures embedded Tomcat if spring-boot-starter-web included");
        System.out.println("   - Sets up security if Spring Security on classpath\n");
        
        System.out.println("2. SPRING BOOT STARTERS:");
        System.out.println("   - spring-boot-starter-web: For web applications");
        System.out.println("   - spring-boot-starter-data-jpa: For JPA/Hibernate");
        System.out.println("   - spring-boot-starter-security: For security");
        System.out.println("   - spring-boot-starter-test: For testing\n");
        
        System.out.println("3. PROPERTY CONFIGURATION:");
        System.out.println("   - application.properties or application.yml");
        System.out.println("   - Profile-specific properties (application-dev.properties)");
        System.out.println("   - Environment variables and command line arguments\n");
    }
}

/**
 * REST CONTROLLER EXAMPLE
 * WHEN TO USE: For creating REST APIs, microservices
 */
// @RestController
// @RequestMapping("/api/products")
class ProductController {
    
    // @Autowired
    private ProductService productService;
    
    /**
     * GET /api/products - Get all products
     * SCENARIO: E-commerce product catalog
     */
    // @GetMapping
    public String getAllProducts() {
        return "List of all products";
        // return productService.findAll();
    }
    
    /**
     * GET /api/products/{id} - Get product by ID
     * SCENARIO: Product detail page
     */
    // @GetMapping("/{id}")
    public String getProduct(Long id) {
        return "Product with ID: " + id;
        // return productService.findById(id);
    }
    
    /**
     * POST /api/products - Create new product
     * SCENARIO: Admin product management
     */
    // @PostMapping
    public String createProduct(Product product) {
        return "Created product: " + product.getName();
        // return productService.save(product);
    }
    
    /**
     * PUT /api/products/{id} - Update product
     * SCENARIO: Product information updates
     */
    // @PutMapping("/{id}")
    public String updateProduct(Long id, Product product) {
        return "Updated product " + id + ": " + product.getName();
        // return productService.update(id, product);
    }
    
    /**
     * DELETE /api/products/{id} - Delete product
     * SCENARIO: Product discontinuation
     */
    // @DeleteMapping("/{id}")
    public String deleteProduct(Long id) {
        return "Deleted product with ID: " + id;
        // productService.delete(id);
    }
}

/**
 * JPA ENTITY EXAMPLE
 * WHEN TO USE: For database persistence with JPA/Hibernate
 */
// @Entity
// @Table(name = "products")
class Product {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column(nullable = false)
    private String name;
    
    // @Column(precision = 10, scale = 2)
    private Double price;
    
    private String description;
    private String category;
    
    // @CreationTimestamp
    private java.time.LocalDateTime createdAt;
    
    // @UpdateTimestamp
    private java.time.LocalDateTime updatedAt;
    
    // Constructors
    public Product() {}
    
    public Product(String name, Double price, String description, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
}

/**
 * JPA REPOSITORY EXAMPLE
 * WHEN TO USE: For database operations with minimal code
 */
// @Repository
interface ProductRepository /* extends JpaRepository<Product, Long> */ {
    
    // Spring Data JPA automatically implements these methods:
    // - save(Product product)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - count()
    
    // Custom query methods (Spring Data JPA derives queries from method names):
    // List<Product> findByCategory(String category);
    // List<Product> findByNameContaining(String name);
    // List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    // List<Product> findByCategoryAndPriceLessThan(String category, Double price);
    
    // Custom queries using @Query annotation:
    // @Query("SELECT p FROM Product p WHERE p.price > ?1 ORDER BY p.price DESC")
    // List<Product> findExpensiveProducts(Double minPrice);
    
    // @Query(value = "SELECT * FROM products WHERE category = ?1", nativeQuery = true)
    // List<Product> findProductsByCategory(String category);
}

/**
 * SERVICE LAYER WITH BUSINESS LOGIC
 * WHEN TO USE: For complex business operations, transaction management
 */
// @Service
// @Transactional
class ProductService {
    
    // @Autowired
    private ProductRepository productRepository;
    
    /**
     * Find all products
     * SCENARIO: Product catalog display
     */
    public String findAll() {
        return "All products from database";
        // return productRepository.findAll();
    }
    
    /**
     * Find product by ID
     * SCENARIO: Product detail view
     */
    public String findById(Long id) {
        return "Product with ID: " + id;
        /*
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        */
    }
    
    /**
     * Create new product with validation
     * SCENARIO: Admin adds new product to catalog
     */
    public String save(Product product) {
        // Business logic and validation
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        
        return "Saved product: " + product.getName();
        // return productRepository.save(product);
    }
    
    /**
     * Update existing product
     * SCENARIO: Product information updates
     */
    public String update(Long id, Product productDetails) {
        /*
        Product product = findById(id);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
        */
        return "Updated product " + id;
    }
    
    /**
     * Delete product
     * SCENARIO: Product discontinuation
     */
    public void delete(Long id) {
        /*
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        */
        System.out.println("Deleted product " + id);
    }
    
    /**
     * Find products by category
     * SCENARIO: Category-based filtering
     */
    public String findByCategory(String category) {
        return "Products in category: " + category;
        // return productRepository.findByCategory(category);
    }
    
    /**
     * Search products by name
     * SCENARIO: Product search functionality
     */
    public String searchByName(String name) {
        return "Products containing: " + name;
        // return productRepository.findByNameContaining(name);
    }
}

/**
 * CONFIGURATION CLASS EXAMPLE
 * WHEN TO USE: For custom beans, third-party library configuration
 */
// @Configuration
class AppConfiguration {
    
    /**
     * Database configuration
     * SCENARIO: Custom database connection settings
     */
    // @Bean
    // @ConfigurationProperties(prefix = "spring.datasource")
    public String dataSource() {
        return "Custom DataSource configuration";
        /*
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(20);
        return new HikariDataSource(config);
        */
    }
    
    /**
     * Web configuration
     * SCENARIO: Custom CORS settings, interceptors
     */
    // @Bean
    public String webMvcConfigurer() {
        return "Custom Web MVC configuration";
        /*
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowCredentials(true);
            }
        };
        */
    }
}

/**
 * EXCEPTION HANDLING
 * WHEN TO USE: For centralized error handling in REST APIs
 */
// @RestControllerAdvice
class GlobalExceptionHandler {
    
    /**
     * Handle validation errors
     * SCENARIO: Invalid input data from client
     */
    // @ExceptionHandler(IllegalArgumentException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationError(IllegalArgumentException ex) {
        return "Validation Error: " + ex.getMessage();
        /*
        return new ErrorResponse(
            "VALIDATION_ERROR",
            ex.getMessage(),
            LocalDateTime.now()
        );
        */
    }
    
    /**
     * Handle not found errors
     * SCENARIO: Resource doesn't exist
     */
    // @ExceptionHandler(ProductNotFoundException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception ex) {
        return "Not Found: " + ex.getMessage();
    }
    
    /**
     * Handle general errors
     * SCENARIO: Unexpected server errors
     */
    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneral(Exception ex) {
        return "Server Error: " + ex.getMessage();
    }
}

/**
 * TESTING EXAMPLE
 * WHEN TO USE: For unit and integration testing
 */
// @SpringBootTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest {
    
    // @MockBean
    private ProductRepository productRepository;
    
    // @Autowired
    private ProductService productService;
    
    // @Test
    public void testCreateProduct() {
        System.out.println("Testing product creation...");
        
        // Arrange
        Product product = new Product("Test Product", 99.99, "Test Description", "Electronics");
        
        // Act
        String result = productService.save(product);
        
        // Assert
        System.out.println("Test result: " + result);
        
        /*
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        Product result = productService.save(product);
        
        assertThat(result.getName()).isEqualTo("Test Product");
        verify(productRepository).save(product);
        */
    }
}
