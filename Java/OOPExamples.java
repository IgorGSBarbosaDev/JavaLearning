/*
=== JAVA OBJECT-ORIENTED PROGRAMMING ===
Comprehensive examples of OOP concepts in Java
*/

// ====================================
// 1. ENCAPSULATION EXAMPLE
// ====================================

/**
 * BankAccount class demonstrating encapsulation
 * Private fields with controlled access through public methods
 */
class BankAccount {
    // Private fields (encapsulated data)
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private static int totalAccounts = 0;
    
    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
            System.out.println("Initial balance cannot be negative. Set to 0.");
        }
        totalAccounts++;
    }
    
    // Public methods to access private data (controlled access)
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: $" + amount + ". New balance: $" + balance);
            return true;
        } else if (amount > balance) {
            System.out.println("Insufficient funds. Current balance: $" + balance);
            return false;
        } else {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
    }
    
    // Getters (read-only access to private fields)
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public static int getTotalAccounts() { return totalAccounts; }
    
    // Setter with validation
    public void setAccountHolder(String accountHolder) {
        if (accountHolder != null && !accountHolder.trim().isEmpty()) {
            this.accountHolder = accountHolder;
        } else {
            System.out.println("Account holder name cannot be empty.");
        }
    }
    
    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolder='" + accountHolder + '\'' +
                ", balance=" + balance +
                '}';
    }
}

// ====================================
// 2. INHERITANCE EXAMPLE
// ====================================

/**
 * Base class for all vehicles
 */
class Vehicle {
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    
    public Vehicle(String brand, String model, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }
    
    // Methods that can be inherited and overridden
    public void start() {
        System.out.println(brand + " " + model + " is starting...");
    }
    
    public void stop() {
        System.out.println(brand + " " + model + " has stopped.");
    }
    
    public void displayInfo() {
        System.out.println("Vehicle: " + year + " " + brand + " " + model + " - $" + price);
    }
    
    // Getters
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
}

/**
 * Car class inheriting from Vehicle
 */
class Car extends Vehicle {
    private int numberOfDoors;
    private String fuelType;
    
    public Car(String brand, String model, int year, double price, int numberOfDoors, String fuelType) {
        super(brand, model, year, price); // Call parent constructor
        this.numberOfDoors = numberOfDoors;
        this.fuelType = fuelType;
    }
    
    // Override parent method
    @Override
    public void start() {
        System.out.println("Car " + brand + " " + model + " engine started with a purr.");
    }
    
    // Car-specific method
    public void honk() {
        System.out.println(brand + " " + model + " goes BEEP BEEP!");
    }
    
    // Override displayInfo to include car-specific details
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent method
        System.out.println("  Type: Car, Doors: " + numberOfDoors + ", Fuel: " + fuelType);
    }
    
    // Getters
    public int getNumberOfDoors() { return numberOfDoors; }
    public String getFuelType() { return fuelType; }
}

/**
 * Motorcycle class inheriting from Vehicle
 */
class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    private int engineSize;
    
    public Motorcycle(String brand, String model, int year, double price, boolean hasSidecar, int engineSize) {
        super(brand, model, year, price);
        this.hasSidecar = hasSidecar;
        this.engineSize = engineSize;
    }
    
    @Override
    public void start() {
        System.out.println("Motorcycle " + brand + " " + model + " engine roars to life!");
    }
    
    public void wheelie() {
        System.out.println(brand + " " + model + " is doing a wheelie!");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Type: Motorcycle, Engine: " + engineSize + "cc, Sidecar: " + hasSidecar);
    }
    
    public boolean hasSidecar() { return hasSidecar; }
    public int getEngineSize() { return engineSize; }
}

// ====================================
// 3. POLYMORPHISM EXAMPLE
// ====================================

/**
 * Abstract class demonstrating polymorphism
 */
abstract class Shape {
    protected String color;
    protected double x, y; // position
    
