import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExpenseChart extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/expense_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Monthly Expense Chart");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Total Amount");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Expenses per Month");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Expenses");

        Map<String, Double> data = fetchMonthlyTotals();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);

        VBox vbox = new VBox(barChart);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Map<String, Double> fetchMonthlyTotals() {
        Map<String, Double> totals = new LinkedHashMap<>();

        String sql = "SELECT DATE_FORMAT(date, '%Y-%m') AS month, SUM(amount) AS total FROM expenses GROUP BY month ORDER BY month";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                totals.put(rs.getString("month"), rs.getDouble("total"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching monthly totals: " + e.getMessage());
        }

        return totals;
    }

    public static void main(String[] args) {
        launch(args);
    }
}