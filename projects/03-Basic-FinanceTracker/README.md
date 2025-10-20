# 💰 Virtual Wallet - Carteira Digital Inteligente

> **Nível:** 🟢 Basic | **Duração:** 2-3 semanas | **Complexidade:** ⭐⭐⭐

Carteira digital construída com **Java Spring Boot** para controlar suas finanças pessoais do dia a dia. Gerencie receitas, despesas, acompanhe seu histórico completo e visualize para onde seu dinheiro está indo através de gráficos e relatórios práticos. Projeto focado em ser **realmente útil** no cotidiano enquanto você aprende Spring Boot.

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

Este não é apenas um projeto de estudo - é uma **ferramenta real** para usar no seu dia a dia!

✅ **Controle Real das Finanças**: Saiba exatamente para onde seu dinheiro está indo  
✅ **Histórico Completo**: Todas as transações armazenadas e pesquisáveis  
✅ **Categorização Inteligente**: Veja quanto gasta em cada área (alimentação, transporte, etc)  
✅ **Saldo Atual**: Sempre saiba quanto tem disponível  
✅ **Análise Mensal**: Compare seus gastos mês a mês  
✅ **Aprenda Spring Boot**: Construa algo útil enquanto estuda  
✅ **Boas Práticas**: Código organizado, seguro e profissional

### 💡 Cenário Real de Uso

**Dia 1 - Configuração Inicial:**
- Cadastrar suas categorias (Alimentação, Transporte, Lazer, Casa, etc)
- Registrar seu saldo inicial

**Dia a Dia:**
- Toda vez que gastar ou receber dinheiro → registrar na carteira
- Ver saldo atualizado em tempo real
- Acompanhar quanto já gastou no mês

**Fim do Mês:**
- Ver relatório: quanto gastou em cada categoria
- Comparar com mês anterior
- Identificar onde pode economizar

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

