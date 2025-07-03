/*
=== JAVA ADVANCED CONCEPTS ===
Collections, Generics, Lambda Expressions, Streams, and Exception Handling
*/

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.time.*;
import java.util.concurrent.*;

// ====================================
// 1. COLLECTIONS FRAMEWORK
// ====================================

class Student implements Comparable<Student> {
    private String name;
    private int age;
    private double gpa;
    private String major;
    
    public Student(String name, int age, double gpa, String major) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        this.major = major;
    }
    
    // Comparable interface implementation for natural ordering
    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa); // Descending order by GPA
    }
    
    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }
    public String getMajor() { return major; }
    
    @Override
    public String toString() {
        return String.format("Student{name='%s', age=%d, gpa=%.2f, major='%s'}", 
                           name, age, gpa, major);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return age == student.age && 
               Double.compare(student.gpa, gpa) == 0 && 
               Objects.equals(name, student.name) && 
               Objects.equals(major, student.major);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age, gpa, major);
    }
}

// ====================================
// 2. GENERIC CLASSES AND METHODS
// ====================================

/**
 * Generic class example - A simple container
 */
class Container<T> {
    private T item;
    private String label;
    
    public Container(String label) {
        this.label = label;
    }
    
    public void setItem(T item) {
        this.item = item;
    }
    
    public T getItem() {
        return item;
    }
    
    public boolean isEmpty() {
        return item == null;
    }
    
    public String getLabel() {
        return label;
    }
    
    @Override
    public String toString() {
        return String.format("Container[%s]: %s", label, item);
    }
}

/**
 * Generic utility class with bounded type parameters
 */
class MathUtils {
    
    // Generic method with bounded type parameter
    public static <T extends Number> double average(List<T> numbers) {
        if (numbers.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (T number : numbers) {
            sum += number.doubleValue();
        }
        return sum / numbers.size();
    }
    
    // Generic method with multiple type parameters
    public static <T, U> void printPair(T first, U second) {
        System.out.println("Pair: (" + first + ", " + second + ")");
    }
    
    // Generic method with wildcard
    public static void printNumbers(List<? extends Number> numbers) {
        for (Number num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    // Generic method with upper bounded wildcard
    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }
}

// ====================================
// 3. FUNCTIONAL INTERFACES AND LAMBDAS
// ====================================

/**
 * Custom functional interface
 */
@FunctionalInterface
interface Calculator {
    double calculate(double a, double b);
    
    // Default method
    default void printResult(double a, double b) {
        System.out.printf("Result: %.2f%n", calculate(a, b));
    }
    
    // Static method
    static Calculator getAdder() {
        return (a, b) -> a + b;
    }
}

/**
 * Utility class for lambda and stream operations
 */
class LambdaExamples {
    
    public static void demonstrateLambdas() {
        System.out.println("LAMBDA EXPRESSIONS:");
        
        // Basic lambda expressions
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;
        Calculator divide = (a, b) -> b != 0 ? a / b : 0;
        
        System.out.println("5 + 3 = " + add.calculate(5, 3));
        System.out.println("5 * 3 = " + multiply.calculate(5, 3));
        System.out.println("10 / 2 = " + divide.calculate(10, 2));
        
        // Built-in functional interfaces
        Predicate<String> isLongName = name -> name.length() > 5;
        Function<String, Integer> stringLength = String::length; // Method reference
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());
        Supplier<String> greeting = () -> "Hello, Lambda!";
        
        // Using the functional interfaces
        System.out.println("Is 'Alexander' a long name? " + isLongName.test("Alexander"));
        System.out.println("Length of 'Java': " + stringLength.apply("Java"));
        printUpperCase.accept("lambda expressions");
        System.out.println(greeting.get());
    }
    
    public static void demonstrateStreams() {
        System.out.println("\nSTREAM OPERATIONS:");
        
        List<Student> students = Arrays.asList(
            new Student("Alice", 20, 3.8, "Computer Science"),
            new Student("Bob", 22, 3.2, "Mathematics"),
            new Student("Charlie", 19, 3.9, "Physics"),
            new Student("Diana", 21, 3.6, "Computer Science"),
            new Student("Eve", 20, 3.4, "Mathematics"),
            new Student("Frank", 23, 3.1, "Physics")
        );
        
        // Filter and collect
        System.out.println("Computer Science students with GPA > 3.5:");
        students.stream()
            .filter(s -> s.getMajor().equals("Computer Science"))
            .filter(s -> s.getGpa() > 3.5)
            .forEach(System.out::println);
        
        // Map and collect
        List<String> studentNames = students.stream()
            .map(Student::getName)
            .collect(Collectors.toList());
        System.out.println("\nAll student names: " + studentNames);
        
        // Group by major
        Map<String, List<Student>> studentsByMajor = students.stream()
            .collect(Collectors.groupingBy(Student::getMajor));
        System.out.println("\nStudents grouped by major:");
        studentsByMajor.forEach((major, studentList) -> {
            System.out.println(major + ": " + studentList.size() + " students");
        });
        
        // Statistical operations
        DoubleSummaryStatistics gpaStats = students.stream()
            .mapToDouble(Student::getGpa)
            .summaryStatistics();
        
        System.out.println("\nGPA Statistics:");
        System.out.printf("Average: %.2f%n", gpaStats.getAverage());
        System.out.printf("Min: %.2f%n", gpaStats.getMin());
        System.out.printf("Max: %.2f%n", gpaStats.getMax());
        System.out.printf("Count: %d%n", gpaStats.getCount());
        
        // Sorting and limiting
        System.out.println("\nTop 3 students by GPA:");
        students.stream()
            .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
            .limit(3)
            .forEach(System.out::println);
        
        // Parallel stream for performance
        long count = students.parallelStream()
            .filter(s -> s.getAge() >= 21)
            .count();
        System.out.println("\nStudents aged 21 or older: " + count);
    }
}

// ====================================
// 4. EXCEPTION HANDLING
// ====================================

/**
 * Custom exception classes
 */
class InsufficientFundsException extends Exception {
    private double requestedAmount;
    private double availableBalance;
    
