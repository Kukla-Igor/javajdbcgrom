package lesson1_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "********";

    public static void main(String[] args) {
        //saveProduct();
        //deleteProducts();
        deleteProductsByPrice();
    }

    static void saveProduct() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            boolean res = statement.execute("INSERT INTO product VALUES (9999, 'toy55', 'for children', 600 )");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    static void deleteProducts() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            boolean res = statement.execute("DELETE FROM product WHERE NAME != 'toy'");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    static void deleteProductsByPrice() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            boolean res = statement.execute("DELETE FROM product WHERE price < 100");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
