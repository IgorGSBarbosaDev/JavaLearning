package Spring;

/*
=== SPRING DATA JPA - DATABASE ACCESS MADE EASY ===
Spring Data JPA provides a powerful abstraction over JPA to simplify database operations

KEY FEATURES:
- Repository pattern implementation
- Automatic query generation from method names
- Custom queries with @Query annotation
- Pagination and sorting support
- Auditing capabilities
- Specification-based queries

WHEN TO USE SPRING DATA JPA:
1. CRUD Operations - Basic database operations
2. Complex Queries - Custom queries for business needs
3. Data Pagination - Large dataset handling
4. Audit Trails - Tracking data changes
5. Multi-database Applications - Abstract database operations
*/

// Note: These imports would be available with Spring Data JPA dependencies
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import javax.persistence.*;

/**
 * ENTITY RELATIONSHIPS EXAMPLE
 * WHEN TO USE: For modeling real-world business relationships
 */

// Customer Entity (One-to-Many with Orders)
// @Entity
// @Table(name = "customers")
class Customer {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column(nullable = false)
    private String firstName;
    
    // @Column(nullable = false)
    private String lastName;
    
    // @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;
    private String address;
    
    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<Order> orders = new java.util.ArrayList<>();
    
    // @CreationTimestamp
    private java.time.LocalDateTime createdAt;
    
    // @UpdateTimestamp
    private java.time.LocalDateTime updatedAt;
    
    // Constructors
    public Customer() {}
    
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Helper methods for bidirectional relationship
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }
    
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public java.util.List<Order> getOrders() { return orders; }
    public void setOrders(java.util.List<Order> orders) { this.orders = orders; }
    
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

// Order Entity (Many-to-One with Customer, One-to-Many with OrderItems)
// @Entity
// @Table(name = "orders")
class Order {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<OrderItem> orderItems = new java.util.ArrayList<>();
    
    // @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    
    private java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
    
    // @CreationTimestamp
    private java.time.LocalDateTime orderDate;
    
    private java.time.LocalDateTime shippedDate;
    private java.time.LocalDateTime deliveredDate;
    
    // Constructors
    public Order() {}
    
    public Order(Customer customer) {
        this.customer = customer;
    }
    
