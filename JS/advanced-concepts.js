/*
=== ADVANCED JAVASCRIPT CONCEPTS ===
Covering ES6+, async programming, classes, modules, and modern JavaScript patterns
*/

// ====================================
// 1. CLASSES AND PROTOTYPES
// ====================================

console.log("=== 1. CLASSES AND PROTOTYPES ===");

// ES6 Class syntax
class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    
    // Instance method
    introduce() {
        return `Hi, I'm ${this.name}, ${this.age} years old`;
    }
    
    // Static method
    static createAdult(name) {
        return new Person(name, 18);
    }
    
    // Getter
    get summary() {
        return `${this.name} (${this.age})`;
    }
    
    // Setter
    set updateAge(newAge) {
        if (newAge > 0) {
            this.age = newAge;
        }
    }
}

// Inheritance
class Employee extends Person {
    constructor(name, age, jobTitle) {
        super(name, age);  // Call parent constructor
        this.jobTitle = jobTitle;
    }
    
    introduce() {
        return `${super.introduce()}, and I work as a ${this.jobTitle}`;
    }
    
    work() {
        return `${this.name} is working as a ${this.jobTitle}`;
    }
}

// Usage examples
const person1 = new Person("Alice", 25);
const employee1 = new Employee("Bob", 30, "Developer");
const adult = Person.createAdult("Charlie");

console.log(person1.introduce());
console.log(employee1.introduce());
console.log(employee1.work());
console.log("Adult:", adult.summary);

// ====================================
// 2. PROMISES AND ASYNC/AWAIT
// ====================================

console.log("\n=== 2. PROMISES AND ASYNC/AWAIT ===");

// Creating a Promise
function fetchUserData(userId) {
    return new Promise((resolve, reject) => {
        // Simulate API call
        setTimeout(() => {
            if (userId > 0) {
                resolve({
                    id: userId,
                    name: "John Doe",
                    email: "john@example.com"
                });
            } else {
                reject(new Error("Invalid user ID"));
            }
        }, 1000);
    });
}

// Promise with .then()/.catch()
console.log("Fetching user data with Promise...");
fetchUserData(1)
    .then(user => {
        console.log("User data received:", user);
        return user.name;
    })
    .then(name => {
        console.log("User name:", name);
    })
    .catch(error => {
        console.error("Error:", error.message);
    });

// Async/Await (modern approach)
async function getUserInfo(userId) {
    try {
        console.log("Fetching user data with async/await...");
        const user = await fetchUserData(userId);
        console.log("User info:", user);
        return user;
    } catch (error) {
        console.error("Failed to get user info:", error.message);
        return null;
    }
}

// Call async function
getUserInfo(2);

// Multiple async operations
async function fetchMultipleUsers() {
    try {
        // Parallel execution
        const [user1, user2, user3] = await Promise.all([
            fetchUserData(1),
            fetchUserData(2),
            fetchUserData(3)
        ]);
        
        console.log("Multiple users:", { user1, user2, user3 });
    } catch (error) {
        console.error("Failed to fetch multiple users:", error.message);
    }
}

// ====================================
// 3. CLOSURES AND SCOPE
// ====================================

console.log("\n=== 3. CLOSURES AND SCOPE ===");

// Closure example
function createCounter() {
    let count = 0;
    
    return {
        increment: () => ++count,
        decrement: () => --count,
        getValue: () => count
    };
}

const counter = createCounter();
console.log("Counter:", counter.getValue()); // 0
console.log("After increment:", counter.increment()); // 1
console.log("After increment:", counter.increment()); // 2
console.log("After decrement:", counter.decrement()); // 1

// Module pattern using IIFE (Immediately Invoked Function Expression)
const Calculator = (function() {
    let result = 0;
    
    return {
        add: (x) => { result += x; return Calculator; },
        subtract: (x) => { result -= x; return Calculator; },
        multiply: (x) => { result *= x; return Calculator; },
        divide: (x) => { 
            if (x !== 0) result /= x; 
            return Calculator; 
        },
        getResult: () => result,
        reset: () => { result = 0; return Calculator; }
    };
})();

// Method chaining
const calcResult = Calculator
    .reset()
    .add(10)
    .multiply(2)
    .subtract(5)
    .getResult();

console.log("Calculator result:", calcResult); // 15

// ====================================
// 4. HIGHER-ORDER FUNCTIONS
// ====================================

console.log("\n=== 4. HIGHER-ORDER FUNCTIONS ===");

// Function that takes another function as parameter
function processArray(arr, processor) {
    return arr.map(processor);
}

// Function that returns another function
function createMultiplier(factor) {
    return (number) => number * factor;
}

const numbers = [1, 2, 3, 4, 5];
const double = createMultiplier(2);
const triple = createMultiplier(3);

console.log("Original:", numbers);
console.log("Doubled:", processArray(numbers, double));
console.log("Tripled:", processArray(numbers, triple));

