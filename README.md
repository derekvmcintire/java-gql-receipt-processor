# Receipt Processor API Documentation

## Overview

The **ReceiptProcessor** API is a Java-based Spring Boot (demo) application that processes receipts and assigns loyalty points based on contrived rules. The API is built using Spring Boot, GraphQL, and Memcached, and utilizes an in-memory database for storing receipts. This documentation outlines how to set up, run, and interact with the API.

---

### Prerequisites

Before setting up and running the project, ensure you have the following installed:

1. **Java 17 (or later)**  
   Required for building and running the project.

2. **Maven (Optional)**  
   Maven is typically used for building the project. However, if you do not wish to install Maven, the project includes a wrapper script (`mvnw`) that can be used instead.  

   - **To use Maven wrapper**: Run `./mvnw` (Linux/macOS) or `mvnw.cmd` (Windows) for all Maven-related commands.  
   - Example:  
     ```bash
     ./mvnw clean install
     ```

3. **Memcached**  
   Memcached is required for caching. Ensure it is installed and running on your machine.  

--- 

## Dependencies

The project relies on the following dependencies, which are included in the `pom.xml` file:

- **Spring Boot**: For building the web application and managing the GraphQL API.
  - `spring-boot-starter-web`
  - `spring-boot-starter-graphql`
  - `spring-boot-devtools` (for automatic restarts during development)
  
- **Memcached**: For caching and improving API performance.
  - `xmemcached` (XMemcached client for Memcached)

- **GraphQL**: To define and query data in a flexible way.
  - `spring-graphql-test` (for testing GraphQL queries)

- **Database**: An in-memory database for storing receipts.
  - `h2` (for runtime usage)

- **Lombok**: For reducing boilerplate code.
  - `lombok`

- **Validation**: For validating receipt input data.
  - `jakarta.validation-api`
  - `hibernate-validator`

- **Testing**: For testing critical components like the points calculator.
  - `spring-boot-starter-test`
  - `spring-graphql-test`

---

## Setup Instructions

### 1. Clone the repository

Clone the repository to your local machine.

```bash
git clone https://github.com/your-repository/ReceiptProcessor.git
cd ReceiptProcessor
```

### 2. Install dependencies

