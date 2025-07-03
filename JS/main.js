/*
=== COMPREHENSIVE JAVASCRIPT GUIDE ===
A complete guide to JavaScript fundamentals with examples and best practices
*/

// ====================================
// 1. VARIABLES AND DATA TYPES
// ====================================

console.log("=== 1. VARIABLES AND DATA TYPES ===");

// Variable declarations - use let/const, avoid var
let name = "John";          // mutable variable (can be reassigned)
const age = 25;             // immutable variable (cannot be reassigned)
var oldWay = "avoid this";  // old syntax, has function scope issues

// Primitive data types
const stringType = "Hello World";           // String
const numberType = 42;                      // Number (integers and floats)
const booleanType = true;                   // Boolean
const undefinedType = undefined;            // Undefined
const nullType = null;                      // Null
const symbolType = Symbol('id');            // Symbol (ES6+)
const bigIntType = 123n;                    // BigInt (for large numbers)

console.log("Data types:", typeof stringType, typeof numberType, typeof booleanType);

// ====================================
// 2. FUNCTIONS
// ====================================

console.log("\n=== 2. FUNCTIONS ===");

// Function declaration (hoisted)
function greet(name) {
    return "Hello, " + name;
}

// Function expression (not hoisted)
const greet2 = function(name) {
    return "Hello, " + name;
};

// Arrow functions (ES6+) - shorter syntax
const greet3 = (name) => {
    return "Hello, " + name;
};

// Even shorter arrow function (implicit return)
const greet4 = name => "Hello, " + name;

// Multiple parameters
const add = (a, b) => a + b;

// No parameters
const getCurrentTime = () => new Date().toLocaleTimeString();

// Default parameters
const greetWithDefault = (name = "Guest") => `Hello, ${name}!`;

console.log(greet("Alice"));
console.log(greetWithDefault());
console.log("Current time:", getCurrentTime());

// ====================================
// 3. OBJECTS
// ====================================

console.log("\n=== 3. OBJECTS ===");

// Object literal
const person = {
    name: "John",
    age: 25,
    city: "New York",
    
    // Method using function
    greet: function() {
        return `Hi, I'm ${this.name}`;
    },
    
    // Method using arrow function (careful with 'this')
    getAge: () => age, // 'this' doesn't work here
    
    // ES6 method syntax
    introduce() {
        return `I'm ${this.name}, ${this.age} years old from ${this.city}`;
    }
};

// Accessing properties
console.log("Name:", person.name);
console.log("Age:", person["age"]);
console.log(person.greet());
console.log(person.introduce());

// Adding properties dynamically
person.email = "john@example.com";
person.sayGoodbye = function() {
    return "Goodbye!";
};

// ====================================
// 4. ARRAYS
// ====================================

console.log("\n=== 4. ARRAYS ===");

// Array creation
const numbers = [1, 2, 3, 4, 5];
const fruits = ["apple", "banana", "orange"];
const mixed = [1, "hello", true, null, {name: "object"}];

// Array methods
console.log("Original array:", numbers);

// Adding elements
numbers.push(6);                    // Add to end
numbers.unshift(0);                 // Add to beginning
console.log("After push/unshift:", numbers);

// Removing elements
const lastElement = numbers.pop();     // Remove from end
const firstElement = numbers.shift();  // Remove from beginning
console.log("Removed:", firstElement, lastElement);
console.log("After pop/shift:", numbers);

// Array iteration methods
const doubled = numbers.map(num => num * 2);
const evenNumbers = numbers.filter(num => num % 2 === 0);
const sum = numbers.reduce((acc, num) => acc + num, 0);

console.log("Doubled:", doubled);
console.log("Even numbers:", evenNumbers);
console.log("Sum:", sum);

// ====================================
// 5. TEMPLATE LITERALS
// ====================================

console.log("\n=== 5. TEMPLATE LITERALS ===");

const userName = "Alice";
const userAge = 30;

// Template literals with ${} interpolation
const message = `Hello, ${userName}! You are ${userAge} years old.`;
console.log(message);