    public InsufficientFundsException(double requestedAmount, double availableBalance) {
        super(String.format("Insufficient funds: Requested $%.2f, Available $%.2f", 
                          requestedAmount, availableBalance));
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }
    
    public double getRequestedAmount() { return requestedAmount; }
    public double getAvailableBalance() { return availableBalance; }
}

class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String message) {
        super(message);
    }
}

/**
 * Bank service demonstrating exception handling
 */
class BankService {
    private Map<String, Double> accounts;
    
    public BankService() {
        accounts = new HashMap<>();
        // Initialize with some sample accounts
        accounts.put("ACC001", 1000.0);
        accounts.put("ACC002", 500.0);
        accounts.put("ACC003", 2000.0);
    }
    
    public void withdraw(String accountNumber, double amount) 
            throws InsufficientFundsException, InvalidAccountException {
        
        // Check if account exists
        if (!accounts.containsKey(accountNumber)) {
            throw new InvalidAccountException("Account " + accountNumber + " does not exist");
        }
        
        double balance = accounts.get(accountNumber);
        
        // Check if sufficient funds
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        
        // Perform withdrawal
        accounts.put(accountNumber, balance - amount);
        System.out.printf("Withdrew $%.2f from %s. New balance: $%.2f%n", 
                         amount, accountNumber, accounts.get(accountNumber));
    }
    
    public void deposit(String accountNumber, double amount) throws InvalidAccountException {
        if (!accounts.containsKey(accountNumber)) {
            throw new InvalidAccountException("Account " + accountNumber + " does not exist");
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        double balance = accounts.get(accountNumber);
        accounts.put(accountNumber, balance + amount);
        System.out.printf("Deposited $%.2f to %s. New balance: $%.2f%n", 
                         amount, accountNumber, accounts.get(accountNumber));
    }
    
    public double getBalance(String accountNumber) throws InvalidAccountException {
        if (!accounts.containsKey(accountNumber)) {
            throw new InvalidAccountException("Account " + accountNumber + " does not exist");
        }
        return accounts.get(accountNumber);
    }
    
    // Method demonstrating try-with-resources
    public void saveAccountsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Double> entry : accounts.entrySet()) {
                writer.printf("%s,%.2f%n", entry.getKey(), entry.getValue());
            }
            System.out.println("Accounts saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
}

// ====================================
// MAIN CLASS
// ====================================

public class AdvancedJavaExamples {
    public static void main(String[] args) {
        System.out.println("=== JAVA ADVANCED CONCEPTS EXAMPLES ===\n");
        
        // 1. Collections demonstration
        demonstrateCollections();
        
        // 2. Generics demonstration
        demonstrateGenerics();
        
        // 3. Lambda expressions and streams
        LambdaExamples.demonstrateLambdas();
        LambdaExamples.demonstrateStreams();
        
        // 4. Exception handling
        demonstrateExceptionHandling();
        
        // 5. Modern Java features
        demonstrateModernFeatures();
    }
    
    public static void demonstrateCollections() {
        System.out.println("1. COLLECTIONS FRAMEWORK:\n");
        
        // Lists
        List<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList("Apple", "Banana", "Cherry"));
        
        List<String> linkedList = new LinkedList<>();
        linkedList.addAll(Arrays.asList("Dog", "Cat", "Bird"));
        
        System.out.println("ArrayList: " + arrayList);
        System.out.println("LinkedList: " + linkedList);
        
        // Sets
        Set<String> hashSet = new HashSet<>(Arrays.asList("Red", "Green", "Blue", "Red"));
        Set<String> treeSet = new TreeSet<>(Arrays.asList("Zebra", "Apple", "Bird"));
        
        System.out.println("HashSet (no duplicates): " + hashSet);
        System.out.println("TreeSet (sorted): " + treeSet);
        
