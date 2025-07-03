/*
=== DOM MANIPULATION AND WEB APIs ===
Working with HTML elements, events, and browser APIs in JavaScript
*/

// ====================================
// 1. DOM SELECTION AND MANIPULATION
// ====================================

console.log("=== 1. DOM SELECTION AND MANIPULATION ===");

// Note: These examples assume you're running in a browser environment

// DOM Selection Methods
/*
// Single element selection
const elementById = document.getElementById('myElement');
const elementByClass = document.querySelector('.my-class');
const elementByTag = document.querySelector('div');
const elementByAttribute = document.querySelector('[data-id="123"]');

// Multiple elements selection
const elementsByClass = document.querySelectorAll('.my-class');
const elementsByTag = document.getElementsByTagName('div');
const elementsByClassName = document.getElementsByClassName('my-class');

// Creating and modifying elements
const newDiv = document.createElement('div');
newDiv.textContent = 'Hello World';
newDiv.className = 'new-element';
newDiv.setAttribute('data-info', 'example');

// Inserting elements
document.body.appendChild(newDiv);
// document.body.insertBefore(newDiv, existingElement);
// existingElement.insertAdjacentElement('beforebegin', newDiv);

// Modifying content
elementById.innerHTML = '<strong>Bold text</strong>';
elementById.textContent = 'Plain text only';

// Modifying styles
elementById.style.color = 'blue';
elementById.style.fontSize = '16px';
elementById.classList.add('active');
elementById.classList.remove('inactive');
elementById.classList.toggle('highlight');

// Getting element properties
const rect = elementById.getBoundingClientRect();
const computedStyle = window.getComputedStyle(elementById);
*/

// Example utility functions for DOM manipulation
const DOMUtils = {
    // Safe element selection
    select: (selector, context = document) => {
        return context.querySelector(selector);
    },
    
    selectAll: (selector, context = document) => {
        return Array.from(context.querySelectorAll(selector));
    },
    
    // Create element with properties
    createElement: (tag, properties = {}, children = []) => {
        const element = document.createElement(tag);
        
        Object.entries(properties).forEach(([key, value]) => {
            if (key === 'className') {
                element.className = value;
            } else if (key === 'textContent') {
                element.textContent = value;
            } else if (key.startsWith('data-')) {
                element.setAttribute(key, value);
            } else {
                element[key] = value;
            }
        });
        
        children.forEach(child => {
            if (typeof child === 'string') {
                element.appendChild(document.createTextNode(child));
            } else {
                element.appendChild(child);
            }
        });
        
        return element;
    },
    
    // Remove element safely
    remove: (element) => {
        if (element && element.parentNode) {
            element.parentNode.removeChild(element);
        }
    }
};

console.log("DOM utility functions defined");

// ====================================
// 2. EVENT HANDLING
// ====================================

console.log("\n=== 2. EVENT HANDLING ===");

// Event handler patterns
class EventManager {
    constructor() {
        this.handlers = new Map();
    }
    
    // Add event listener with cleanup tracking
    addListener(element, event, handler, options = {}) {
        const wrappedHandler = (e) => {
            try {
                handler(e);
            } catch (error) {
                console.error(`Error in ${event} handler:`, error);
            }
        };
        
        element.addEventListener(event, wrappedHandler, options);
        
        // Track for cleanup
        if (!this.handlers.has(element)) {
            this.handlers.set(element, []);
        }
        this.handlers.get(element).push({
            event,
            handler: wrappedHandler,
            options
        });
        
        return () => this.removeListener(element, event, wrappedHandler);
    }
    
    removeListener(element, event, handler) {
        element.removeEventListener(event, handler);
        
        const elementHandlers = this.handlers.get(element);
        if (elementHandlers) {
            const index = elementHandlers.findIndex(h => 
                h.event === event && h.handler === handler
            );
            if (index > -1) {
                elementHandlers.splice(index, 1);
            }
        }
    }
    