    public Shape(String color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
    
    // Abstract methods - must be implemented by subclasses
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    public abstract void draw();
    
    // Concrete method that can be used by all subclasses
    public void move(double newX, double newY) {
        this.x = newX;
        this.y = newY;
        System.out.println(color + " shape moved to (" + x + ", " + y + ")");
    }
    
    public void displayInfo() {
        System.out.println("Shape: " + color + " at (" + x + ", " + y + ")");
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
    
    // Getters
    public String getColor() { return color; }
    public double getX() { return x; }
    public double getY() { return y; }
}

/**
 * Circle implementation of Shape
 */
class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double x, double y, double radius) {
        super(color, x, y);
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " circle with radius " + radius + 
                         " at (" + x + ", " + y + ")");
    }
    
    public double getRadius() { return radius; }
}

/**
 * Rectangle implementation of Shape
 */
class Rectangle extends Shape {
    private double width, height;
    
    public Rectangle(String color, double x, double y, double width, double height) {
        super(color, x, y);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " rectangle " + width + "x" + height + 
                         " at (" + x + ", " + y + ")");
    }
    
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}

/**
 * Triangle implementation of Shape
 */
class Triangle extends Shape {
    private double base, height, side1, side2;
    
    public Triangle(String color, double x, double y, double base, double height, double side1, double side2) {
        super(color, x, y);
        this.base = base;
        this.height = height;
        this.side1 = side1;
        this.side2 = side2;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return base + side1 + side2;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " triangle with base " + base + 
                         " and height " + height + " at (" + x + ", " + y + ")");
    }
    
    public double getBase() { return base; }
    public double getHeight() { return height; }
}

// ====================================
// 4. INTERFACE EXAMPLE
// ====================================

/**
 * Interface for objects that can be drawn
 */
interface Drawable {
    void render();
    void setVisible(boolean visible);
    boolean isVisible();
    
    // Default method (Java 8+)
    default void highlight() {
        System.out.println("Highlighting the drawable object");
    }
    
    // Static method (Java 8+)
    static void printDrawableInfo() {
        System.out.println("This is a drawable interface");
    }
}

/**
 * Interface for objects that can be moved
 */
interface Movable {
    void moveUp(double distance);
    void moveDown(double distance);
    void moveLeft(double distance);
    void moveRight(double distance);
    
    default void moveToOrigin() {
        System.out.println("Moving to origin (0,0)");
    }
}

/**
 * Game object implementing multiple interfaces
 */
class GameObject implements Drawable, Movable {
    private String name;
    private double x, y;
    private boolean visible;
    
    public GameObject(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.visible = true;
    }
    
    @Override
    public void render() {
        if (visible) {
            System.out.println("Rendering " + name + " at (" + x + ", " + y + ")");
        } else {
            System.out.println(name + " is not visible");
        }
    }
    
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println(name + " visibility set to: " + visible);
    }
    
    @Override
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public void moveUp(double distance) {
        y += distance;
        System.out.println(name + " moved up by " + distance + " to (" + x + ", " + y + ")");
    }
    
    @Override
    public void moveDown(double distance) {
        y -= distance;
        System.out.println(name + " moved down by " + distance + " to (" + x + ", " + y + ")");
    }
    
    @Override
    public void moveLeft(double distance) {
        x -= distance;
        System.out.println(name + " moved left by " + distance + " to (" + x + ", " + y + ")");
    }
    
    @Override
    public void moveRight(double distance) {
        x += distance;
        System.out.println(name + " moved right by " + distance + " to (" + x + ", " + y + ")");
    }
    
    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
}

// ====================================
// MAIN CLASS TO DEMONSTRATE OOP CONCEPTS
// ====================================

public class OOPExamples {
    public static void main(String[] args) {
        System.out.println("=== JAVA OBJECT-ORIENTED PROGRAMMING EXAMPLES ===\n");
        
        // 1. Encapsulation demonstration
        demonstrateEncapsulation();
        
        // 2. Inheritance demonstration
        demonstrateInheritance();
        
        // 3. Polymorphism demonstration
        demonstratePolymorphism();
        
        // 4. Interface demonstration
        demonstrateInterfaces();
    }
    
