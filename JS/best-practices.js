/*
=== JAVASCRIPT BEST PRACTICES AND DESIGN PATTERNS ===
Common patterns, conventions, and recommended practices for JavaScript development
*/

// ====================================
// 1. CODING CONVENTIONS AND BEST PRACTICES
// ====================================

console.log("=== 1. CODING CONVENTIONS AND BEST PRACTICES ===");

// ✅ GOOD: Use meaningful variable names
const userAccountBalance = 1500;
const isUserLoggedIn = true;
const getUserPreferences = () => ({ theme: 'dark', language: 'en' });

// ❌ BAD: Unclear variable names
// const x = 1500;
// const flag = true;
// const getData = () => ({ theme: 'dark', language: 'en' });

// ✅ GOOD: Use const by default, let when reassignment is needed
const apiUrl = 'https://api.example.com';
let currentUser = null;

// ✅ GOOD: Use template literals for string interpolation
const welcomeMessage = `Welcome back, ${currentUser?.name || 'Guest'}!`;

// ✅ GOOD: Use optional chaining and nullish coalescing
const userEmail = currentUser?.profile?.email ?? 'No email provided';

console.log("Best practices example:", { userAccountBalance, welcomeMessage, userEmail });

// ====================================
// 2. DESIGN PATTERNS
// ====================================

console.log("\n=== 2. DESIGN PATTERNS ===");

// Singleton Pattern
class DatabaseConnection {
    constructor() {
        if (DatabaseConnection.instance) {
            return DatabaseConnection.instance;
        }
        
        this.isConnected = false;
        DatabaseConnection.instance = this;
        return this;
    }
    
    connect() {
        if (!this.isConnected) {
            console.log("Connecting to database...");
            this.isConnected = true;
        }
        return this;
    }
    
    query(sql) {
        if (!this.isConnected) {
            throw new Error("Database not connected");
        }
        return `Executing: ${sql}`;
    }
}

// Observer Pattern
class EventEmitter {
    constructor() {
        this.events = {};
    }
    
    on(event, callback) {
        if (!this.events[event]) {
            this.events[event] = [];
        }
        this.events[event].push(callback);
    }
    
    emit(event, data) {
        if (this.events[event]) {
            this.events[event].forEach(callback => callback(data));
        }
    }
    
    off(event, callback) {
        if (this.events[event]) {
            this.events[event] = this.events[event].filter(cb => cb !== callback);
        }
    }
}

// Factory Pattern
class ShapeFactory {
    static createShape(type, ...args) {
        switch (type.toLowerCase()) {
            case 'circle':
                return new Circle(...args);
            case 'rectangle':
                return new Rectangle(...args);
            case 'triangle':
                return new Triangle(...args);
            default:
                throw new Error(`Unknown shape type: ${type}`);
        }
    }
}

class Circle {
    constructor(radius) {
        this.radius = radius;
        this.type = 'circle';
    }
    
    area() {
        return Math.PI * this.radius ** 2;
    }
}

class Rectangle {
    constructor(width, height) {
        this.width = width;
        this.height = height;
        this.type = 'rectangle';
    }
    
    area() {
        return this.width * this.height;
    }
}

class Triangle {
    constructor(base, height) {
        this.base = base;
        this.height = height;
        this.type = 'triangle';
    }
    
    area() {
        return (this.base * this.height) / 2;
    }
}

// Using design patterns
const db1 = new DatabaseConnection();
const db2 = new DatabaseConnection();
console.log("Singleton test:", db1 === db2); // true

const emitter = new EventEmitter();
emitter.on('user-login', (user) => console.log(`User ${user.name} logged in`));
emitter.emit('user-login', { name: 'Alice' });

const circle = ShapeFactory.createShape('circle', 5);
const rectangle = ShapeFactory.createShape('rectangle', 4, 6);
console.log("Circle area:", circle.area());
console.log("Rectangle area:", rectangle.area());

// ====================================
// 3. FUNCTIONAL PROGRAMMING CONCEPTS
// ====================================

console.log("\n=== 3. FUNCTIONAL PROGRAMMING CONCEPTS ===");

// Pure functions (no side effects, same input = same output)
const add = (a, b) => a + b;
const multiply = (a, b) => a * b;

// Higher-order functions
const compose = (...functions) => (value) => 
    functions.reduceRight((acc, fn) => fn(acc), value);

const pipe = (...functions) => (value) => 
    functions.reduce((acc, fn) => fn(acc), value);

// Function composition examples
const addOne = x => x + 1;
const double = x => x * 2;
const square = x => x * x;

const addOneAndDouble = compose(double, addOne);
const processNumber = pipe(addOne, double, square);

console.log("Compose (double(addOne(5))):", addOneAndDouble(5)); // 12
console.log("Pipe (square(double(addOne(5)))):", processNumber(5)); // 144

// Currying
const curriedMultiply = (a) => (b) => a * b;
const multiplyByTwo = curriedMultiply(2);
const multiplyByThree = curriedMultiply(3);

console.log("Curried multiply by 2:", multiplyByTwo(5)); // 10
console.log("Curried multiply by 3:", multiplyByThree(4)); // 12

// Partial application
const partial = (fn, ...args1) => (...args2) => fn(...args1, ...args2);

const log = (level, message, details) => 
    console.log(`[${level}] ${message}`, details || '');

const logError = partial(log, 'ERROR');
const logInfo = partial(log, 'INFO');

logError("Something went wrong", { error: "404" });
logInfo("Process completed successfully");