    // Remove all listeners for an element
    removeAllListeners(element) {
        const elementHandlers = this.handlers.get(element);
        if (elementHandlers) {
            elementHandlers.forEach(({ event, handler }) => {
                element.removeEventListener(event, handler);
            });
            this.handlers.delete(element);
        }
    }
}

// Event delegation utility
const EventDelegation = {
    delegate: (container, selector, event, handler) => {
        container.addEventListener(event, (e) => {
            const target = e.target.closest(selector);
            if (target && container.contains(target)) {
                handler.call(target, e);
            }
        });
    }
};

// Custom events
class CustomEventEmitter extends EventTarget {
    emit(eventName, data) {
        const event = new CustomEvent(eventName, {
            detail: data,
            bubbles: true,
            cancelable: true
        });
        this.dispatchEvent(event);
    }
    
    on(eventName, handler) {
        this.addEventListener(eventName, handler);
    }
    
    off(eventName, handler) {
        this.removeEventListener(eventName, handler);
    }
}

console.log("Event handling classes defined");

// ====================================
// 3. FORM HANDLING
// ====================================

console.log("\n=== 3. FORM HANDLING ===");

class FormValidator {
    constructor(formElement) {
        this.form = formElement;
        this.rules = new Map();
        this.errors = new Map();
    }
    
    addRule(fieldName, validator, errorMessage) {
        if (!this.rules.has(fieldName)) {
            this.rules.set(fieldName, []);
        }
        this.rules.get(fieldName).push({ validator, errorMessage });
        return this;
    }
    
    validate() {
        this.errors.clear();
        
        this.rules.forEach((rules, fieldName) => {
            const field = this.form.querySelector(`[name="${fieldName}"]`);
            if (!field) return;
            
            const value = field.value.trim();
            
            rules.forEach(({ validator, errorMessage }) => {
                if (!validator(value, field)) {
                    if (!this.errors.has(fieldName)) {
                        this.errors.set(fieldName, []);
                    }
                    this.errors.get(fieldName).push(errorMessage);
                }
            });
        });
        
        return this.errors.size === 0;
    }
    
    getErrors() {
        return Object.fromEntries(this.errors);
    }
    
    showErrors() {
        // Clear previous errors
        this.form.querySelectorAll('.error-message').forEach(el => el.remove());
        
        // Show new errors
        this.errors.forEach((messages, fieldName) => {
            const field = this.form.querySelector(`[name="${fieldName}"]`);
            if (field) {
                const errorDiv = document.createElement('div');
                errorDiv.className = 'error-message';
                errorDiv.textContent = messages.join(', ');
                field.parentNode.insertBefore(errorDiv, field.nextSibling);
            }
        });
    }
}

// Common validators
const Validators = {
    required: (value) => value.length > 0,
    minLength: (min) => (value) => value.length >= min,
    maxLength: (max) => (value) => value.length <= max,
    email: (value) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value),
    phone: (value) => /^\d{10}$/.test(value.replace(/\D/g, '')),
    number: (value) => !isNaN(parseFloat(value)),
    range: (min, max) => (value) => {
        const num = parseFloat(value);
        return !isNaN(num) && num >= min && num <= max;
    }
};

console.log("Form validation utilities defined");

// ====================================
// 4. AJAX AND FETCH API
// ====================================

console.log("\n=== 4. AJAX AND FETCH API ===");

