/*
=== COMPREHENSIVE JAVA GUIDE ===
A complete guide to Java fundamentals, OOP concepts, advanced features, and best practices
Note: This file has .js extension but contains Java code examples for reference
*/

/*
=====================================
1. JAVA BASICS AND FUNDAMENTALS
=====================================

// Basic Java Program Structure
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

// Variables and Data Types
public class DataTypes {
    public static void main(String[] args) {
        // Primitive data types
        byte smallNumber = 127;           // 8-bit signed integer (-128 to 127)
        short shortNumber = 32767;        // 16-bit signed integer
        int number = 2147483647;          // 32-bit signed integer
        long bigNumber = 9223372036854775807L; // 64-bit signed integer
        
        float decimal = 3.14f;            // 32-bit floating point
        double preciseDecimal = 3.14159;  // 64-bit floating point
        
        char character = 'A';             // 16-bit Unicode character
        boolean isTrue = true;            // true or false
        
        // Reference types
        String text = "Hello Java";       // String object
        Integer boxedInt = 42;            // Wrapper class
        
        // Arrays
        int[] numbers = {1, 2, 3, 4, 5};
        String[] names = new String[3];
        names[0] = "Alice";
        names[1] = "Bob";
        names[2] = "Charlie";
        
        System.out.println("Number: " + number);
        System.out.println("Text: " + text);
        System.out.println("Array length: " + numbers.length);
    }
}

=====================================
2. CONTROL STRUCTURES
=====================================

public class ControlStructures {
    public static void main(String[] args) {
        int score = 85;
        
        // If-else statements
        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else {
            System.out.println("Grade: F");
        }
        
        // Switch statement
        String day = "Monday";
        switch (day) {
            case "Monday":
                System.out.println("Start of work week");
                break;
            case "Friday":
                System.out.println("TGIF!");
                break;
            case "Saturday":
            case "Sunday":
                System.out.println("Weekend!");
                break;
            default:
                System.out.println("Regular day");
        }
        
        // Enhanced switch (Java 12+)
        String result = switch (day) {
            case "Monday" -> "Start of work week";
            case "Friday" -> "TGIF!";
            case "Saturday", "Sunday" -> "Weekend!";
            default -> "Regular day";
        };
        
        // Loops
        // For loop
        for (int i = 0; i < 5; i++) {
            System.out.println("For loop iteration: " + i);
        }
        
        // Enhanced for loop (for-each)
        int[] numbers = {1, 2, 3, 4, 5};
        for (int num : numbers) {
            System.out.println("Number: " + num);
        }
        
        // While loop
        int count = 0;
        while (count < 3) {
            System.out.println("While loop count: " + count);
            count++;
        }
        
        // Do-while loop
        int x = 0;
        do {
            System.out.println("Do-while: " + x);
            x++;
        } while (x < 3);
    }
}

=====================================
3. OBJECT-ORIENTED PROGRAMMING
=====================================

// Class definition with encapsulation
public class Person {
    // Private fields (encapsulation)
    private String name;
    private int age;
    private String email;
    
    // Constructors
    public Person() {
        this("Unknown", 0, "");
    }
    
    public Person(String name, int age) {
        this(name, age, "");
    }
    
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setter methods with validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        }
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }
    
    // Methods
    public void introduce() {
        System.out.println("Hi, I'm " + name + ", " + age + " years old.");
    }
    
    public boolean isAdult() {
        return age >= 18;
    }
    
    // toString method override
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
    
    // equals and hashCode override
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person person = (Person) obj;
        return age == person.age && 
               name.equals(person.name) && 
               email.equals(person.email);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() + age + email.hashCode();
    }
}

// Inheritance example
public class Employee extends Person {
    private String jobTitle;
    private double salary;
    private String department;
    
    public Employee(String name, int age, String email, String jobTitle, double salary) {
        super(name, age, email); // Call parent constructor
        this.jobTitle = jobTitle;
        this.salary = salary;
    }
    
    // Getter and setter methods
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { 
        if (salary >= 0) this.salary = salary; 
    }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    // Method overriding
    @Override
    public void introduce() {
        super.introduce(); // Call parent method
        System.out.println("I work as a " + jobTitle + " in " + department);
    }
    
    // Additional methods
    public void work() {
        System.out.println(getName() + " is working as a " + jobTitle);
    }
    
    public double getAnnualSalary() {
        return salary * 12;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", jobTitle='" + jobTitle + "', salary=" + salary;
    }
}

// Abstract class example
public abstract class Shape {
    protected String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    // Concrete method
    public void displayInfo() {
        System.out.println("Shape color: " + color);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
    
    // Getter and setter
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}

// Concrete implementation of abstract class
public class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
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
    
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
}

public class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
    
    // Getters and setters
    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }
    
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
}

=====================================
4. INTERFACES AND POLYMORPHISM
=====================================

// Interface definition
public interface Drawable {
    void draw(); // implicitly public and abstract
    
    // Default method (Java 8+)
    default void display() {
        System.out.println("Displaying the drawable object");
        draw();
    }
    
    // Static method (Java 8+)
    static void printInfo() {
        System.out.println("This is a drawable interface");
    }
}

// Multiple interfaces
public interface Movable {
    void move(int x, int y);
    
    default void reset() {
        move(0, 0);
    }
}

// Class implementing multiple interfaces
public class GraphicObject implements Drawable, Movable {
    private int x, y;
    private String name;
    
    public GraphicObject(String name) {
        this.name = name;
        this.x = 0;
        this.y = 0;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing " + name + " at position (" + x + ", " + y + ")");
    }
    
    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println(name + " moved to (" + x + ", " + y + ")");
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public String getName() { return name; }
}

// Functional interface (for lambda expressions)
@FunctionalInterface
public interface Calculator {
    double calculate(double a, double b);
    
    // Can have default and static methods
    default void printResult(double a, double b) {
        System.out.println("Result: " + calculate(a, b));
    }
}

=====================================
5. COLLECTIONS FRAMEWORK
=====================================

import java.util.*;

public class CollectionsExample {
    public static void main(String[] args) {
        // Lists - ordered, allow duplicates
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        arrayList.add("Apple"); // duplicates allowed
        
        List<String> linkedList = new LinkedList<>();
        linkedList.add("Dog");
        linkedList.add("Cat");
        linkedList.add("Bird");
        
        // Sets - no duplicates
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Red");
        hashSet.add("Green");
        hashSet.add("Blue");
        hashSet.add("Red"); // duplicate ignored
        
        Set<String> treeSet = new TreeSet<>(); // sorted
        treeSet.add("Zebra");
        treeSet.add("Apple");
        treeSet.add("Bird");
        
        // Maps - key-value pairs
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Alice", 25);
        hashMap.put("Bob", 30);
        hashMap.put("Charlie", 35);
        
        Map<String, Integer> treeMap = new TreeMap<>(); // sorted by keys
        treeMap.put("Zebra", 1);
        treeMap.put("Apple", 2);
        treeMap.put("Bird", 3);
        
        // Iterating through collections
        System.out.println("ArrayList iteration:");
        for (String fruit : arrayList) {
            System.out.println(fruit);
        }
        
        System.out.println("HashMap iteration:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // Streams and lambda expressions (Java 8+)
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Filter even numbers and double them
        List<Integer> result = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * 2)
            .collect(Collectors.toList());
        
        System.out.println("Filtered and doubled: " + result);
        
        // Find sum of all numbers greater than 5
        int sum = numbers.stream()
            .filter(n -> n > 5)
            .reduce(0, Integer::sum);
        
        System.out.println("Sum of numbers > 5: " + sum);
    }
}

=====================================
6. EXCEPTION HANDLING
=====================================

// Custom exception classes
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

public class ExceptionHandling {
    
    // Method that throws checked exception
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age must be between 0 and 150");
        }
    }
    
    // Method that throws unchecked exception
    public static void withdraw(double balance, double amount) {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        System.out.println("Withdrawal successful: " + amount);
    }
    
    public static void main(String[] args) {
        // Try-catch-finally
        try {
            validateAge(25);
            System.out.println("Age is valid");
            
            withdraw(100.0, 150.0); // This will throw exception
        } catch (InvalidAgeException e) {
            System.err.println("Age validation error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("Transaction error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        } finally {
            System.out.println("Cleanup operations");
        }
        
        // Try-with-resources (automatic resource management)
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter a number:");
            // scanner automatically closed
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        // Multiple catch blocks
        try {
            int[] array = {1, 2, 3};
            int result = array[5] / 0; // Multiple potential exceptions
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array index error: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.err.println("Math error: " + e.getMessage());
        }
    }
}

=====================================
7. GENERICS
=====================================

// Generic class
public class Box<T> {
    private T content;
    
    public void set(T content) {
        this.content = content;
    }
    
    public T get() {
        return content;
    }
    
    public boolean isEmpty() {
        return content == null;
    }
}

// Generic method
public class GenericMethods {
    
    // Generic method with type parameter
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    // Bounded type parameter
    public static <T extends Number> double sum(T[] numbers) {
        double total = 0.0;
        for (T num : numbers) {
            total += num.doubleValue();
        }
        return total;
    }
    
    // Wildcard usage
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }
    
    // Upper bounded wildcard
    public static double sumNumbers(List<? extends Number> numbers) {
        double total = 0.0;
        for (Number num : numbers) {
            total += num.doubleValue();
        }
        return total;
    }
    
    // Lower bounded wildcard
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }
}

// Usage examples
public class GenericsExample {
    public static void main(String[] args) {
        // Using generic class
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello");
        String value = stringBox.get();
        
        Box<Integer> intBox = new Box<>();
        intBox.set(42);
        Integer number = intBox.get();
        
        // Using generic methods
        String[] names = {"Alice", "Bob", "Charlie"};
        GenericMethods.swap(names, 0, 2);
        
        Integer[] numbers = {1, 2, 3, 4, 5};
        double sum = GenericMethods.sum(numbers);
        
        // Wildcards
        List<String> stringList = Arrays.asList("A", "B", "C");
        GenericMethods.printList(stringList);
        
        List<Integer> intList = Arrays.asList(1, 2, 3);
        double total = GenericMethods.sumNumbers(intList);
    }
}

=====================================
8. LAMBDA EXPRESSIONS AND STREAMS
=====================================

import java.util.function.*;
import java.util.stream.*;

public class LambdaAndStreams {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Lambda expressions with functional interfaces
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;
        
        // Predicate - test a condition
        Predicate<String> startsWithA = name -> name.startsWith("A");
        Predicate<Integer> isEven = num -> num % 2 == 0;
        
        // Function - transform input to output
        Function<String, Integer> stringLength = str -> str.length();
        Function<Integer, Integer> square = num -> num * num;
        
        // Consumer - accept input, return nothing
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());
        
        // Supplier - supply a value
        Supplier<String> greeting = () -> "Hello, World!";
        
        // Stream operations
        System.out.println("Names starting with 'A':");
        names.stream()
            .filter(startsWithA)
            .forEach(System.out::println);
        
        System.out.println("Name lengths:");
        names.stream()
            .map(stringLength)
            .forEach(System.out::println);
        
        // Complex stream operations
        List<String> processedNames = names.stream()
            .filter(name -> name.length() > 3)
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList());
        
        System.out.println("Processed names: " + processedNames);
        
        // Numeric stream operations
        OptionalDouble average = numbers.stream()
            .mapToInt(Integer::intValue)
            .average();
        
        if (average.isPresent()) {
            System.out.println("Average: " + average.getAsDouble());
        }
        
        // Grouping and partitioning
        Map<Integer, List<String>> namesByLength = names.stream()
            .collect(Collectors.groupingBy(String::length));
        
        Map<Boolean, List<Integer>> evenOdd = numbers.stream()
            .collect(Collectors.partitioningBy(isEven));
        
        System.out.println("Names by length: " + namesByLength);
        System.out.println("Even/Odd partition: " + evenOdd);
        
        // Parallel streams for performance
        long count = IntStream.range(1, 1000000)
            .parallel()
            .filter(n -> n % 2 == 0)
            .count();
        
        System.out.println("Even numbers count: " + count);
    }
}

=====================================
9. MULTITHREADING
=====================================

// Extending Thread class
class NumberPrinter extends Thread {
    private String name;
    
    public NumberPrinter(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println(name + " interrupted");
                return;
            }
        }
    }
}

// Implementing Runnable interface
class Task implements Runnable {
    private String taskName;
    
    public Task(String taskName) {
        this.taskName = taskName;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(taskName + " - Step " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

// Synchronized methods and blocks
class Counter {
    private int count = 0;
    
    // Synchronized method
    public synchronized void increment() {
        count++;
    }
    
    // Synchronized block
    public void decrement() {
        synchronized(this) {
            count--;
        }
    }
    
    public synchronized int getCount() {
        return count;
    }
}

// Using ExecutorService
import java.util.concurrent.*;

public class MultithreadingExample {
    public static void main(String[] args) {
        // Creating and starting threads
        NumberPrinter thread1 = new NumberPrinter("Thread-1");
        NumberPrinter thread2 = new NumberPrinter("Thread-2");
        
        thread1.start();
        thread2.start();
        
        // Using Runnable
        Thread taskThread = new Thread(new Task("Task-A"));
        taskThread.start();
        
        // Lambda expression for Runnable
        Thread lambdaThread = new Thread(() -> {
            System.out.println("Lambda thread running");
        });
        lambdaThread.start();
        
        // ExecutorService - thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " executed by " + 
                                 Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
        
        // CompletableFuture for asynchronous programming
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Async result";
        });
        
        future.thenAccept(result -> System.out.println("Got result: " + result));
        
        // Synchronized counter example
        Counter counter = new Counter();
        
        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };
        
        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(incrementTask);
        
        t1.start();
        t2.start();
        
        try {
            t1.join(); // Wait for thread to complete
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Final count: " + counter.getCount());
    }
}

=====================================
10. FILE I/O AND SERIALIZATION
=====================================

import java.io.*;
import java.nio.file.*;
import java.util.List;

// Serializable class
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    private double gpa;
    private transient String password; // transient = not serialized
    
    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }
    
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
    }
}

public class FileIOExample {
    public static void main(String[] args) {
        String filename = "example.txt";
        String dataFile = "students.dat";
        
        // Writing to file
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            writer.write("Hello, Java File I/O!");
            writer.newLine();
            writer.write("This is line 2");
            writer.newLine();
            writer.write("This is line 3");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
        
        // Reading from file
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            System.out.println("File contents:");
            lines.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        // Object serialization
        List<Student> students = Arrays.asList(
            new Student("Alice", 20, 3.8),
            new Student("Bob", 21, 3.5),
            new Student("Charlie", 19, 3.9)
        );
        
        // Serialize objects to file
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(dataFile))) {
            oos.writeObject(students);
            System.out.println("Students serialized to file");
        } catch (IOException e) {
            System.err.println("Error serializing: " + e.getMessage());
        }
        
        // Deserialize objects from file
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(dataFile))) {
            @SuppressWarnings("unchecked")
            List<Student> loadedStudents = (List<Student>) ois.readObject();
            System.out.println("Loaded students:");
            loadedStudents.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing: " + e.getMessage());
        }
        
        // Working with directories
        try {
            Path directory = Paths.get("temp_directory");
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
                System.out.println("Directory created: " + directory);
            }
            
            // List files in directory
            Files.list(Paths.get("."))
                .filter(Files::isRegularFile)
                .forEach(System.out::println);
            
        } catch (IOException e) {
            System.err.println("Error with directory operations: " + e.getMessage());
        }
    }
}

=====================================
SUMMARY: RECOMMENDED USAGE SCENARIOS
=====================================

1. VARIABLES AND DATA TYPES:
   - Use appropriate primitive types for memory efficiency
   - Use wrapper classes when you need null values or collections
   - Prefer final for constants and immutable references

2. CONTROL STRUCTURES:
   - Use enhanced for loops for collections
   - Prefer switch expressions (Java 12+) for cleaner code
   - Use break and continue judiciously

3. OBJECT-ORIENTED PROGRAMMING:
   - Encapsulate fields with private access and public getters/setters
   - Use inheritance for "is-a" relationships
   - Favor composition over inheritance when possible
   - Override toString(), equals(), and hashCode() when needed

4. INTERFACES:
   - Use interfaces to define contracts
   - Prefer interfaces over abstract classes for multiple inheritance
   - Use functional interfaces with lambda expressions

5. COLLECTIONS:
   - ArrayList: When you need indexed access and don't mind resize overhead
   - LinkedList: When you frequently insert/delete at arbitrary positions
   - HashMap: For fast key-value lookups
   - TreeMap: When you need sorted keys
   - HashSet: For unique elements without order
   - TreeSet: For unique, sorted elements

6. EXCEPTION HANDLING:
   - Use checked exceptions for recoverable conditions
   - Use unchecked exceptions for programming errors
   - Always use try-with-resources for resource management
   - Create custom exceptions for domain-specific errors

7. GENERICS:
   - Use generics for type safety and elimination of casts
   - Use bounded type parameters when you need specific functionality
   - Use wildcards for flexibility in method parameters

8. LAMBDA EXPRESSIONS:
   - Use for functional programming style
   - Prefer method references when possible
   - Use streams for data processing pipelines

9. MULTITHREADING:
   - Use ExecutorService instead of creating Thread objects directly
   - Use synchronized for simple thread safety
   - Consider CompletableFuture for asynchronous programming
   - Use thread-safe collections when appropriate

10. FILE I/O:
    - Use try-with-resources for automatic resource management
    - Prefer NIO.2 (java.nio.file) for modern file operations
    - Use serialization for object persistence
    - Consider JSON/XML for cross-platform data exchange

BEST PRACTICES:
- Follow naming conventions (camelCase for variables/methods, PascalCase for classes)
- Use meaningful names for variables, methods, and classes
- Keep methods small and focused on single responsibility
- Document public APIs with Javadoc
- Use static analysis tools (like SpotBugs, PMD) for code quality
- Write unit tests for your code
- Handle exceptions appropriately
- Use logging instead of System.out.println for production code
*/