// ====================================
// 4. MEMORY MANAGEMENT AND PERFORMANCE
// ====================================

console.log("\n=== 4. MEMORY MANAGEMENT AND PERFORMANCE ===");

// Debouncing (limit function calls)
function debounce(func, delay) {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => func.apply(this, args), delay);
    };
}

// Throttling (limit function execution rate)
function throttle(func, limit) {
    let inThrottle;
    return function (...args) {
        if (!inThrottle) {
            func.apply(this, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// Memoization (cache function results)
function memoize(fn) {
    const cache = new Map();
    return function (...args) {
        const key = JSON.stringify(args);
        if (cache.has(key)) {
            console.log("Cache hit for:", key);
            return cache.get(key);
        }
        const result = fn.apply(this, args);
        cache.set(key, result);
        console.log("Cache miss for:", key);
        return result;
    };
}

// Example: Expensive calculation with memoization
const expensiveCalculation = memoize((n) => {
    console.log(`Calculating factorial of ${n}...`);
    if (n <= 1) return 1;
    return n * expensiveCalculation(n - 1);
});

console.log("Factorial 5:", expensiveCalculation(5));
console.log("Factorial 5 again:", expensiveCalculation(5)); // Cache hit

// WeakMap for memory-efficient associations
const privateData = new WeakMap();

class UserAccount {
    constructor(username) {
        this.username = username;
        privateData.set(this, {
            password: 'secret123',
            sessionToken: 'abc123xyz'
        });
    }
    
    authenticate(password) {
        const data = privateData.get(this);
        return data.password === password;
    }
    
    getSessionToken() {
        return privateData.get(this).sessionToken;
    }
}

const account = new UserAccount("alice");
console.log("Authentication:", account.authenticate("secret123"));

// ====================================
// 5. ERROR HANDLING PATTERNS
// ====================================

console.log("\n=== 5. ERROR HANDLING PATTERNS ===");

// Custom error classes
class ValidationError extends Error {
    constructor(message, field) {
        super(message);
        this.name = 'ValidationError';
        this.field = field;
    }
}

class NetworkError extends Error {
    constructor(message, statusCode) {
        super(message);
        this.name = 'NetworkError';
        this.statusCode = statusCode;
    }
}

// Result pattern (alternative to throwing errors)
class Result {
    constructor(success, data, error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
    
    static ok(data) {
        return new Result(true, data, null);
    }
    
    static error(error) {
        return new Result(false, null, error);
    }
    
    map(fn) {
        if (this.success) {
            try {
                return Result.ok(fn(this.data));
            } catch (error) {
                return Result.error(error);
            }
        }
        return this;
    }
    
    flatMap(fn) {
        if (this.success) {
            try {
                return fn(this.data);
            } catch (error) {
                return Result.error(error);
            }
        }
        return this;
    }
}

// Usage example
function parseNumber(str) {
    const num = parseFloat(str);
    if (isNaN(num)) {
        return Result.error(new Error(`"${str}" is not a valid number`));
    }
    return Result.ok(num);
}

function sqrt(num) {
    if (num < 0) {
        return Result.error(new Error("Cannot calculate square root of negative number"));
    }
    return Result.ok(Math.sqrt(num));
}

const result = parseNumber("16")
    .flatMap(sqrt)
    .map(x => x * 2);

if (result.success) {
    console.log("Result:", result.data);
} else {
    console.error("Error:", result.error.message);
}

// ====================================
// 6. ASYNC PATTERNS
// ====================================

console.log("\n=== 6. ASYNC PATTERNS ===");

// Promise-based retry mechanism
async function retryOperation(operation, maxRetries = 3, delay = 1000) {
    for (let attempt = 1; attempt <= maxRetries; attempt++) {
        try {
            return await operation();
        } catch (error) {
            console.log(`Attempt ${attempt} failed:`, error.message);
            
            if (attempt === maxRetries) {
                throw error;
            }
            
            // Wait before retrying
            await new Promise(resolve => setTimeout(resolve, delay));
        }
    }
}

// Queue for managing async operations
class AsyncQueue {
    constructor(concurrency = 1) {
        this.concurrency = concurrency;
        this.running = 0;
        this.queue = [];
    }
    
    async add(task) {
        return new Promise((resolve, reject) => {
            this.queue.push({
                task,
                resolve,
                reject
            });
            this.process();
        });
    }
    
    async process() {
        if (this.running >= this.concurrency || this.queue.length === 0) {
            return;
        }
        
        this.running++;
        const { task, resolve, reject } = this.queue.shift();
        
        try {
            const result = await task();
            resolve(result);
        } catch (error) {
            reject(error);
        } finally {
            this.running--;
            this.process();
        }
    }
}

// ====================================
// SUMMARY
// ====================================

console.log("\n=== BEST PRACTICES SUMMARY ===");
console.log("JavaScript best practices and patterns covered:");
console.log("✓ Coding Conventions and Best Practices");
console.log("✓ Design Patterns (Singleton, Observer, Factory)");
console.log("✓ Functional Programming Concepts");
console.log("✓ Memory Management and Performance");
console.log("✓ Error Handling Patterns");
console.log("✓ Async Patterns");
console.log("\nRecommended scenarios:");
console.log("• Use const/let instead of var");
console.log("• Prefer arrow functions for short functions");
console.log("• Use template literals for string interpolation");
console.log("• Implement proper error handling");
console.log("• Use async/await for async operations");
console.log("• Apply design patterns for scalable code");
console.log("• Use functional programming for data transformations");
console.log("• Implement performance optimizations when needed");
