package lesson1_2.Homework;
import java.sql.*;
import java.util.ArrayList;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "*******";

    public static void main(String[] args) {
        System.out.println(getAllProducts());
        System.out.println(getAllProductsByPrice());
        System.out.println(getAllProductsByDescription());
    }


    static ArrayList getAllProducts() {
        ArrayList list = new ArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT")) {
                while (resultSet.next()) {
                    list.add(add(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return list;
    }


    static ArrayList getAllProductsByPrice() {
        ArrayList list = new ArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE <= 100")) {
                while (resultSet.next()) {
                    list.add(add(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return list;
    }

    static ArrayList getAllProductsByDescription() {
        ArrayList list = new ArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT")) {
                while (resultSet.next()) {
                    Product product = add(resultSet);

                    if (product.getDescriprion().length() > 50 )
                        list.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return list;
    }



    private static Product add( ResultSet resultSet) throws SQLException {
        long id = resultSet.getInt(1);
        String name  = resultSet.getString(2);
        String description = resultSet.getString(3);
        Double price = resultSet.getDouble(4);
        Product product = new Product(id, name, description, price);
        return product;
    }


}