        // Maps
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Alice", 25);
        hashMap.put("Bob", 30);
        hashMap.put("Charlie", 35);
        
        Map<String, Integer> treeMap = new TreeMap<>(hashMap);
        
        System.out.println("HashMap: " + hashMap);
        System.out.println("TreeMap (sorted keys): " + treeMap);
        
        // Working with Student objects
        List<Student> students = Arrays.asList(
            new Student("Alice", 20, 3.8, "CS"),
            new Student("Bob", 22, 3.2, "Math"),
            new Student("Charlie", 19, 3.9, "Physics")
        );
        
        Collections.sort(students); // Uses Comparable implementation
        System.out.println("\nStudents sorted by GPA (descending):");
        students.forEach(System.out::println);
        
        // Custom comparator
        students.sort(Comparator.comparing(Student::getName));
        System.out.println("\nStudents sorted by name:");
        students.forEach(System.out::println);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void demonstrateGenerics() {
        System.out.println("2. GENERICS:\n");
        
        // Generic container
        Container<String> stringContainer = new Container<>("String Container");
        stringContainer.setItem("Hello, Generics!");
        System.out.println(stringContainer);
        
        Container<Integer> intContainer = new Container<>("Integer Container");
        intContainer.setItem(42);
        System.out.println(intContainer);
        
        Container<Student> studentContainer = new Container<>("Student Container");
        studentContainer.setItem(new Student("John", 21, 3.7, "Engineering"));
        System.out.println(studentContainer);
        
        // Generic methods
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> decimals = Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5);
        
        System.out.println("\nAverage of integers: " + MathUtils.average(numbers));
        System.out.println("Average of decimals: " + MathUtils.average(decimals));
        
        MathUtils.printPair("Hello", 123);
        MathUtils.printPair(LocalDate.now(), 2024);
        
        System.out.print("Numbers: ");
        MathUtils.printNumbers(numbers);
        System.out.print("Decimals: ");
        MathUtils.printNumbers(decimals);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void demonstrateExceptionHandling() {
        System.out.println("3. EXCEPTION HANDLING:\n");
        
        BankService bankService = new BankService();
        
        // Demonstrate various exception scenarios
        try {
            // Valid operations
            System.out.println("Initial balance ACC001: $" + bankService.getBalance("ACC001"));
            bankService.deposit("ACC001", 200.0);
            bankService.withdraw("ACC001", 150.0);
            
            // This will throw InsufficientFundsException
            bankService.withdraw("ACC001", 2000.0);
            
        } catch (InsufficientFundsException e) {
            System.err.println("Transaction failed: " + e.getMessage());
            System.err.println("Available balance: $" + e.getAvailableBalance());
        } catch (InvalidAccountException e) {
            System.err.println("Account error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
        }
        
        // Demonstrate multiple catch blocks
        try {
            bankService.withdraw("INVALID", 100.0); // Invalid account
        } catch (InvalidAccountException | InsufficientFundsException e) {
            System.err.println("Bank operation failed: " + e.getMessage());
        }
        
        // Try-with-resources demonstration
        bankService.saveAccountsToFile("accounts.csv");
        
        // Custom exception handling with recovery
        String[] accountNumbers = {"ACC001", "INVALID", "ACC002"};
        for (String accountNum : accountNumbers) {
            try {
                double balance = bankService.getBalance(accountNum);
                System.out.println("Account " + accountNum + " balance: $" + balance);
            } catch (InvalidAccountException e) {
                System.err.println("Skipping invalid account: " + accountNum);
                continue; // Continue with next account
            }
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    public static void demonstrateModernFeatures() {
        System.out.println("4. MODERN JAVA FEATURES:\n");
        
        // Optional (Java 8+)
        Optional<String> optional = Optional.of("Hello, Optional!");
        optional.ifPresent(System.out::println);
        
        Optional<String> empty = Optional.empty();
        System.out.println("Empty optional value: " + empty.orElse("Default Value"));
        
        // LocalDateTime (Java 8+)
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        
        System.out.println("Current date and time: " + now);
        System.out.println("Today: " + today);
        System.out.println("Current time: " + currentTime);
        
        // Text blocks (Java 15+) - Note: Commented out for compatibility
        /*
        String json = \"\"\"
            {
                "name": "John Doe",
                "age": 30,
                "city": "New York"
            }
            \"\"\";
        System.out.println("JSON text block:\\n" + json);
        */
        
        // Pattern matching improvements and records would be shown here
        // but are commented out for broader compatibility
        
        // CompletableFuture for asynchronous programming
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Async result";
        });
        
        future.thenAccept(result -> System.out.println("Async operation completed: " + result));
        
        // Stream enhancements
        List<String> words = Arrays.asList("hello", "world", "java", "programming");
        String result = words.stream()
            .filter(word -> word.length() > 4)
            .map(String::toUpperCase)
            .collect(Collectors.joining(", "));
        
        System.out.println("Processed words: " + result);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
}
