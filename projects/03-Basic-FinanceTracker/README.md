# 💰 Personal Finance Tracker - Controle Financeiro Pessoal

> **Nível:** 🟢 Basic | **Duração:** 2-3 semanas | **Complexidade:** ⭐⭐⭐

Sistema de controle financeiro pessoal construído com **Java Spring Boot** para gerenciar receitas, despesas e visualizar gastos através de relatórios simples. Projeto focado em aprender os fundamentos de uma aplicação web completa.

---

## 📖 Índice

- [Por que este projeto?](#-por-que-este-projeto)
- [O que você vai aprender](#-o-que-você-vai-aprender)
- [Arquitetura](#️-arquitetura)
- [Stack Tecnológico](#️-stack-tecnológico)
- [Funcionalidades](#-funcionalidades)
- [Estrutura do Projeto](#️-estrutura-do-projeto)
- [Banco de Dados](#️-banco-de-dados)
- [Guia de Implementação](#-guia-de-implementação)
- [Como Rodar](#-como-rodar)
- [Testando a API](#-testando-a-api)
- [Melhorias Futuras](#-melhorias-futuras)
- [Recursos](#-recursos)

---

## 🎯 Por que este projeto?

✅ **Primeiro contato com Spring Boot**: Entenda como criar APIs REST  
✅ **CRUD Completo**: Criar, Ler, Atualizar e Deletar dados  
✅ **Banco de Dados**: Trabalhar com JPA/Hibernate e PostgreSQL  
✅ **Autenticação**: Implementar login simples com JWT  
✅ **Frontend Integrado**: Conectar React com backend Java  
✅ **Deploy**: Colocar sua aplicação no ar (Heroku/Railway)  
✅ **Boas Práticas**: Código organizado em camadas (Controller → Service → Repository)

---

## 📚 O que você vai aprender

### Backend com Java Spring Boot

- ✅ Criar projeto Spring Boot do zero (Spring Initializr)
- ✅ API REST com Controllers (@RestController, @GetMapping, @PostMapping)
- ✅ Arquitetura em 3 camadas (Controller → Service → Repository)
- ✅ JPA/Hibernate para trabalhar com banco de dados
- ✅ Validações com Bean Validation (@NotNull, @Valid)
- ✅ Exception Handling personalizado (@ControllerAdvice)
- ✅ Autenticação JWT básica com Spring Security
- ✅ Documentação automática com Swagger/SpringDoc

### Banco de Dados

- ✅ Modelagem de dados (Usuários, Categorias, Transações)
- ✅ PostgreSQL/MySQL básico
- ✅ Relacionamentos JPA (OneToMany, ManyToOne)
- ✅ Queries personalizadas com @Query e JPQL
- ✅ Migrations com Flyway (opcional)

### Frontend com React

- ✅ Criar interface com React + TypeScript
- ✅ Chamar API com Axios/Fetch
- ✅ Gerenciar estado com useState/useEffect
- ✅ Formulários e validação
- ✅ Gráficos simples com Chart.js ou Recharts
- ✅ Tabelas responsivas com paginação

### DevOps Básico

- ✅ Docker básico (Dockerfile, docker-compose)
- ✅ Deploy em plataforma cloud (Heroku/Railway/Render)
- ✅ Variáveis de ambiente (.env)
- ✅ Git & GitHub

---

## 🏗️ Arquitetura

Sistema monolítico com arquitetura em 3 camadas:

```
┌─────────────────────────────────────────────────────┐
│           FRONTEND (React + TypeScript)             │
│  Dashboard | Transações | Categorias | Relatórios  │
└─────────────────────────────────────────────────────┘
                      ↓ HTTP (Axios)
┌─────────────────────────────────────────────────────┐
│         BACKEND (Spring Boot - Porta 8080)          │
├─────────────────────────────────────────────────────┤
│  CONTROLLER LAYER                                   │
│  • AuthController                                   │
│  • TransactionController                            │
│  • CategoryController                               │
│  • ReportController                                 │
├─────────────────────────────────────────────────────┤
│  SERVICE LAYER                                      │
│  • AuthService                                      │
│  • TransactionService                               │
│  • CategoryService                                  │
│  • ReportService                                    │
├─────────────────────────────────────────────────────┤
│  REPOSITORY LAYER                                   │
│  • UserRepository                                   │
│  • TransactionRepository                            │
│  • CategoryRepository                               │
└─────────────────────────────────────────────────────┘
                      ↓ JPA/Hibernate
┌─────────────────────────────────────────────────────┐
│         BANCO DE DADOS (PostgreSQL)                 │
│  • users                                            │
│  • categories                                       │
│  • transactions                                     │
└─────────────────────────────────────────────────────┘
```

### Fluxo de uma requisição

```
1. Usuário preenche formulário no React
   ↓
2. POST /api/transactions
   Headers: Authorization: Bearer {jwt}
   Body: { description, amount, date, categoryId, type }
   ↓
3. TransactionController valida JWT e dados
   ↓
4. TransactionService processa regras de negócio
   ↓
5. TransactionRepository salva no PostgreSQL
   ↓
6. Retorna 201 Created
   ↓
7. Frontend atualiza lista
```

---

## 🛠️ Stack Tecnológico

### Backend

```xml
<!-- Spring Boot 3.x com Java 17 -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

### Frontend

```json
{
  "dependencies": {
    "react": "^18.2.0",
    "react-router-dom": "^6.20.0",
    "axios": "^1.6.0",
    "chart.js": "^4.4.0",
    "react-chartjs-2": "^5.2.0",
    "react-hook-form": "^7.48.0",
    "zod": "^3.22.0",
    "date-fns": "^2.30.0"
  },
  "devDependencies": {
    "@vitejs/plugin-react": "^4.2.0",
    "typescript": "^5.3.0",
    "vite": "^5.0.0",
    "tailwindcss": "^3.3.0"
  }
}
```

---

## 📋 Funcionalidades

### 1. Autenticação de Usuários

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO dto) {
        // Registra novo usuário
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO dto) {
        // Retorna JWT token
    }
}
```

**Features:**
- ✅ Registro com email único
- ✅ Login com JWT (válido 24h)
- ✅ Senha com BCrypt
- ✅ Logout (frontend limpa token)

### 2. Gestão de Categorias

```java
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @GetMapping
    public List<CategoryDTO> getUserCategories() { }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) { }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) { }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { }
}
```

**Features:**
- ✅ CRUD completo
- ✅ Tipos: RECEITA ou DESPESA
- ✅ Personalização: nome, cor, ícone
- ✅ Categorias padrão no registro

### 3. Gestão de Transações

```java
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @GetMapping
    public Page<TransactionDTO> getAll(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) TransactionType type,
        Pageable pageable
    ) { }
    
    @PostMapping
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody TransactionDTO dto) { }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id, @Valid @RequestBody TransactionDTO dto) { }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { }
}
```

**Features:**
- ✅ CRUD completo
- ✅ Filtros: período, categoria, tipo
- ✅ Paginação (10 itens/página)
- ✅ Ordenação por data
- ✅ Validações: valor > 0, data não futura

### 4. Relatórios e Dashboard

```java
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate
    ) {
        // Total receitas, despesas, saldo
        // Dados para gráficos
    }
    
    @GetMapping("/monthly/{year}/{month}")
    public MonthlyReportDTO getMonthlyReport(@PathVariable int year, @PathVariable int month) {
        // Relatório detalhado do mês
    }
}
```

**Features:**
- ✅ Resumo: receitas, despesas, saldo
- ✅ Gráfico pizza: gastos por categoria
- ✅ Gráfico linha: evolução mensal
- ✅ Filtro por período
- ✅ Top 5 maiores gastos

---

## 🗂️ Estrutura do Projeto

```
finance-tracker/
├── backend/
│   ├── src/main/java/com/finance/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── OpenAPIConfig.java
│   │   │   └── CorsConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── TransactionController.java
│   │   │   ├── CategoryController.java
│   │   │   └── ReportController.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   ├── TransactionService.java
│   │   │   ├── CategoryService.java
│   │   │   └── ReportService.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   └── CategoryRepository.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   ├── Transaction.java
│   │   │   └── Category.java
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── exception/
│   │   │   └── GlobalExceptionHandler.java
│   │   └── security/
│   │       ├── JwtUtil.java
│   │       └── JwtAuthFilter.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Dashboard/
│   │   │   ├── Transactions/
│   │   │   ├── Categories/
│   │   │   ├── Layout/
│   │   │   └── Common/
│   │   ├── pages/
│   │   │   ├── Login.tsx
│   │   │   ├── Register.tsx
│   │   │   ├── Dashboard.tsx
│   │   │   ├── Transactions.tsx
│   │   │   └── Categories.tsx
│   │   ├── services/
│   │   │   └── api.ts
│   │   ├── contexts/
│   │   │   └── AuthContext.tsx
│   │   └── App.tsx
│   └── package.json
│
├── docker-compose.yml
└── README.md
```

---

## 🗄️ Banco de Dados

### Modelagem

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password; // BCrypt
    
    private String name;
    
    @OneToMany(mappedBy = "user")
    private List<Category> categories;
    
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
}

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private CategoryType type; // INCOME, EXPENSE
    
    private String color; // "#FF5733"
    private String icon;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type; // INCOME, EXPENSE
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

### Queries Úteis

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId " +
           "AND (:startDate IS NULL OR t.date >= :startDate) " +
           "AND (:endDate IS NULL OR t.date <= :endDate)")
    Page<Transaction> findWithFilters(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'INCOME' " +
           "AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalIncome(@Param("userId") Long userId,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate);
}
```

---

## 🚀 Guia de Implementação

### Semana 1: Backend (5 dias)

#### Dia 1: Setup e Entidades
- [ ] Criar projeto no Spring Initializr
- [ ] Configurar PostgreSQL com Docker
- [ ] Criar entidades (User, Category, Transaction)
- [ ] Criar repositories
- [ ] Testar conexão com banco

#### Dia 2: Autenticação
- [ ] Implementar JwtUtil
- [ ] Criar JwtAuthFilter
- [ ] Configurar SecurityConfig
- [ ] Implementar AuthService
- [ ] Criar AuthController
- [ ] Testar com Postman

#### Dia 3: CRUD de Categorias
- [ ] Implementar CategoryService
- [ ] Criar CategoryController
- [ ] Adicionar categorias padrão
- [ ] Testar endpoints

#### Dia 4: CRUD de Transações
- [ ] Implementar TransactionService
- [ ] Criar TransactionController
- [ ] Implementar filtros e paginação
- [ ] Testar endpoints

#### Dia 5: Relatórios e Documentação
- [ ] Implementar ReportService
- [ ] Criar ReportController
- [ ] Configurar Swagger
- [ ] Global Exception Handler
- [ ] Testar tudo

### Semana 2: Frontend (5 dias)

#### Dia 1: Setup e Autenticação
- [ ] Criar projeto React com Vite
- [ ] Configurar Tailwind CSS
- [ ] Criar páginas Login/Register
- [ ] Implementar AuthContext
- [ ] Configurar Axios
- [ ] Protected routes

#### Dia 2: Dashboard
- [ ] Criar layout (Navbar, Sidebar)
- [ ] Página Dashboard
- [ ] Cards de resumo
- [ ] Integrar com API
- [ ] Loading states

#### Dia 3: Transações
- [ ] Lista de transações
- [ ] Formulário criar/editar
- [ ] Modal
- [ ] Filtros
- [ ] Paginação

#### Dia 4: Categorias e Gráficos
- [ ] CRUD de categorias
- [ ] Instalar Chart.js
- [ ] Gráfico de pizza
- [ ] Gráfico de linha
- [ ] Página de relatórios

#### Dia 5: Refinamentos
- [ ] Validações
- [ ] Mensagens de erro/sucesso
- [ ] Formatação de valores
- [ ] Responsividade
- [ ] Testes

### Semana 3: Deploy (3 dias)

#### Dia 1: Docker
- [ ] Dockerfile para backend
- [ ] docker-compose.yml
- [ ] Testar localmente

#### Dia 2: Deploy
- [ ] Deploy backend (Railway/Render)
- [ ] Deploy frontend (Vercel/Netlify)
- [ ] Configurar variáveis de ambiente
- [ ] Testar produção

#### Dia 3: Documentação
- [ ] README completo
- [ ] Screenshots
- [ ] Vídeo demo
- [ ] Adicionar ao portfólio

---

## 🔧 Como Rodar

### Com Docker (Recomendado)

```bash
# Clonar repositório
git clone https://github.com/seu-usuario/finance-tracker.git
cd finance-tracker

# Subir toda a stack
docker-compose up -d

# Acessar:
# Backend: http://localhost:8080
# Frontend: http://localhost:5173
# Swagger: http://localhost:8080/swagger-ui.html
```

### Sem Docker

#### Backend

```bash
cd backend

# Subir PostgreSQL
docker run --name postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:15-alpine

# Configurar application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financedb
spring.datasource.username=postgres
spring.datasource.password=postgres

# Rodar aplicação
./mvnw spring-boot:run
```

#### Frontend

```bash
cd frontend

# Instalar dependências
npm install

# Rodar em desenvolvimento
npm run dev
```

---

## 🧪 Testando a API

### Exemplos com curl

```bash
# 1. Registrar usuário
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "password": "senha123",
    "name": "João Silva"
  }'

# 2. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "password": "senha123"
  }'
# Response: { "token": "eyJhbGc...", "user": {...} }

# 3. Criar categoria (usar token do login)
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGc..." \
  -d '{
    "name": "Alimentação",
    "type": "EXPENSE",
    "color": "#FF5733",
    "icon": "shopping-cart"
  }'

# 4. Criar transação
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGc..." \
  -d '{
    "description": "Supermercado",
    "amount": 250.50,
    "date": "2024-10-15",
    "type": "EXPENSE",
    "categoryId": 1
  }'

# 5. Listar transações
curl http://localhost:8080/api/transactions?page=0&size=10 \
  -H "Authorization: Bearer eyJhbGc..."

# 6. Dashboard
curl http://localhost:8080/api/reports/summary \
  -H "Authorization: Bearer eyJhbGc..."
```

---

## 🎯 Melhorias Futuras

Depois de completar o projeto básico, você pode adicionar:

### Features Intermediárias
- 📊 Export de relatórios em PDF/CSV
- 🔔 Sistema de notificações
- 📅 Transações recorrentes
- 💳 Gestão de múltiplas contas
- 🎯 Metas de economia
- 📱 PWA (app instalável)
- 🌙 Dark mode

### Features Avançadas
- 🤖 Categorização automática com AI
- 🏦 Integração com Open Banking
- 📈 Gráficos mais complexos (D3.js)
- 🔐 Two-Factor Authentication
- 🌍 Multi-idioma (i18n)
- 📊 Dashboard analytics avançado

---

## 📚 Recursos

### Documentação
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [React Docs](https://react.dev/)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)

### Tutoriais
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)
- [React Router](https://reactrouter.com/)
- [Docker Get Started](https://docs.docker.com/get-started/)

---

## 📄 Licença

MIT License - use como quiser para aprender! 🚀

---

**Próximo projeto recomendado:** [E-commerce](../04-Intermediate-Ecommerce/) ou [Blog Platform](../05-Intermediate-Blog/)