// Advanced array methods
const products = [
    { name: "Laptop", price: 1000, category: "Electronics" },
    { name: "Phone", price: 500, category: "Electronics" },
    { name: "Book", price: 20, category: "Education" },
    { name: "Desk", price: 300, category: "Furniture" }
];

// Chaining array methods
const expensiveElectronics = products
    .filter(product => product.category === "Electronics")
    .filter(product => product.price > 600)
    .map(product => product.name);

console.log("Expensive electronics:", expensiveElectronics);

// Reduce examples
const totalPrice = products.reduce((sum, product) => sum + product.price, 0);
const productsByCategory = products.reduce((acc, product) => {
    if (!acc[product.category]) {
        acc[product.category] = [];
    }
    acc[product.category].push(product.name);
    return acc;
}, {});

console.log("Total price:", totalPrice);
console.log("Products by category:", productsByCategory);

// ====================================
// 5. MODULES (ES6 IMPORT/EXPORT)
// ====================================

console.log("\n=== 5. MODULES EXAMPLE ===");

// Note: These examples show module syntax
// In a real application, these would be in separate files

// math-utils.js (example module)
/*
export const PI = 3.14159;

export function add(a, b) {
    return a + b;
}

export function multiply(a, b) {
    return a * b;
}

export default function calculate(operation, a, b) {
    switch (operation) {
        case 'add': return add(a, b);
        case 'multiply': return multiply(a, b);
        default: return 0;
    }
}
*/

// main.js (importing)
/*
import calculate, { PI, add, multiply } from './math-utils.js';
import * as MathUtils from './math-utils.js';

console.log('PI:', PI);
console.log('Add 2 + 3:', add(2, 3));
console.log('Calculate multiply 4 * 5:', calculate('multiply', 4, 5));
console.log('Using namespace:', MathUtils.add(1, 2));
*/

console.log("Modules are used for organizing code into separate files");

// ====================================
// 6. GENERATORS
// ====================================

console.log("\n=== 6. GENERATORS ===");

// Generator function
function* numberGenerator() {
    let num = 1;
    while (true) {
        yield num++;
    }
}

function* fibonacciGenerator() {
    let [a, b] = [0, 1];
    while (true) {
        yield a;
        [a, b] = [b, a + b];
    }
}

// Using generators
const numGen = numberGenerator();
console.log("Generated numbers:", numGen.next().value, numGen.next().value, numGen.next().value);

const fibGen = fibonacciGenerator();
const fibSequence = [];
for (let i = 0; i < 10; i++) {
    fibSequence.push(fibGen.next().value);
}
console.log("Fibonacci sequence:", fibSequence);

// ====================================
// 7. SYMBOLS AND ITERATORS
// ====================================

console.log("\n=== 7. SYMBOLS AND ITERATORS ===");

// Symbols for unique property keys
const ID = Symbol('id');
const user = {
    name: "John",
    [ID]: 12345
};

console.log("User name:", user.name);
console.log("User ID:", user[ID]);

// Custom iterator
const iterableRange = {
    start: 1,
    end: 5,
    
    [Symbol.iterator]() {
        let current = this.start;
        const end = this.end;
        
        return {
            next() {
                if (current <= end) {
                    return { value: current++, done: false };
                } else {
                    return { done: true };
                }
            }
        };
    }
};

console.log("Custom iterable range:");
for (const num of iterableRange) {
    console.log(num);
}

// ====================================
// 8. PROXY AND REFLECTION
// ====================================

console.log("\n=== 8. PROXY AND REFLECTION ===");

// Proxy for property validation
const validatedUser = new Proxy({}, {
    set(target, property, value) {
        if (property === 'age' && (typeof value !== 'number' || value < 0)) {
            throw new Error('Age must be a positive number');
        }
        if (property === 'email' && !value.includes('@')) {
            throw new Error('Email must contain @');
        }
        target[property] = value;
        return true;
    },
    
    get(target, property) {
        if (property in target) {
            return target[property];
        } else {
            return `Property '${property}' does not exist`;
        }
    }
});

try {
    validatedUser.name = "Alice";
    validatedUser.age = 25;
    validatedUser.email = "alice@example.com";
    console.log("Validated user:", validatedUser);
    console.log("Non-existent property:", validatedUser.nonExistent);
} catch (error) {
    console.error("Validation error:", error.message);
}

// ====================================
// SUMMARY
// ====================================

console.log("\n=== ADVANCED CONCEPTS SUMMARY ===");
console.log("Advanced JavaScript concepts covered:");
console.log("✓ Classes and Inheritance");
console.log("✓ Promises and Async/Await");
console.log("✓ Closures and Scope");
console.log("✓ Higher-Order Functions");
console.log("✓ Modules (Import/Export)");
console.log("✓ Generators");
console.log("✓ Symbols and Iterators");
console.log("✓ Proxy and Reflection");