// Multi-line strings
const multilineMessage = `
    Welcome to our application, ${userName}!
    Your age: ${userAge}
    Status: ${userAge >= 18 ? 'Adult' : 'Minor'}
`;
console.log(multilineMessage);

// ====================================
// 6. CONDITIONALS
// ====================================

console.log("\n=== 6. CONDITIONALS ===");

const score = 85;

// If-else statement
if (score >= 90) {
    console.log("Grade: A");
} else if (score >= 80) {
    console.log("Grade: B");
} else if (score >= 70) {
    console.log("Grade: C");
} else {
    console.log("Grade: F");
}

// Ternary operator
const result = score >= 60 ? "Pass" : "Fail";
console.log("Result:", result);

// Switch statement
const day = "Monday";
switch (day) {
    case "Monday":
        console.log("Start of work week");
        break;
    case "Friday":
        console.log("TGIF!");
        break;
    case "Saturday":
    case "Sunday":
        console.log("Weekend!");
        break;
    default:
        console.log("Regular day");
}

// ====================================
// 7. LOOPS
// ====================================

console.log("\n=== 7. LOOPS ===");

// For loop
console.log("For loop:");
for (let i = 0; i < 3; i++) {
    console.log(`Iteration ${i + 1}`);
}

// For...of loop (for arrays)
console.log("For...of loop:");
for (const fruit of fruits) {
    console.log("Fruit:", fruit);
}

// For...in loop (for objects)
console.log("For...in loop:");
for (const key in person) {
    console.log(`${key}: ${person[key]}`);
}

// While loop
console.log("While loop:");
let count = 0;
while (count < 3) {
    console.log(`Count: ${count}`);
    count++;
}

// ====================================
// 8. DESTRUCTURING
// ====================================

console.log("\n=== 8. DESTRUCTURING ===");

// Array destructuring
const colors = ["red", "green", "blue"];
const [first, second, third] = colors;
console.log("First color:", first);

// Object destructuring
const user = { id: 1, username: "johndoe", email: "john@example.com" };
const { username, email } = user;
console.log("Username:", username, "Email:", email);

// Destructuring with renaming
const { username: displayName } = user;
console.log("Display name:", displayName);

// ====================================
// 9. SPREAD AND REST OPERATORS
// ====================================

console.log("\n=== 9. SPREAD AND REST OPERATORS ===");

// Spread operator with arrays
const arr1 = [1, 2, 3];
const arr2 = [4, 5, 6];
const combined = [...arr1, ...arr2];
console.log("Combined arrays:", combined);

// Spread operator with objects
const basicInfo = { name: "John", age: 25 };
const contactInfo = { email: "john@example.com", phone: "123-456-7890" };
const fullProfile = { ...basicInfo, ...contactInfo, city: "New York" };
console.log("Full profile:", fullProfile);

// Rest operator in function parameters
const sumAll = (...numbers) => {
    return numbers.reduce((sum, num) => sum + num, 0);
};
console.log("Sum of 1,2,3,4,5:", sumAll(1, 2, 3, 4, 5));

// ====================================
// 10. ERROR HANDLING
// ====================================

console.log("\n=== 10. ERROR HANDLING ===");

// Try-catch-finally
function divideNumbers(a, b) {
    try {
        if (b === 0) {
            throw new Error("Division by zero is not allowed");
        }
        return a / b;
    } catch (error) {
        console.log("Error caught:", error.message);
        return null;
    } finally {
        console.log("Division operation completed");
    }
}

console.log("10 / 2 =", divideNumbers(10, 2));
console.log("10 / 0 =", divideNumbers(10, 0));

// ====================================
// FINAL OUTPUT
// ====================================

console.log("\n=== SUMMARY ===");
console.log("JavaScript fundamentals covered:");
console.log("✓ Variables and Data Types");
console.log("✓ Functions (regular, arrow, methods)");
console.log("✓ Objects and Properties");
console.log("✓ Arrays and Array Methods");
console.log("✓ Template Literals");
console.log("✓ Conditionals");
console.log("✓ Loops");
console.log("✓ Destructuring");
console.log("✓ Spread and Rest Operators");
console.log("✓ Error Handling");