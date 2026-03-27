# Rewards API - Spring Boot Application

##  Overview

This project implements a **Rewards Program API** for a retailer. Customers earn reward points based on their transaction amounts.

###  Reward Rules

* 2 points for every dollar spent **above $100**
* 1 point for every dollar spent **between $50 and $100**
* No points for transactions **below $50**

#### Example:

* Transaction of $120 → (20 × 2) + (50 × 1) = **90 points**

-------------------------------------------------------------------------------------------------------------------------------------------
##  Tech Stack

* Java 8
* Spring Boot
* Spring Data JPA
* H2 In-Memory Database
* Maven
* JUnit 5
* Mockito

---------------------------------------------------------------------------------------------------------------------------------------------

##  Project Structure

```
com.retail.rewards
│
├── controller        # REST Controllers
├── service           # Business logic
├── service.impl      # Service implementations
├── repository        # JPA Repositories
├── entity            # Database entities
├── dto               # Data Transfer Objects
├── exception         # Custom exceptions & handlers
├── util              # Utility classes

----------------------------------------------------------------------------------------------------------------------------------------------

##  Features

* Calculate **monthly reward points per customer**
* Calculate **total reward points**
* RESTful API design
* Exception handling using `@ControllerAdvice`
* Unit & Integration testing
* Clean layered architecture (Controller → Service → Repository)

--------------------------------------------------------------------------------------------------------------------------------------------------

##  API Endpoints

###  Get Rewards by Customer ID

```
GET /api/rewards/{customerId}
```

#### Example:

```
GET http://localhost:8080/api/rewards/101
```

#### Sample Response:

```json
{
  "customerId": 101,
  "monthlyRewards": [
    {
      "month": "Jan",
      "points": 115
    }
  ],
  "totalPoints": 115
}
```
POST http://localhost:8080/api/rewards/transactions
###Sample Request
{
  "customerId": 104,
  "amount": 150,
  "transactionDate": "2026-03-27"
}

--------------------------------------------------------------------------------------------------------------------------------------
##  Database (H2)

### Access H2 Console:

```
http://localhost:8080/h2-console
```

### JDBC URL:

```
jdbc:h2:mem:testdb
```
---------------------------------------------------------------------------------------------------------------------------------------

##  Testing

### Unit Tests
Service layer
Used Mockito for unit testing.

### Integration Tests

* Controller layer using MockMvc

### Test Coverage Includes:

* Positive scenarios
* Negative scenarios (no data, invalid inputs)
* Edge cases (amount = 50, 100, <50)

--------------------------------------------------------------------------------------------------------------------------------------------

##  Exception Handling

* Custom exception: `ResourceNotFoundException`
* Global handler using `@ControllerAdvice`

----------------------------------------------------------------------------------------------------------------------------------------------

##  Key Highlights

* No hardcoded months (dynamic calculation)
* Java 8 Streams for data processing
* Clean and maintainable code
* Proper package structure
* Production-ready coding standards

-------------------------------------------------------------------------------------------------------------------------------------------

## For Future Enhancements

* Pagination & filtering (date range)
* Authentication (Spring Security + JWT)
* Caching (Redis)

-----------------------------------------------------------------------------------------------------------------------------------------------
Test Data
-- Customer 101 (3 months)
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 120, '2026-01-10');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 75,  '2026-01-15');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 40,  '2026-01-20');

INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 200, '2026-02-05');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 95,  '2026-02-12');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 60,  '2026-02-25');

INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 110, '2026-03-03');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (101, 45,  '2026-03-10');

-- Customer 102
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (102, 130, '2026-01-08');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (102, 85,  '2026-01-18');

INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (102, 220, '2026-02-02');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (102, 55,  '2026-02-14');

INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (102, 75,  '2026-03-01');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (102, 150, '2026-03-20');

-- Customer 103
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (103, 49,  '2026-01-05');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (103, 51,  '2026-01-25');

INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (103, 101, '2026-02-11');
INSERT INTO transactions (customer_id, amount, transaction_date) VALUES (103, 180, '2026-02-21');




