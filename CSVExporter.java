
import java.io.FileWriter;
import java.sql.*;

public class CSVExporter {
    public static void exportToCSV() {
        try (Statement stmt = DB.conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM expenses");
             FileWriter fw = new FileWriter("expenses_export.csv")) {

            fw.write("ID,Date,Category,Amount,Note\n");
            while (rs.next()) {
                fw.write(String.format("%d,%s,%s,%.2f,%s\n",
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("note").replace(",", " ")));
            }

            System.out.println("Exported to expenses_export.csv");
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }
}