    // Helper methods
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        calculateTotalAmount();
    }
    
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
        calculateTotalAmount();
    }
    
    private void calculateTotalAmount() {
        totalAmount = orderItems.stream()
            .map(OrderItem::getSubtotal)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public java.util.List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(java.util.List<OrderItem> orderItems) { this.orderItems = orderItems; }
    
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    
    public java.math.BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(java.math.BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public java.time.LocalDateTime getOrderDate() { return orderDate; }
    public java.time.LocalDateTime getShippedDate() { return shippedDate; }
    public void setShippedDate(java.time.LocalDateTime shippedDate) { this.shippedDate = shippedDate; }
    public java.time.LocalDateTime getDeliveredDate() { return deliveredDate; }
    public void setDeliveredDate(java.time.LocalDateTime deliveredDate) { this.deliveredDate = deliveredDate; }
}

// OrderItem Entity (Many-to-One with Order and Product)
// @Entity
// @Table(name = "order_items")
class OrderItem {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    private Integer quantity;
    private java.math.BigDecimal unitPrice;
    
    // Constructors
    public OrderItem() {}
    
    public OrderItem(Order order, Product product, Integer quantity, java.math.BigDecimal unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Calculated field
    public java.math.BigDecimal getSubtotal() {
        return unitPrice.multiply(java.math.BigDecimal.valueOf(quantity));
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public java.math.BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(java.math.BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}

// Product Entity (for reference)
// @Entity
// @Table(name = "products")
class Product {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private java.math.BigDecimal price;
    private String category;
    private Integer stockQuantity;
    
    // Constructors
    public Product() {}
    
    public Product(String name, java.math.BigDecimal price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public java.math.BigDecimal getPrice() { return price; }
    public void setPrice(java.math.BigDecimal price) { this.price = price; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}

// Enum for Order Status
enum OrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}

/**
 * REPOSITORY INTERFACES WITH SPRING DATA JPA
 * WHEN TO USE: For data access operations with minimal code
 */

// Customer Repository
// @Repository
interface CustomerRepository /* extends JpaRepository<Customer, Long> */ {
    
    // ========================================
    // QUERY METHODS - Spring Data JPA derives queries from method names
    // ========================================
    
    /**
     * Find customer by email
     * SCENARIO: User login, password reset
     * Generated Query: SELECT * FROM customers WHERE email = ?
     */
    // Optional<Customer> findByEmail(String email);
    
    /**
     * Find customers by first name (case-insensitive)
     * SCENARIO: Customer search functionality
     * Generated Query: SELECT * FROM customers WHERE UPPER(first_name) = UPPER(?)
     */
    // List<Customer> findByFirstNameIgnoreCase(String firstName);
    
    /**
     * Find customers by first name and last name
     * SCENARIO: Full name search
     * Generated Query: SELECT * FROM customers WHERE first_name = ? AND last_name = ?
     */
    // List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Find customers with email containing domain
     * SCENARIO: Find customers from specific email domains
     * Generated Query: SELECT * FROM customers WHERE email LIKE %?%
     */
    // List<Customer> findByEmailContaining(String domain);
    
    /**
     * Find customers created after specific date
     * SCENARIO: Recent customer analysis
     * Generated Query: SELECT * FROM customers WHERE created_at > ?
     */
    // List<Customer> findByCreatedAtAfter(LocalDateTime date);
    
    /**
     * Count customers by email domain
     * SCENARIO: Analytics on customer email providers
     * Generated Query: SELECT COUNT(*) FROM customers WHERE email LIKE %?%
     */
    // long countByEmailContaining(String domain);
    
    /**
     * Check if customer exists by email
     * SCENARIO: Registration validation
     * Generated Query: SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM customers WHERE email = ?
     */
    // boolean existsByEmail(String email);
    
    // ========================================
    // CUSTOM QUERIES WITH @Query ANNOTATION
    // ========================================
    
    /**
     * Find customers with orders above certain amount
     * SCENARIO: VIP customer identification
     */
    // @Query("SELECT DISTINCT c FROM Customer c JOIN c.orders o WHERE o.totalAmount > :amount")
    // List<Customer> findCustomersWithOrdersAbove(@Param("amount") BigDecimal amount);
    
    /**
     * Find customers by phone area code
     * SCENARIO: Regional customer analysis
     */
    // @Query("SELECT c FROM Customer c WHERE c.phone LIKE CONCAT(:areaCode, '%')")
    // List<Customer> findByPhoneAreaCode(@Param("areaCode") String areaCode);
    
    /**
     * Get customer statistics
     * SCENARIO: Dashboard analytics
     */
    // @Query("SELECT new spring.dto.CustomerStats(COUNT(c), AVG(SIZE(c.orders)), MAX(c.createdAt)) FROM Customer c")
    // CustomerStats getCustomerStatistics();
    
    /**
     * Native SQL query example
     * SCENARIO: Complex database-specific queries
     */
    // @Query(value = "SELECT * FROM customers c WHERE c.created_at >= DATE_SUB(NOW(), INTERVAL :days DAY)", nativeQuery = true)
    // List<Customer> findRecentCustomers(@Param("days") int days);
    
    // ========================================
    // PAGINATION AND SORTING
    // ========================================
    
    /**
     * Find customers with pagination
     * SCENARIO: Customer list with pagination
     */
    // Page<Customer> findByLastNameContaining(String lastName, Pageable pageable);
    
    /**
     * Find customers ordered by creation date
     * SCENARIO: Recent customers first
     */
    // List<Customer> findByFirstNameOrderByCreatedAtDesc(String firstName);
}

// Order Repository
// @Repository
interface OrderRepository /* extends JpaRepository<Order, Long> */ {
    
    /**
     * Find orders by customer
     * SCENARIO: Customer order history
     */
    // List<Order> findByCustomer(Customer customer);
    
    /**
     * Find orders by customer ID
     * SCENARIO: Order lookup without loading customer entity
     */
    // List<Order> findByCustomerId(Long customerId);
    
    /**
     * Find orders by status
     * SCENARIO: Order status filtering
     */
    // List<Order> findByStatus(OrderStatus status);
    
    /**
     * Find orders between dates
     * SCENARIO: Date range reporting
     */
    // List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find orders with total amount greater than specified value
     * SCENARIO: High-value order analysis
     */
    // List<Order> findByTotalAmountGreaterThan(BigDecimal amount);
    
    /**
     * Custom query: Monthly sales report
     * SCENARIO: Business intelligence reporting
     */
    // @Query("SELECT new spring.dto.MonthlySales(YEAR(o.orderDate), MONTH(o.orderDate), SUM(o.totalAmount), COUNT(o)) " +
    //        "FROM Order o WHERE o.orderDate >= :startDate GROUP BY YEAR(o.orderDate), MONTH(o.orderDate)")
    // List<MonthlySales> getMonthlySalesReport(@Param("startDate") LocalDateTime startDate);
    
    /**
     * Update order status
     * SCENARIO: Bulk status updates
     */
    // @Modifying
    // @Query("UPDATE Order o SET o.status = :newStatus WHERE o.status = :oldStatus")
    // int updateOrderStatus(@Param("oldStatus") OrderStatus oldStatus, @Param("newStatus") OrderStatus newStatus);
}

// Product Repository
// @Repository
interface ProductRepository /* extends JpaRepository<Product, Long> */ {
    
    /**
     * Find products by category
     * SCENARIO: Category-based product listing
     */
    // List<Product> findByCategory(String category);
    
    /**
     * Find products by price range
     * SCENARIO: Price filtering
     */
    // List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find products with low stock
     * SCENARIO: Inventory management
     */
    // List<Product> findByStockQuantityLessThan(Integer threshold);
    
    /**
     * Search products by name
     * SCENARIO: Product search functionality
     */
    // List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find top-selling products
     * SCENARIO: Popular products identification
     */
    // @Query("SELECT p FROM Product p JOIN OrderItem oi ON p.id = oi.product.id " +
    //        "GROUP BY p.id ORDER BY SUM(oi.quantity) DESC")
    // List<Product> findTopSellingProducts(Pageable pageable);
}

/**
 * SERVICE LAYER WITH SPRING DATA JPA
 * WHEN TO USE: For business logic that uses repository operations
 */
// @Service
// @Transactional
class CustomerService {
    
    // @Autowired
    private CustomerRepository customerRepository;
    
    // @Autowired
    private OrderRepository orderRepository;
    
    /**
     * Create new customer
     * SCENARIO: Customer registration
     */
    public String createCustomer(String firstName, String lastName, String email) {
        // Validate input
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        
        // Check if customer already exists
        // if (customerRepository.existsByEmail(email)) {
        //     throw new IllegalArgumentException("Customer with this email already exists");
        // }
        
        Customer customer = new Customer(firstName, lastName, email);
        // Customer saved = customerRepository.save(customer);
        
        return "Customer created: " + customer.getFullName() + " with email: " + email;
    }
    
    /**
     * Get customer with order history
     * SCENARIO: Customer profile page
     */
    public String getCustomerWithOrders(Long customerId) {
        /*
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        
        // Lazy loading will fetch orders when accessed
        List<Order> orders = customer.getOrders();
        
        return new CustomerWithOrdersDto(customer, orders);
        */
        
        return "Customer with orders for ID: " + customerId;
    }
    
    /**
     * Search customers with pagination
     * SCENARIO: Admin customer management
     */
    public String searchCustomers(String lastName, int page, int size) {
        /*
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName").ascending());
        Page<Customer> customers = customerRepository.findByLastNameContaining(lastName, pageable);
        
        return customers;
        */
        
        return "Searching customers with lastName containing: " + lastName + 
               " (page " + page + ", size " + size + ")";
    }
    
    /**
     * Get customer statistics
     * SCENARIO: Dashboard analytics
     */
    public String getCustomerStatistics() {
        /*
        long totalCustomers = customerRepository.count();
        long recentCustomers = customerRepository.findRecentCustomers(30).size();
        CustomerStats stats = customerRepository.getCustomerStatistics();
        
        return new DashboardStats(totalCustomers, recentCustomers, stats);
        */
        
        return "Customer statistics: Total customers, recent signups, average orders";
    }
}

/**
 * ORDER SERVICE EXAMPLE
 * WHEN TO USE: For order management business logic
 */
// @Service
// @Transactional
class OrderService {
    
    // @Autowired
    private OrderRepository orderRepository;
    
    // @Autowired
    private CustomerRepository customerRepository;
    
    // @Autowired
    private ProductRepository productRepository;
    
    /**
     * Create order with items
     * SCENARIO: E-commerce checkout process
     */
    public String createOrder(Long customerId, java.util.List<OrderItemRequest> items) {
        /*
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        
        Order order = new Order(customer);
        
        for (OrderItemRequest itemRequest : items) {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
            
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }
            
            OrderItem orderItem = new OrderItem(order, product, itemRequest.getQuantity(), product.getPrice());
            order.addOrderItem(orderItem);
            
            // Update stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);
        }
        
        Order savedOrder = orderRepository.save(order);
        customer.addOrder(savedOrder);
        
        return savedOrder;
        */
        
        return "Order created for customer " + customerId + " with " + items.size() + " items";
    }
    
    /**
     * Update order status
     * SCENARIO: Order fulfillment workflow
     */
    public String updateOrderStatus(Long orderId, OrderStatus newStatus) {
        /*
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        
        order.setStatus(newStatus);
        
        if (newStatus == OrderStatus.SHIPPED) {
            order.setShippedDate(LocalDateTime.now());
        } else if (newStatus == OrderStatus.DELIVERED) {
            order.setDeliveredDate(LocalDateTime.now());
        }
        
        return orderRepository.save(order);
        */
        
        return "Order " + orderId + " status updated to: " + newStatus;
    }
    
    /**
     * Get monthly sales report
     * SCENARIO: Business analytics
     */
    public String getMonthlySalesReport(int months) {
        /*
        LocalDateTime startDate = LocalDateTime.now().minusMonths(months);
        List<MonthlySales> report = orderRepository.getMonthlySalesReport(startDate);
        
        return report;
        */
        
        return "Monthly sales report for last " + months + " months";
    }
}

// Helper classes for demonstration
class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    
    public OrderItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
}

/**
 * DEMONSTRATION CLASS
 */
public class SpringDataJpaExample {
    
    public static void main(String[] args) {
        System.out.println("=== SPRING DATA JPA DEMONSTRATION ===\n");
        
        demonstrateCustomerOperations();
        demonstrateOrderOperations();
        demonstrateRepositoryFeatures();
    }
    
    private static void demonstrateCustomerOperations() {
        System.out.println("1. CUSTOMER OPERATIONS:");
        
        CustomerService customerService = new CustomerService();
        
        // Create customer
        String createResult = customerService.createCustomer("John", "Doe", "john.doe@example.com");
        System.out.println("Create: " + createResult);
        
        // Get customer with orders
        String customerResult = customerService.getCustomerWithOrders(1L);
        System.out.println("Get Customer: " + customerResult);
        
        // Search customers
        String searchResult = customerService.searchCustomers("Doe", 0, 10);
        System.out.println("Search: " + searchResult);
        
        // Get statistics
        String statsResult = customerService.getCustomerStatistics();
        System.out.println("Statistics: " + statsResult);
        System.out.println();
    }
    
    private static void demonstrateOrderOperations() {
        System.out.println("2. ORDER OPERATIONS:");
        
        OrderService orderService = new OrderService();
        
        // Create order
        java.util.List<OrderItemRequest> items = java.util.Arrays.asList(
            new OrderItemRequest(1L, 2),
            new OrderItemRequest(2L, 1)
        );
        String orderResult = orderService.createOrder(1L, items);
        System.out.println("Create Order: " + orderResult);
        
        // Update order status
        String statusResult = orderService.updateOrderStatus(1L, OrderStatus.SHIPPED);
        System.out.println("Update Status: " + statusResult);
        
        // Get sales report
        String reportResult = orderService.getMonthlySalesReport(6);
        System.out.println("Sales Report: " + reportResult);
        System.out.println();
    }
    
    private static void demonstrateRepositoryFeatures() {
        System.out.println("3. SPRING DATA JPA REPOSITORY FEATURES:");
        
        System.out.println("Query Methods:");
        System.out.println("  - findByEmail(String email)");
        System.out.println("  - findByFirstNameAndLastName(String first, String last)");
        System.out.println("  - findByEmailContaining(String domain)");
        System.out.println("  - findByCreatedAtAfter(LocalDateTime date)");
        System.out.println("  - countByEmailContaining(String domain)");
        
        System.out.println("\nCustom Queries:");
        System.out.println("  - @Query annotations for complex queries");
        System.out.println("  - Native SQL queries when needed");
        System.out.println("  - Projection queries for specific fields");
        
        System.out.println("\nPagination & Sorting:");
        System.out.println("  - Page<T> findAll(Pageable pageable)");
        System.out.println("  - Sort.by(\"fieldName\").ascending()");
        System.out.println("  - PageRequest.of(page, size, sort)");
        
        System.out.println("\n=== SPRING DATA JPA BENEFITS ===");
        System.out.println("✓ Reduced Boilerplate Code - No need to implement basic CRUD operations");
        System.out.println("✓ Query Generation - Automatic query generation from method names");
        System.out.println("✓ Pagination Support - Built-in pagination and sorting");
        System.out.println("✓ Custom Queries - Flexible query options with @Query");
        System.out.println("✓ Relationship Management - Easy handling of entity relationships");
        System.out.println("✓ Transaction Management - Automatic transaction handling");
    }
}