// Modern fetch wrapper with error handling
class ApiClient {
    constructor(baseURL = '') {
        this.baseURL = baseURL;
        this.defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
            }
        };
    }
    
    async request(url, options = {}) {
        const fullUrl = this.baseURL + url;
        const config = {
            ...this.defaultOptions,
            ...options,
            headers: {
                ...this.defaultOptions.headers,
                ...options.headers
            }
        };
        
        try {
            const response = await fetch(fullUrl, config);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return await response.json();
            } else {
                return await response.text();
            }
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }
    
    get(url, options = {}) {
        return this.request(url, { ...options, method: 'GET' });
    }
    
    post(url, data, options = {}) {
        return this.request(url, {
            ...options,
            method: 'POST',
            body: JSON.stringify(data)
        });
    }
    
    put(url, data, options = {}) {
        return this.request(url, {
            ...options,
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }
    
    delete(url, options = {}) {
        return this.request(url, { ...options, method: 'DELETE' });
    }
}

// Usage example (would work with real API)
/*
const api = new ApiClient('https://jsonplaceholder.typicode.com');

async function fetchUserData() {
    try {
        const users = await api.get('/users');
        console.log('Users:', users);
        
        const newUser = await api.post('/users', {
            name: 'John Doe',
            email: 'john@example.com'
        });
        console.log('Created user:', newUser);
    } catch (error) {
        console.error('Failed to fetch user data:', error);
    }
}
*/

console.log("API client class defined");

// ====================================
// 5. LOCAL STORAGE AND SESSION MANAGEMENT
// ====================================

console.log("\n=== 5. LOCAL STORAGE AND SESSION MANAGEMENT ===");

class StorageManager {
    constructor(prefix = 'app_') {
        this.prefix = prefix;
    }
    
    // Local Storage methods
    setLocal(key, value) {
        try {
            const serializedValue = JSON.stringify({
                value,
                timestamp: Date.now(),
                type: typeof value
            });
            localStorage.setItem(this.prefix + key, serializedValue);
        } catch (error) {
            console.error('Failed to save to localStorage:', error);
        }
    }
    
    getLocal(key, defaultValue = null) {
        try {
            const item = localStorage.getItem(this.prefix + key);
            if (!item) return defaultValue;
            
            const parsed = JSON.parse(item);
            return parsed.value;
        } catch (error) {
            console.error('Failed to read from localStorage:', error);
            return defaultValue;
        }
    }
    
    removeLocal(key) {
        localStorage.removeItem(this.prefix + key);
    }
    
    clearLocal() {
        Object.keys(localStorage).forEach(key => {
            if (key.startsWith(this.prefix)) {
                localStorage.removeItem(key);
            }
        });
    }
    
    // Session Storage methods
    setSession(key, value) {
        try {
            sessionStorage.setItem(this.prefix + key, JSON.stringify(value));
        } catch (error) {
            console.error('Failed to save to sessionStorage:', error);
        }
    }
    
    getSession(key, defaultValue = null) {
        try {
            const item = sessionStorage.getItem(this.prefix + key);
            return item ? JSON.parse(item) : defaultValue;
        } catch (error) {
            console.error('Failed to read from sessionStorage:', error);
            return defaultValue;
        }
    }
    
    // Expiring storage
    setWithExpiry(key, value, expiryMinutes) {
        const expiryTime = Date.now() + (expiryMinutes * 60 * 1000);
        this.setLocal(key + '_expiry', { value, expiryTime });
    }
    
    getWithExpiry(key, defaultValue = null) {
        const item = this.getLocal(key + '_expiry');
        if (!item) return defaultValue;
        
        if (Date.now() > item.expiryTime) {
            this.removeLocal(key + '_expiry');
            return defaultValue;
        }
        
        return item.value;
    }
}

// Cookie utilities
const CookieManager = {
    set: (name, value, days = 7) => {
        const expires = new Date();
        expires.setTime(expires.getTime() + (days * 24 * 60 * 60 * 1000));
        document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
    },
    
    get: (name) => {
        const nameEQ = name + "=";
        const ca = document.cookie.split(';');
        for (let c of ca) {
            while (c.charAt(0) === ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    },
    
    remove: (name) => {
        document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
    }
};

console.log("Storage management utilities defined");

// ====================================
// 6. BROWSER APIs
// ====================================

console.log("\n=== 6. BROWSER APIs ===");

// Geolocation wrapper
class LocationService {
    static async getCurrentPosition(options = {}) {
        const defaultOptions = {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 300000 // 5 minutes
        };
        
        return new Promise((resolve, reject) => {
            if (!navigator.geolocation) {
                reject(new Error('Geolocation is not supported'));
                return;
            }
            
            navigator.geolocation.getCurrentPosition(
                resolve,
                reject,
                { ...defaultOptions, ...options }
            );
        });
    }
    
    static watchPosition(callback, errorCallback, options = {}) {
        if (!navigator.geolocation) {
            errorCallback(new Error('Geolocation is not supported'));
            return null;
        }
        
        return navigator.geolocation.watchPosition(
            callback,
            errorCallback,
            options
        );
    }
}

// Notification wrapper
class NotificationService {
    static async requestPermission() {
        if (!('Notification' in window)) {
            throw new Error('Notifications are not supported');
        }
        
        const permission = await Notification.requestPermission();
        return permission === 'granted';
    }
    
    static show(title, options = {}) {
        if (Notification.permission === 'granted') {
            return new Notification(title, {
                icon: '/favicon.ico',
                badge: '/badge.png',
                ...options
            });
        } else {
            console.warn('Notification permission not granted');
            return null;
        }
    }
}

// File handling utilities
class FileUtils {
    static readFileAsText(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => resolve(reader.result);
            reader.onerror = () => reject(reader.error);
            reader.readAsText(file);
        });
    }
    
    static readFileAsDataURL(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => resolve(reader.result);
            reader.onerror = () => reject(reader.error);
            reader.readAsDataURL(file);
        });
    }
    
    static downloadFile(content, filename, mimeType = 'text/plain') {
        const blob = new Blob([content], { type: mimeType });
        const url = URL.createObjectURL(blob);
        
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }
}