    public static void demonstrateEncapsulation() {
        System.out.println("1. ENCAPSULATION EXAMPLE:");
        System.out.println("Creating bank accounts with controlled access to data...\n");
        
        // Create bank accounts
        BankAccount account1 = new BankAccount("ACC001", "Alice Johnson", 1000.0);
        BankAccount account2 = new BankAccount("ACC002", "Bob Smith", 500.0);
        
        // Demonstrate controlled access
        System.out.println("Initial accounts:");
        System.out.println(account1);
        System.out.println(account2);
        System.out.println();
        
        // Perform operations
        account1.deposit(200.0);
        account1.withdraw(150.0);
        account1.withdraw(2000.0); // Should fail due to insufficient funds
        
        account2.deposit(300.0);
        account2.withdraw(100.0);
        
        System.out.println("\nFinal account states:");
        System.out.println(account1);
        System.out.println(account2);
        System.out.println("Total accounts created: " + BankAccount.getTotalAccounts());
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void demonstrateInheritance() {
        System.out.println("2. INHERITANCE EXAMPLE:");
        System.out.println("Creating vehicles with inherited properties and methods...\n");
        
        // Create different types of vehicles
        Car car = new Car("Toyota", "Camry", 2023, 25000.0, 4, "Gasoline");
        Motorcycle motorcycle = new Motorcycle("Harley-Davidson", "Street 750", 2023, 7500.0, false, 750);
        
        // Demonstrate inheritance - all vehicles have these methods
        car.displayInfo();
        car.start();
        car.honk(); // Car-specific method
        car.stop();
        System.out.println();
        
        motorcycle.displayInfo();
        motorcycle.start();
        motorcycle.wheelie(); // Motorcycle-specific method
        motorcycle.stop();
        System.out.println();
        
        // Array of vehicles (polymorphism)
        Vehicle[] vehicles = {car, motorcycle};
        System.out.println("All vehicles starting:");
        for (Vehicle vehicle : vehicles) {
            vehicle.start(); // Calls overridden methods
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void demonstratePolymorphism() {
        System.out.println("3. POLYMORPHISM EXAMPLE:");
        System.out.println("Creating different shapes with same interface...\n");
        
        // Create different shapes
        Shape[] shapes = {
            new Circle("Red", 0, 0, 5.0),
            new Rectangle("Blue", 10, 10, 8.0, 6.0),
            new Triangle("Green", 20, 20, 6.0, 4.0, 5.0, 5.0)
        };
        
        // Polymorphism in action - same method calls, different behaviors
        for (Shape shape : shapes) {
            shape.draw();           // Calls different draw() implementations
            shape.displayInfo();    // Shows area and perimeter calculations
            shape.move(shape.getX() + 5, shape.getY() + 5);
            System.out.println();
        }
        
        // Calculate total area
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        System.out.println("Total area of all shapes: " + String.format("%.2f", totalArea));
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void demonstrateInterfaces() {
        System.out.println("4. INTERFACE EXAMPLE:");
        System.out.println("Creating game objects with multiple interface implementations...\n");
        
        // Create game objects
        GameObject player = new GameObject("Player", 100, 100);
        GameObject enemy = new GameObject("Enemy", 200, 150);
        GameObject powerup = new GameObject("PowerUp", 300, 50);
        
        // Array of game objects
        GameObject[] gameObjects = {player, enemy, powerup};
        
        // Demonstrate Drawable interface
        System.out.println("Rendering all game objects:");
        for (GameObject obj : gameObjects) {
            obj.render();
            obj.highlight(); // Default method from interface
        }
        System.out.println();
        
        // Demonstrate Movable interface
        System.out.println("Moving player:");
        player.moveRight(50);
        player.moveUp(25);
        player.moveLeft(20);
        player.moveDown(10);
        System.out.println();
        
        // Hide and show objects
        System.out.println("Hiding enemy and trying to render:");
        enemy.setVisible(false);
        enemy.render();
        enemy.setVisible(true);
        enemy.render();
        System.out.println();
        
        // Use static method from interface
        Drawable.printDrawableInfo();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}