### 🔐 1. Sistema de Usuário

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO dto) {
        // Cadastro com email único + senha segura
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO dto) {
        // Login retorna JWT + dados do usuário
    }
}
```

**Features:**
- ✅ Cada usuário tem sua própria carteira isolada
- ✅ Login seguro com JWT
- ✅ Senha criptografada (BCrypt)
- ✅ Perfil do usuário editável

---

### 💰 2. Saldo e Carteira

```java
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    
    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getCurrentBalance() {
        // Saldo atual = soma de todas receitas - despesas
    }
    
    @PostMapping("/initial-balance")
    public ResponseEntity<Void> setInitialBalance(@RequestBody BigDecimal amount) {
        // Define saldo inicial ao começar a usar
    }
    
    @GetMapping("/balance-history")
    public ResponseEntity<List<BalanceHistoryDTO>> getBalanceHistory(
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate
    ) {
        // Evolução do saldo ao longo do tempo
    }
}
```

**Features:**
- ✅ **Saldo em tempo real**: Sempre atualizado após cada transação
- ✅ **Saldo inicial configurável**: Começa com o valor que você tem hoje
- ✅ **Histórico de saldo**: Veja como seu dinheiro evoluiu
- ✅ **Validações**: Não permite saldo negativo (opcional)

---

### 🏷️ 3. Categorias Personalizadas

```java
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @GetMapping
    public List<CategoryDTO> getMyCategories() {
        // Lista todas as categorias do usuário
    }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        // Criar categoria customizada
    }
    
    @GetMapping("/default")
    public List<CategoryDTO> getDefaultCategories() {
        // Categorias sugeridas para começar
    }
}
```

**Categorias Padrão Sugeridas:**

**DESPESAS:**
- 🍔 Alimentação (restaurantes, mercado, delivery)
- 🚗 Transporte (combustível, uber, ônibus, manutenção)
- 🏠 Casa (aluguel, contas, internet, luz, água)
- 🎮 Lazer (cinema, jogos, streaming, hobbies)
- 👕 Vestuário (roupas, sapatos, acessórios)
- 💊 Saúde (farmácia, consultas, academia)
- 📚 Educação (cursos, livros, material)
- 💳 Outros

**RECEITAS:**
- 💼 Salário
- 💰 Freelance
- 🎁 Presente/Bonificação
- 📈 Investimentos
- 💵 Outros

**Features:**
- ✅ Criar categorias ilimitadas
- ✅ Personalizar: nome, cor, ícone
- ✅ Ativar/desativar categorias
- ✅ Ver total gasto por categoria

---

### 📝 4. Gestão de Transações

```java
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @PostMapping
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody TransactionDTO dto) {
        // Adicionar receita ou despesa
        // Atualiza saldo automaticamente
    }
    
    @GetMapping
    public Page<TransactionDTO> getAll(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) TransactionType type,
        @RequestParam(required = false) String search,
        Pageable pageable
    ) {
        // Lista com filtros poderosos
    }
    
    @GetMapping("/recent")
    public List<TransactionDTO> getRecent(@RequestParam(defaultValue = "10") int limit) {
        // Últimas transações (útil no dashboard)
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id, @Valid @RequestBody TransactionDTO dto) {
        // Editar transação (recalcula saldo)
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Deletar transação (recalcula saldo)
    }
}
```

**Features:**
- ✅ **Adicionar rápido**: Descrição, valor, categoria, data
- ✅ **Histórico completo**: Todas as transações salvas
- ✅ **Busca avançada**: Por descrição, categoria, período, tipo
- ✅ **Editar/Deletar**: Corrigir erros facilmente
- ✅ **Paginação**: Carrega rápido mesmo com muitos dados
- ✅ **Anexar nota**: Campo opcional para observações

---

### 📊 5. Relatórios e Análises

```java
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard() {
        // Dashboard principal com resumo do mês atual
        return {
            currentBalance: 2450.00,
            monthIncome: 5000.00,
            monthExpense: 2550.00,
            mostExpensiveCategory: "Alimentação (R$ 800)",
            transactionCount: 45,
            averageExpense: 56.67
        };
    }
    
    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<MonthlyReportDTO> getMonthlyReport(
        @PathVariable int year, 
        @PathVariable int month
    ) {
        // Relatório completo do mês
        return {
            totalIncome: 5000.00,
            totalExpense: 2550.00,
            balance: 2450.00,
            expenseByCategory: [
                { category: "Alimentação", amount: 800.00, percentage: 31% },
                { category: "Transporte", amount: 450.00, percentage: 18% },
                { category: "Casa", amount: 800.00, percentage: 31% },
                { category: "Lazer", amount: 300.00, percentage: 12% },
                { category: "Outros", amount: 200.00, percentage: 8% }
            ],
            topExpenses: [...],
            comparisonWithLastMonth: { ... }
        };
    }
    
    @GetMapping("/yearly/{year}")
    public ResponseEntity<YearlyReportDTO> getYearlyReport(@PathVariable int year) {
        // Visão anual: evolução mês a mês
    }
    
    @GetMapping("/category/{categoryId}/history")
    public ResponseEntity<CategoryHistoryDTO> getCategoryHistory(
        @PathVariable Long categoryId,
        @RequestParam int months // últimos N meses
    ) {
        // Quanto gastou nessa categoria nos últimos meses
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToCSV(
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate
    ) {
        // Exportar transações para Excel/CSV
    }
}
```

**Features Dashboard:**
- ✅ **Saldo atual** em destaque
- ✅ **Resumo do mês**: Total receitas vs despesas
- ✅ **Gráfico de pizza**: Distribuição por categoria (%)
- ✅ **Gráfico de linha**: Evolução do saldo nos últimos 6 meses
- ✅ **Top 5 maiores gastos** do mês
- ✅ **Comparação**: Este mês vs mês anterior
- ✅ **Média diária** de gastos
- ✅ **Dias até acabar o dinheiro** (previsão baseada no padrão)

**Features Relatórios:**
- ✅ **Mensal**: Detalhamento completo do mês
- ✅ **Anual**: Visão de 12 meses
- ✅ **Por categoria**: Acompanhar tendências
- ✅ **Exportar**: Baixar dados em CSV/Excel
- ✅ **Filtros personalizados**: Qualquer período

---

### 🔍 6. Pesquisa e Filtros

```java
@GetMapping("/transactions/search")
public Page<TransactionDTO> search(
    @RequestParam String query,              // Busca na descrição
    @RequestParam(required = false) LocalDate startDate,
    @RequestParam(required = false) LocalDate endDate,
    @RequestParam(required = false) Long categoryId,
    @RequestParam(required = false) TransactionType type,
    @RequestParam(required = false) BigDecimal minAmount,
    @RequestParam(required = false) BigDecimal maxAmount,
    Pageable pageable
) {
    // Exemplo: "Encontre todos os gastos com 'uber' entre R$20 e R$50 em Março"
}
```

**Features:**
- ✅ Buscar por descrição (ex: "uber", "ifood")
- ✅ Filtrar por período customizado
- ✅ Filtrar por categoria
- ✅ Filtrar por tipo (receita ou despesa)
- ✅ Filtrar por faixa de valor
- ✅ Combinar múltiplos filtros

---

### 📱 7. Funcionalidades Extras Úteis

```java
// Metas de gastos
@PostMapping("/api/goals")
public ResponseEntity<GoalDTO> createGoal(@Valid @RequestBody GoalDTO dto) {
    // Ex: "Gastar no máximo R$ 500 em Alimentação este mês"
}

