# 📄 Bank APIs
🔑 1. Login API
Purpose: Allows ASY Bank customers to log in with their customerId and password.

Details:

Validates credentials from the database.

Returns a simple success/failure response.

(No JWT or Spring Security — plain login for demo purposes)

---

💰 2. Calculate & Credit Interest API
Purpose: Automatically calculates and credits daily interest to all customer accounts.

How it Works:

Formula: <br>
Daily Interest = Account Balance × 3% ÷ 365  <br>
Loops through all accounts, calculates daily interest, updates account balance, and records the transaction in the CreditInterest table.

(For demo, the process is triggered manually or on a shorter schedule instead of quarterly.)

Response:

✅ SUCCESS: Interest credited successfully.

❌ FAILURE: No accounts found.

---

✅ Tech Stack
Java • Spring Boot • REST API • MySQL • JPA
