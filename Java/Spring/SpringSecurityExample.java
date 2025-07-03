package Spring;

/*
=== SPRING SECURITY - COMPREHENSIVE SECURITY GUIDE ===
Spring Security provides comprehensive security services for Java applications

KEY FEATURES:
- Authentication - Who is the user?
- Authorization - What can the user do?
- Protection against attacks - CSRF, Session Fixation, etc.
- Integration with various authentication providers
- Method-level security
- Web security configuration

WHEN TO USE SPRING SECURITY:
1. Web Applications - User login, role-based access
2. REST APIs - JWT tokens, OAuth2 authentication
3. Enterprise Applications - LDAP, Active Directory integration
4. Microservices - Service-to-service authentication
5. Admin Interfaces - Role-based feature access
*/

// Note: These imports would be available with Spring Security dependencies
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SECURITY CONFIGURATION
 * WHEN TO USE: To customize security settings for your application
 */
// @Configuration
// @EnableWebSecurity
class SecurityConfig {
    
    /**
     * HTTP Security Configuration
     * SCENARIO: Configure which URLs require authentication
     */
    // @Bean
    public String securityFilterChain(/* HttpSecurity http */) throws Exception {
        System.out.println("Configuring HTTP Security:");
        System.out.println("- Public endpoints: /api/public/**");
        System.out.println("- Admin endpoints: /api/admin/** (ADMIN role required)");
        System.out.println("- User endpoints: /api/user/** (USER role required)");
        System.out.println("- All other endpoints require authentication");
        
        /*
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable for REST APIs
        
        return http.build();
        */
        
        return "HTTP Security configured";
    }
    
    /**
     * Password Encoder Bean
     * SCENARIO: Secure password storage with bcrypt hashing
     */
    // @Bean
    public String passwordEncoder() {
        System.out.println("Password Encoder: BCrypt with strength 12");
        // return new BCryptPasswordEncoder(12);
        return "BCryptPasswordEncoder configured";
    }
}

/**
 * USER ENTITY FOR AUTHENTICATION
 * WHEN TO USE: For storing user credentials and roles in database
 */
// @Entity
// @Table(name = "users")
class User {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column(unique = true, nullable = false)
    private String username;
    
    // @Column(nullable = false)
    private String password;
    
    // @Column(unique = true, nullable = false)
    private String email;
    
    private boolean enabled = true;
    
    // @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    // Constructors
    public User() {}
    
    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}

/**
 * USER ROLES ENUM
 * WHEN TO USE: For role-based access control
 */
enum Role {
    USER,     // Regular user - can access user endpoints
    ADMIN,    // Administrator - can access admin endpoints
    MODERATOR // Moderator - can moderate content
}

/**
 * USER REPOSITORY
 * WHEN TO USE: For user data access operations
 */
// @Repository
interface UserRepository /* extends JpaRepository<User, Long> */ {
    // Optional<User> findByUsername(String username);
    // Optional<User> findByEmail(String email);
    // boolean existsByUsername(String username);
    // boolean existsByEmail(String email);
}

/**
 * USER DETAILS SERVICE IMPLEMENTATION
 * WHEN TO USE: For custom user authentication logic
 */
// @Service
class CustomUserDetailsService /* implements UserDetailsService */ {
    
    // @Autowired
    private UserRepository userRepository;
    
    /**
     * Load user by username for authentication
     * SCENARIO: Spring Security needs to authenticate a user
     */
    // @Override
    public String loadUserByUsername(String username) /* throws UsernameNotFoundException */ {
        System.out.println("Loading user details for: " + username);
        
        /*
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities("ROLE_" + user.getRole().name())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!user.isEnabled())
            .build();
        */
        
        return "UserDetails for " + username;
    }
}

/**
 * AUTHENTICATION CONTROLLER
 * WHEN TO USE: For custom login/registration endpoints
 */
// @RestController
// @RequestMapping("/api/auth")
class AuthController {
    
    // @Autowired
    private UserService userService;
    
    // @Autowired
    private String passwordEncoder; // PasswordEncoder passwordEncoder;
    
    /**
     * User Registration
     * SCENARIO: New user account creation
     */
    // @PostMapping("/register")
    public String register(RegistrationRequest request) {
        System.out.println("Registering new user: " + request.getUsername());
        
        // Validate request
        if (request.getUsername() == null || request.getUsername().length() < 3) {
            return "Username must be at least 3 characters";
        }
        
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return "Password must be at least 6 characters";
        }
        
        // Check if user already exists
        if (userService.existsByUsername(request.getUsername())) {
            return "Username already exists";
        }
        
        if (userService.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }
        
        // Create new user
        User user = new User(
            request.getUsername(),
            request.getPassword(), // passwordEncoder.encode(request.getPassword()),
            request.getEmail(),
            Role.USER
        );
        
        String savedUser = userService.save(user);
        return "User registered successfully: " + savedUser;
    }
    
    /**
     * User Login
     * SCENARIO: User authentication
     */
    // @PostMapping("/login")
    public String login(LoginRequest request) {
        System.out.println("User login attempt: " + request.getUsername());
        
        /*
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );
            
            // Generate JWT token
            String token = jwtTokenProvider.generateToken(authentication);
            
            return new JwtAuthenticationResponse(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        */
        
        return "Login successful - JWT token generated";
    }
    
    /**
     * Get Current User
     * SCENARIO: Retrieve authenticated user information
     */
    // @GetMapping("/me")
    // @PreAuthorize("hasRole('USER')")
    public String getCurrentUser(/* Principal principal */) {
        String username = "current-user"; // principal.getName();
        return "Current user: " + username;
        // return userService.findByUsername(username);
    }
}