@GetMapping("/api/goals/status")
public List<GoalStatusDTO> getGoalsStatus() {
    // Mostra quanto falta/ultrapassou de cada meta
}

// Transações recorrentes (sugestão)
@GetMapping("/api/transactions/recurring-suggestion")
public List<RecurringSuggestionDTO> getRecurringSuggestions() {
    // Detecta padrões: "Você gasta R$ 800 todo dia 10 em 'Aluguel'"
}

// Insights automáticos
@GetMapping("/api/insights")
public List<InsightDTO> getInsights() {
    // "Você gastou 25% a mais em Alimentação este mês"
    // "Seu maior gasto foi R$ 150 em 'Restaurante X'"
    // "Você economizou R$ 200 comparado ao mês passado"
}
```

**Features Extras:**
- ✅ **Metas por categoria**: Alerta quando ultrapassar
- ✅ **Insights automáticos**: Sistema avisa sobre padrões
- ✅ **Sugestão de recorrentes**: Detecta gastos fixos
- ✅ **Notificações**: Avisos quando próximo do limite
- ✅ **Tags personalizadas**: Adicionar etiquetas às transações

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

### Modelagem Completa

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
    
    private BigDecimal initialBalance; // Saldo inicial configurado
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
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
    private String icon; // "shopping-cart"
    
    private Boolean active = true; // Pode desativar sem deletar
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;
}

@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type; // INCOME, EXPENSE
    
    private String notes; // Observações opcionais
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

@Entity
@Table(name = "spending_goals")
public class SpendingGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    private BigDecimal targetAmount; // Meta: ex: R$ 500
    
    private Integer month; // 1-12
    private Integer year;
    
    private Boolean active = true;
}
```