console.log("Browser API utilities defined");

// ====================================
// PRACTICAL EXAMPLES
// ====================================

console.log("\n=== PRACTICAL EXAMPLES ===");

// Example: Simple component system
class Component {
    constructor(element) {
        this.element = element;
        this.eventManager = new EventManager();
        this.init();
    }
    
    init() {
        // Override in subclasses
    }
    
    destroy() {
        this.eventManager.removeAllListeners(this.element);
    }
    
    render(template, data = {}) {
        // Simple template rendering
        let html = template;
        Object.entries(data).forEach(([key, value]) => {
            html = html.replace(new RegExp(`{{${key}}}`, 'g'), value);
        });
        this.element.innerHTML = html;
    }
}

// Example: Modal component
class Modal extends Component {
    init() {
        this.isOpen = false;
        this.bindEvents();
    }
    
    bindEvents() {
        // Close on backdrop click
        this.eventManager.addListener(this.element, 'click', (e) => {
            if (e.target === this.element) {
                this.close();
            }
        });
        
        // Close on escape key
        this.eventManager.addListener(document, 'keydown', (e) => {
            if (e.key === 'Escape' && this.isOpen) {
                this.close();
            }
        });
    }
    
    open() {
        this.isOpen = true;
        this.element.style.display = 'block';
        document.body.style.overflow = 'hidden';
    }
    
    close() {
        this.isOpen = false;
        this.element.style.display = 'none';
        document.body.style.overflow = '';
    }
}

console.log("Component examples defined");

// ====================================
// SUMMARY
// ====================================

console.log("\n=== DOM AND WEB APIS SUMMARY ===");
console.log("DOM manipulation and Web APIs covered:");
console.log("✓ DOM Selection and Manipulation");
console.log("✓ Event Handling and Delegation");
console.log("✓ Form Handling and Validation");
console.log("✓ AJAX and Fetch API");
console.log("✓ Local Storage and Session Management");
console.log("✓ Browser APIs (Geolocation, Notifications, File API)");
console.log("\nRecommended scenarios:");
console.log("• Use querySelector/querySelectorAll for element selection");
console.log("• Implement event delegation for dynamic content");
console.log("• Validate forms on both client and server side");
console.log("• Use fetch API for modern AJAX requests");
console.log("• Store user preferences in localStorage");
console.log("• Handle browser API permissions gracefully");
console.log("• Create reusable component classes");
console.log("• Always clean up event listeners to prevent memory leaks");