/**
 * USER SERVICE
 * WHEN TO USE: For user management business logic
 */
// @Service
class UserService {
    
    // @Autowired
    private UserRepository userRepository;
    
    // @Autowired
    private String passwordEncoder; // PasswordEncoder passwordEncoder;
    
    public String save(User user) {
        // Encode password before saving
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return "Saved user: " + user.getUsername();
        // return userRepository.save(user);
    }
    
    public String findByUsername(String username) {
        return "User: " + username;
        // return userRepository.findByUsername(username);
    }
    
    public boolean existsByUsername(String username) {
        // return userRepository.existsByUsername(username);
        return false; // Simulate user doesn't exist
    }
    
    public boolean existsByEmail(String email) {
        // return userRepository.existsByEmail(email);
        return false; // Simulate email doesn't exist
    }
    
    /**
     * Change user password
     * SCENARIO: User wants to update their password
     */
    public String changePassword(String username, String oldPassword, String newPassword) {
        /*
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
        */
        
        return "Password changed for user: " + username;
    }
}

/**
 * REQUEST/RESPONSE DTOs
 * WHEN TO USE: For API request/response data transfer
 */
class RegistrationRequest {
    private String username;
    private String password;
    private String email;
    
    public RegistrationRequest() {}
    
    public RegistrationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

class LoginRequest {
    private String username;
    private String password;
    
    public LoginRequest() {}
    
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

/**
 * METHOD-LEVEL SECURITY EXAMPLE
 * WHEN TO USE: For fine-grained security control
 */
// @Service
// @PreAuthorize("hasRole('ADMIN')")
class AdminService {
    
    /**
     * Only admins can access this method
     * SCENARIO: Administrative operations
     */
    // @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(Long userId) {
        System.out.println("Admin deleting user: " + userId);
        return "User deleted by admin";
    }
    
    /**
     * Admins can view any user, users can only view themselves
     * SCENARIO: User profile access control
     */
    // @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public String getUserProfile(Long userId) {
        System.out.println("Accessing user profile: " + userId);
        return "User profile data";
    }
    
    /**
     * Post-authorization filtering
     * SCENARIO: Filter results based on user permissions
     */
    // @PostFilter("hasRole('ADMIN') or filterObject.ownerId == authentication.principal.id")
    public String getAllUserData() {
        System.out.println("Getting user data with filtering");
        return "Filtered user data based on permissions";
    }
}

/**
 * DEMONSTRATION CLASS
 */
public class SpringSecurityExample {
    
    public static void main(String[] args) {
        System.out.println("=== SPRING SECURITY DEMONSTRATION ===\n");
        
        demonstrateSecurityConcepts();
        demonstrateUserManagement();
        demonstrateRoleBasedAccess();
    }
    
    private static void demonstrateSecurityConcepts() {
        System.out.println("1. SECURITY CONFIGURATION:");
        SecurityConfig securityConfig = new SecurityConfig();
        try {
            String result = securityConfig.securityFilterChain();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        String encoder = securityConfig.passwordEncoder();
        System.out.println("Encoder: " + encoder);
        System.out.println();
    }
    
    private static void demonstrateUserManagement() {
        System.out.println("2. USER MANAGEMENT:");
        
        UserService userService = new UserService();
        AuthController authController = new AuthController();
        
        // Registration
        RegistrationRequest regRequest = new RegistrationRequest("john_doe", "password123", "john@example.com");
        String regResult = authController.register(regRequest);
        System.out.println("Registration: " + regResult);
        
        // Login
        LoginRequest loginRequest = new LoginRequest("john_doe", "password123");
        String loginResult = authController.login(loginRequest);
        System.out.println("Login: " + loginResult);
        
        // Get current user
        String currentUser = authController.getCurrentUser();
        System.out.println("Current User: " + currentUser);
        System.out.println();
    }
    
    private static void demonstrateRoleBasedAccess() {
        System.out.println("3. ROLE-BASED ACCESS CONTROL:");
        
        AdminService adminService = new AdminService();
        
        // Admin operations
        String deleteResult = adminService.deleteUser(123L);
        System.out.println("Delete User: " + deleteResult);
        
        String profileResult = adminService.getUserProfile(123L);
        System.out.println("User Profile: " + profileResult);
        
        String dataResult = adminService.getAllUserData();
        System.out.println("User Data: " + dataResult);
        
        System.out.println("\n=== SPRING SECURITY BENEFITS ===");
        System.out.println("✓ Comprehensive Authentication - Multiple authentication providers");
        System.out.println("✓ Fine-grained Authorization - Method and URL-level security");
        System.out.println("✓ Protection Against Attacks - CSRF, Session Fixation, etc.");
        System.out.println("✓ Easy Integration - Works with existing Spring applications");
        System.out.println("✓ Flexible Configuration - Java config, annotations, XML");
        System.out.println("✓ Extensive Ecosystem - OAuth2, JWT, LDAP support");
    }
}