### Queries Úteis

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Buscar com filtros
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId " +
           "AND (:startDate IS NULL OR t.date >= :startDate) " +
           "AND (:endDate IS NULL OR t.date <= :endDate) " +
           "AND (:categoryId IS NULL OR t.category.id = :categoryId) " +
           "AND (:type IS NULL OR t.type = :type) " +
           "AND (:search IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Transaction> findWithFilters(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("categoryId") Long categoryId,
        @Param("type") TransactionType type,
        @Param("search") String search,
        Pageable pageable
    );
    
    // Calcular saldo atual
    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE -t.amount END), 0) " +
           "FROM Transaction t WHERE t.user.id = :userId")
    BigDecimal calculateCurrentBalance(@Param("userId") Long userId);
    
    // Total de receitas do período
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'INCOME' " +
           "AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalIncome(@Param("userId") Long userId,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate);
    
    // Total de despesas do período
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
           "AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpense(@Param("userId") Long userId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);
    
    // Gastos por categoria no período
    @Query("SELECT t.category.name as categoryName, SUM(t.amount) as total " +
           "FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
           "AND t.date BETWEEN :startDate AND :endDate " +
           "GROUP BY t.category.id, t.category.name " +
           "ORDER BY total DESC")
    List<CategoryExpenseProjection> getExpensesByCategory(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    // Top N maiores gastos
    @Query("SELECT t FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
           "AND t.date BETWEEN :startDate AND :endDate " +
           "ORDER BY t.amount DESC")
    List<Transaction> findTopExpenses(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );
    
    // Últimas N transações
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.date DESC, t.createdAt DESC")
    List<Transaction> findRecentTransactions(@Param("userId") Long userId, Pageable pageable);
    
    // Evolução do saldo (por mês)
    @Query("SELECT FUNCTION('MONTH', t.date) as month, " +
           "FUNCTION('YEAR', t.date) as year, " +
           "SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE -t.amount END) as balance " +
           "FROM Transaction t " +
           "WHERE t.user.id = :userId " +
           "GROUP BY FUNCTION('YEAR', t.date), FUNCTION('MONTH', t.date) " +
           "ORDER BY year DESC, month DESC")
    List<MonthlyBalanceProjection> getMonthlyBalanceHistory(@Param("userId") Long userId);
}

// Projection para gastos por categoria
public interface CategoryExpenseProjection {
    String getCategoryName();
    BigDecimal getTotal();
}

// Projection para histórico mensal
public interface MonthlyBalanceProjection {
    Integer getMonth();
    Integer getYear();
    BigDecimal getBalance();
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

Depois de completar o projeto básico e usar no dia a dia, você pode adicionar:

### Features Práticas (Fácil)
- 📊 **Export Excel/CSV**: Baixar todas as transações
- 🔔 **Notificações por Email**: Avisar quando atingir meta
- 📅 **Transações recorrentes**: Marcar gastos fixos (aluguel, assinatura)
- 💳 **Múltiplas contas**: Carteira, banco, investimentos separados
- 📱 **PWA**: Instalar como app no celular
- 🌙 **Dark mode**: Tema escuro
- 📸 **Upload de recibo**: Anexar foto do comprovante

### Features Intermediárias (Médio)
- 🎯 **Metas avançadas**: "Economizar R$ 1000 até Dezembro"
- � **Gráficos avançados**: Comparações, tendências, previsões
- 🤝 **Compartilhar carteira**: Gerenciar finanças em casal/família
- 🏦 **Conta compartilhada**: Rachar despesas
- 💰 **Controle de dívidas**: Gerenciar empréstimos e parcelamentos
- 📈 **Investimentos**: Acompanhar rentabilidade
- 🔄 **Sincronização**: Backup automático na nuvem

### Features Avançadas (Difícil)
- 🤖 **IA para categorização**: Sugerir categoria automaticamente
- 🏦 **Open Banking**: Importar transações do banco automaticamente
- 📊 **Análise preditiva**: "Você vai gastar R$ X este mês"
- � **Insights inteligentes**: "Você pode economizar R$ Y cortando Z"
- 🌍 **Multi-moeda**: Viagens internacionais
- 📱 **App mobile nativo**: React Native ou Flutter
- 🔐 **Biometria**: Login com digital/face

### Melhorias de UX
- ⚡ **Adicionar transação rápido**: Modal sempre disponível
- � **Personalização**: Escolher tema, cores, layout
- 📊 **Widgets customizáveis**: Montar seu dashboard
- 🔍 **Busca avançada**: Filtros salvos
- ⌨️ **Atalhos de teclado**: Agilizar navegação
- 📱 **Responsividade total**: Perfeito em qualquer tela

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

**Próximo projeto recomendado:** [E-commerce](../04-Intermediate-Ecommerce/) ou [Blog Platform](../05-Intermediate-Blog/)
