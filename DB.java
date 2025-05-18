
import java.sql.*;
import java.util.Scanner;

public class DB {
    public static Connection conn;

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_db", "root", "");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (id INT AUTO_INCREMENT PRIMARY KEY, date DATE, category VARCHAR(255), amount DOUBLE, note TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addExpenseInteractive(Scanner sc) {
        try {
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("Enter category: ");
            String category = sc.nextLine();
            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(sc.nextLine());
            System.out.print("Enter note: ");
            String note = sc.nextLine();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO expenses (date, category, amount, note) VALUES (?, ?, ?, ?)");
            ps.setDate(1, Date.valueOf(date));
            ps.setString(2, category);
            ps.setDouble(3, amount);
            ps.setString(4, note);
            ps.executeUpdate();
            System.out.println("Expense added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void viewAll() {
        try (Statement s = conn.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM expenses")) {
            System.out.printf("%-5s %-12s %-15s %-10s %s\n", "ID", "Date", "Category", "Amount", "Note");
            while (rs.next()) {
                System.out.printf("%-5d %-12s %-15s %-10.2f %s\n", rs.getInt("id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount"), rs.getString("note"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void filterByDate(String date) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM expenses WHERE date = ?")) {
            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f | %s\n", rs.getInt("id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount"), rs.getString("note"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void filterByCategory(String cat) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM expenses WHERE category = ?")) {
            ps.setString(1, cat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f | %s\n", rs.getInt("id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount"), rs.getString("note"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteExpense(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM expenses WHERE id = ?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Deleted." : "ID not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateExpense(int id, Scanner sc) {
        try {
            System.out.print("New date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("New category: ");
            String category = sc.nextLine();
            System.out.print("New amount: ");
            double amount = Double.parseDouble(sc.nextLine());
            System.out.print("New note: ");
            String note = sc.nextLine();

            PreparedStatement ps = conn.prepareStatement("UPDATE expenses SET date=?, category=?, amount=?, note=? WHERE id=?");
            ps.setDate(1, Date.valueOf(date));
            ps.setString(2, category);
            ps.setDouble(3, amount);
            ps.setString(4, note);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Updated." : "ID not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showMonthlyTotal() {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT DATE_FORMAT(date, '%Y-%m') AS month, SUM(amount) as total FROM expenses GROUP BY month")) {
            while (rs.next()) {
                System.out.printf("%s : %.2f\n", rs.getString("month"), rs.getDouble("total"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showCategorySummary() {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT category, SUM(amount) as total FROM expenses GROUP BY category")) {
            while (rs.next()) {
                System.out.printf("%s : %.2f\n", rs.getString("category"), rs.getDouble("total"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void close() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
