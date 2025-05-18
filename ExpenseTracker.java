
import java.util.Scanner;

public class ExpenseTracker {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DB.connect();
        DB.createTable();

        while (true) {
            System.out.println("\n--- Mini Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Filter by Date");
            System.out.println("4. Filter by Category");
            System.out.println("5. Monthly Total");
            System.out.println("6. Delete Expense");
            System.out.println("7. Update Expense");
            System.out.println("8. Export to CSV");
            System.out.println("9. Category Summary");
            System.out.println("10. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            switch (choice) {
                case 1 -> DB.addExpenseInteractive(scanner);
                case 2 -> DB.viewAll();
                case 3 -> {
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    DB.filterByDate(scanner.nextLine());
                }
                case 4 -> {
                    System.out.print("Enter category: ");
                    DB.filterByCategory(scanner.nextLine());
                }
                case 5 -> DB.showMonthlyTotal();
                case 6 -> {
                    System.out.print("Enter ID to delete: ");
                    DB.deleteExpense(Integer.parseInt(scanner.nextLine()));
                }
                case 7 -> {
                    System.out.print("Enter ID to update: ");
                    DB.updateExpense(Integer.parseInt(scanner.nextLine()), scanner);
                }
                case 8 -> CSVExporter.exportToCSV();
                case 9 -> DB.showCategorySummary();
                case 10 -> {
                    DB.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
