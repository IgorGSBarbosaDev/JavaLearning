/*
=== JAVA FUNDAMENTALS - WORKING EXAMPLES ===
Basic Java concepts with executable code examples
*/

// Basic Java program structure
public class JavaFundamentals {
    
    // Static variables (class-level)
    private static final String COMPANY_NAME = "TechCorp";
    private static int employeeCount = 0;
    
    // Instance variables
    private String name;
    private int id;
    
    // Constructor
    public JavaFundamentals(String name) {
        this.name = name;
        this.id = ++employeeCount;
    }
    
    // Main method - entry point
    public static void main(String[] args) {
        System.out.println("=== JAVA FUNDAMENTALS EXAMPLES ===\n");
        
        // 1. Data Types and Variables
        demonstrateDataTypes();
        
        // 2. Control Structures
        demonstrateControlStructures();
        
        // 3. Arrays
        demonstrateArrays();
        
        // 4. Methods
        demonstrateMethods();
        
        // 5. Objects and Classes
        demonstrateObjects();
    }
    
    // Method to demonstrate data types
    public static void demonstrateDataTypes() {
        System.out.println("1. DATA TYPES AND VARIABLES:");
        
        // Primitive data types
        byte byteVar = 127;                    // 8-bit signed integer
        short shortVar = 32767;                // 16-bit signed integer
        int intVar = 2147483647;               // 32-bit signed integer
        long longVar = 9223372036854775807L;   // 64-bit signed integer (note the L)
        
        float floatVar = 3.14f;                // 32-bit floating point (note the f)
        double doubleVar = 3.14159265359;      // 64-bit floating point
        
        char charVar = 'A';                    // 16-bit Unicode character
        boolean boolVar = true;                // true or false
        
        // Reference types
        String stringVar = "Hello, Java!";     // String object
        Integer integerObj = 42;               // Wrapper class (autoboxing)
        
        // Display values
        System.out.println("  byte: " + byteVar);
        System.out.println("  short: " + shortVar);
        System.out.println("  int: " + intVar);
        System.out.println("  long: " + longVar);
        System.out.println("  float: " + floatVar);
        System.out.println("  double: " + doubleVar);
        System.out.println("  char: " + charVar);
        System.out.println("  boolean: " + boolVar);
        System.out.println("  String: " + stringVar);
        System.out.println("  Integer object: " + integerObj);
        
        // Type casting
        int intValue = 100;
        double doubleValue = intValue;         // Implicit casting (widening)
        int backToInt = (int) doubleValue;     // Explicit casting (narrowing)
        
        System.out.println("  Casting demo - int to double: " + doubleValue);
        System.out.println("  Casting demo - double to int: " + backToInt);
        
        System.out.println();
    }
    
    // Method to demonstrate control structures
    public static void demonstrateControlStructures() {
        System.out.println("2. CONTROL STRUCTURES:");
        
        // If-else statements
        int score = 85;
        String grade;
        
        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        
        System.out.println("  Score " + score + " gets grade: " + grade);
        
        // Ternary operator
        String result = score >= 60 ? "Pass" : "Fail";
        System.out.println("  Result: " + result);
        
        // Switch statement
        String day = "Wednesday";
        String dayType;
        
        switch (day) {
            case "Monday":
            case "Tuesday":
            case "Wednesday":
            case "Thursday":
            case "Friday":
                dayType = "Weekday";
                break;
            case "Saturday":
            case "Sunday":
                dayType = "Weekend";
                break;
            default:
                dayType = "Unknown";
        }
        
        System.out.println("  " + day + " is a " + dayType);
        
        // Loops demonstration
        System.out.println("  For loop counting 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.print("    " + i);
        }
        System.out.println();
        
        System.out.println("  While loop countdown from 3:");
        int count = 3;
        while (count > 0) {
            System.out.print("    " + count);
            count--;
        }
        System.out.println();
        
        System.out.println("  Do-while loop (executes at least once):");
        int x = 0;
        do {
            System.out.print("    " + x);
            x++;
        } while (x < 3);
        System.out.println("\n");
    }
    
    // Method to demonstrate arrays
    public static void demonstrateArrays() {
        System.out.println("3. ARRAYS:");
        
        // Array declaration and initialization
        int[] numbers = {1, 2, 3, 4, 5};
        String[] fruits = new String[3];
        fruits[0] = "Apple";
        fruits[1] = "Banana";
        fruits[2] = "Cherry";
        
        // Display array contents
        System.out.println("  Numbers array:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print("    Index " + i + ": " + numbers[i]);
        }
        System.out.println();
        
        System.out.println("  Fruits array (using enhanced for loop):");
        for (String fruit : fruits) {
            System.out.print("    " + fruit);
        }
        System.out.println();
        
        // 2D array
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("  2D Array (matrix):");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("    ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Method to demonstrate methods
    public static void demonstrateMethods() {
        System.out.println("4. METHODS:");
        
        // Calling static methods
        int sum = addNumbers(10, 20);
        System.out.println("  Sum of 10 + 20 = " + sum);
        
        double average = calculateAverage(10, 20, 30);
        System.out.println("  Average of 10, 20, 30 = " + average);
        
        // Method with variable arguments (varargs)
        int total = sumAll(1, 2, 3, 4, 5);
        System.out.println("  Sum of 1,2,3,4,5 = " + total);
        
        // Method overloading demonstration
        System.out.println("  Method overloading:");
        System.out.println("    multiply(5, 3) = " + multiply(5, 3));
        System.out.println("    multiply(5.5, 2.0) = " + multiply(5.5, 2.0));
        System.out.println("    multiply(2, 3, 4) = " + multiply(2, 3, 4));
        
        System.out.println();
    }
    
    // Method to demonstrate objects
    public static void demonstrateObjects() {
        System.out.println("5. OBJECTS AND CLASSES:");
        
        // Creating objects
        JavaFundamentals emp1 = new JavaFundamentals("Alice");
        JavaFundamentals emp2 = new JavaFundamentals("Bob");
        JavaFundamentals emp3 = new JavaFundamentals("Charlie");
        
        // Using objects
        System.out.println("  Employee 1: " + emp1.getInfo());
        System.out.println("  Employee 2: " + emp2.getInfo());
        System.out.println("  Employee 3: " + emp3.getInfo());
        
        System.out.println("  Total employees: " + employeeCount);
        System.out.println("  Company: " + COMPANY_NAME);
        
        System.out.println();
    }
    
    // Static method examples
    public static int addNumbers(int a, int b) {
        return a + b;
    }
    
    public static double calculateAverage(int a, int b, int c) {
        return (a + b + c) / 3.0;
    }
    
    // Method with variable arguments (varargs)
    public static int sumAll(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
    
    // Method overloading examples
    public static int multiply(int a, int b) {
        return a * b;
    }
    
    public static double multiply(double a, double b) {
        return a * b;
    }
    
    public static int multiply(int a, int b, int c) {
        return a * b * c;
    }
    
    // Instance methods
    public String getInfo() {
        return "ID: " + id + ", Name: " + name;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public int getId() {
        return id;
    }
    
    public static int getEmployeeCount() {
        return employeeCount;
    }
}