If Maven is not installed, follow the [official Maven installation guide](https://maven.apache.org/install.html).

To install the dependencies listed in the `pom.xml` file, run the following command:

```bash
mvn install
```

### 3. Start Memcached

The application uses Memcached for caching. Start Memcached using Homebrew:

```bash
brew services start memcached
```

To stop Memcached:

```bash
brew services stop memcached
```

### 4. Running the Application

To run the application locally, use the following command:

```bash
make run
```

This will start the Spring Boot application on `http://localhost:8080`.

Alternatively, you can use Maven to run the application directly:

```bash
./mvnw spring-boot:run
```

### 5. Running Tests

To run the tests and ensure the application is working correctly, use:

```bash
make test
```

Alternatively, run the tests with Maven:

```bash
./mvnw test
```

---

## Endpoints

The following GraphQL endpoints are exposed by the application:

### 1. **Add Receipt**

- **Method**: `POST`
- **URL**: `http://localhost:8080/graphql`
- **Description**: Adds a new receipt and calculates loyalty points based on the items and total amount.
- **Request Body**:

```graphql
mutation {
  addReceipt(input: { 
    store: "SuperMart", 
    date: "2024-12-02T14:33:00", 
    total: 150.25, 
    items: [
      { name: "Soy Milk", quantity: 2, price: 2.5 },
      { name: "Bread", quantity: 1, price: 3.0 },
      { name: "coffee", quantity: 2, price: 12.0 }
    ]
  }) {
    id
    store
    date
    total
    points
    items {
      name
      quantity
      price
    }
  }
}
```

### 2. **Get Receipt**

- **Method**: `POST`
- **URL**: `http://localhost:8080/graphql`
- **Description**: Retrieves a specific receipt by ID.
- **Request Body**:

```graphql
query {
  getReceipt(id: "bb51b612-c9c7-4711-b3da-d2d40ffe6af2") {
    id
    store
    date
    total
    points
    items {
      id
      name
      quantity
      price
    }
  }
}
```

### 3. **Get All Receipts**

- **Method**: `POST`
- **URL**: `http://localhost:8080/graphql`
- **Description**: Retrieves all receipts.
- **Request Body**:

```graphql
{
  listReceipts {
    id
    store
    date
    total
    points
    items {
      id
      name
      quantity
      price
    }
  }
}
```

### 4. **Get Points**

- **Method**: `POST`
- **URL**: `http://localhost:8080/graphql`
- **Description**: Retrieves the points for a specific receipt by ID.
- **Request Body**:

```graphql
query {
  getPoints(id: "bb51b612-c9c7-4711-b3da-d2d40ffe6af2") {
    id
    points
  }
}
```

---

## Core Service Overview

### PointsCalculator

The `PointsCalculator` class is responsible for calculating points based on a given receipt. It uses a list of rules to determine how many points to assign to a receipt. These rules are retrieved from the `RuleRegistry`, which holds all Rule beans.

```java
@Component
public class PointsCalculator {
  private final RuleRegistry ruleRegistry;

  public PointsCalculator(RuleRegistry ruleRegistry) {
    this.ruleRegistry = ruleRegistry;
  }

  public int calculatePoints(AddReceiptInput receipt) {
    return ruleRegistry.getRules()
      .stream()
      .mapToInt(rule -> rule.calculate(receipt))
      .sum();
  }
}
```

### RuleRegistry

The `RuleRegistry` is a Spring-managed component that holds a list of all the Rule implementations. Each rule is automatically injected by Spring during startup.

```java
@Component
public class RuleRegistry {
  private final List<Rule> rules;

  public RuleRegistry(List<Rule> rules) {
    this.rules = rules;
  }

  public List<Rule> getRules() {
    return rules;
  }
}
```

### Rule Interface

The `Rule` interface defines the contract for all rules that calculate points based on the provided receipt.

```java
interface Rule {
  int calculate(AddReceiptInput receipt);
}
```

---

### In-Memory Repository

The **ReceiptRepository** is responsible for storing and retrieving receipts during the application's lifecycle. This repository does not connect to a real database and instead uses an in-memory list to simulate database operations. The data will persist only during the running instance of the application. Once the application stops, all data is lost.

The repository is defined as a Spring component, so it can be easily injected into services and controllers for use.

```java
package ReceiptProcessor.infrastructure.repository;

import ReceiptProcessor.api.dto.ReceiptResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReceiptRepository {

  private final List<ReceiptResponse> store = new ArrayList<>();

  /**
   * Finds a receipt in the store by its unique ID.
   * 
   * @param id The ID of the receipt to find.
   * @return The matching ReceiptResponse if found; otherwise, null.
   */
  public ReceiptResponse findById(String id) {
    return store.stream()
        .filter(receipt -> receipt.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  /**
   * Retrieves all receipts stored in the store.
   * 
   * @return A list of all ReceiptResponse objects.
   */
  public List<ReceiptResponse> findAll() {
    return store;
  }

  /**
   * Saves a new receipt to the store.
   * 
   * @param receipt The ReceiptResponse object to save.
   * @return The saved ReceiptResponse object.
   */
  public ReceiptResponse save(ReceiptResponse receipt) {
    store.add(receipt);
    return receipt;
  }
}
```

### Methods

- **`findById(String id)`**: This method searches for a receipt by its unique ID. It returns the first matching receipt, or `null` if no match is found.
  
- **`findAll()`**: This method retrieves all receipts stored in the in-memory repository.
  
- **`save(ReceiptResponse receipt)`**: This method saves a new receipt to the in-memory store and returns the saved receipt.

### How it works

- The `store` is a simple `ArrayList` of `ReceiptResponse` objects. Each receipt is added to the list when saved.
- The repository uses Java streams to search for receipts by their ID (`findById`) and retrieve all stored receipts (`findAll`).
- Since this repository does not connect to a real database, the data is only available during the application's runtime.

---

## Testing

The critical logic, including the points calculator, is tested using JUnit. Tests ensure that points are correctly calculated based on the rules.

---

