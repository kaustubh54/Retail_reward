# 🏆 Retail Rewards API (Spring Boot)

## 📌 Overview

This project implements a **Rewards Program API** for a retailer. Customers earn reward points based on their transactions over a **dynamic 3-month period**.

---

## 🎯 Reward Rules

* 2 points for every dollar spent **above $100**
* 1 point for every dollar spent **between $50 and $100**
* No points for transactions **below $50**

### 💡 Example

* Transaction of **$120**
  → (20 × 2) + (50 × 1) = **90 points**

---

## ⚙️ Tech Stack

* Java 8+
* Spring Boot
* Spring Data JPA
* H2 In-Memory Database
* Maven
* JUnit 5 & Mockito

---

## 📂 Project Structure

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
```

---

## 🚀 Features

* ✅ Calculate **monthly reward points per customer**
* ✅ Calculate **total reward points**
* ✅ Dynamic **last 3 months calculation** (no hardcoding)
* ✅ SQL-based data initialization (`data.sql`)
* ✅ RESTful API design
* ✅ Exception handling using `@ControllerAdvice`
* ✅ Unit & Integration testing
* ✅ Clean layered architecture

---

## 🔗 API Endpoint

### Get Rewards by Customer ID

```
GET /api/rewards/{customerId}
```

### Example

```
http://localhost:8080/api/rewards/101
```

### Sample Response

```json
{
  "customerId": 101,
  "monthlyRewards": [
    {
      "month": "Jan",
      "points": 115
    },
    {
      "month": "Feb",
      "points": 150
    }
  ],
  "totalPoints": 265
}
```

> ⚠️ Note: Data is preloaded using SQL scripts. No POST API is used.

---

## 🗄️ Database (H2)

### Access H2 Console

```
http://localhost:8080/h2-console
```

### JDBC URL

```
jdbc:h2:mem:testdb
```

---

## 🧪 Testing

### Unit Tests

* Service layer tested using **Mockito**

### Integration Tests

* Controller layer tested using **MockMvc**

### Coverage Includes

* ✔ Positive scenarios
* ✔ Negative scenarios (no data, invalid input)
* ✔ Edge cases (amount = 50, 100, <50)

---

## ⚠️ Exception Handling

* Custom Exception: `ResourceNotFoundException`
* Global handler using `@ControllerAdvice`

---

## 📊 Data Initialization

Data is loaded using `data.sql` at application startup.

✔ Multiple customers
✔ Multiple months
✔ Covers edge cases

---

## ⭐ Key Highlights

* ✔ No hardcoded months (dynamic logic)
* ✔ Java Streams for aggregation
* ✔ Clean and maintainable code
* ✔ Production-ready structure
* ✔ Follows Java coding standards

---

## 🔮 Future Enhancements

* Pagination & filtering (date range)
* Authentication (Spring Security + JWT)
* Caching (Redis)

---

## ▶️ How to Run

1. Clone the repository
2. Run the Spring Boot application
3. Access API:

   ```
   http://localhost:8080/api/rewards/{customerId}
   ```

---

## 🙌 Author

**Kaustubh Nagvekar**

---
