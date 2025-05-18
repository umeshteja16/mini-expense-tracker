# 💸 Mini Expense Tracker (Java + MySQL)

A simple command-line application to manage your daily expenses, built using Java and MySQL. It supports adding, updating, deleting, viewing, exporting, and analyzing expenses directly from the terminal.

---

## 📦 Features

- ✅ Add new expenses (date, category, amount, note)
- ✅ View all expenses in a clean table
- ✅ Filter by date or category
- ✅ Delete or update any expense by ID
- ✅ View monthly totals (grouped by month)
- ✅ Show category-wise total summary
- ✅ Export all expenses to a CSV file (`expenses_export.csv`)

---

## 🛠 Tech Stack

- **Java** (JDK 8 or above)
- **MySQL** for database
- **JDBC** for Java-MySQL connection

---

## 📁 Folder Structure

```
MiniExpenseTracker_Improved/
├── ExpenseTracker.java       # Main CLI logic
├── DB.java                   # All database operations
├── CSVExporter.java          # CSV export utility
├── lib/
│   └── mysql-connector-j-9.3.0.jar  # JDBC driver (add manually)
```

---

## 🧰 Setup Instructions

### ✅ Step 1: MySQL Setup

1. Open MySQL Workbench or terminal.
2. Run the following:
   ```sql
   CREATE DATABASE expense_db;
   ```
3. Update DB credentials in `DB.java`:
   ```java
   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_db", "root", "your_password");
   ```

---

### ✅ Step 2: Compile and Run (Command Line)

```bash
# Navigate to the project folder
cd MiniExpenseTracker_Improved

# Compile
javac -cp ".;lib/mysql-connector-j-9.3.0.jar" *.java

# Run
java -cp ".;lib/mysql-connector-j-9.3.0.jar" ExpenseTracker
```

> 🔁 On macOS/Linux, replace `;` with `:` in the classpath

---

## 🧪 Sample Usage

```
--- Mini Expense Tracker ---
1. Add Expense
2. View All Expenses
3. Filter by Date
4. Filter by Category
5. Monthly Total
6. Delete Expense
7. Update Expense
8. Export to CSV
9. Category Summary
10. Exit
```

---

## 📤 Exported CSV Format

When option `8` is selected, a file `expenses_export.csv` will be created in the current directory with all records in this format:

```
ID,Date,Category,Amount,Note
1,2025-05-18,Food,120.00,Lunch with friends
...
```

---

## 📌 Author

Developed by **Umesh Teja**.  
This project demonstrates practical use of JDBC with MySQL, object-oriented programming in Java, and real-world data handling.
