package lesson3.Homework.Task1;

import lesson1_2.Homework.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "*******";

    public List<Product> getProductsByPrice(int price, int delta) {
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM product where PRICE between ? AND ?")) {
            ps.setInt(1, price - delta);
            ps.setInt(2, price + delta);

            return getList(ps.executeQuery());
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getProductsByName(String word) throws Exception {
        if (!isWord(word))
            throw new Exception("incorrect word " + word);

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM product where NAME LIKE ?")) {
            String specWord = "%" + word + "%";

            ps.setString(1, specWord);
            return getList(ps.executeQuery());



        } catch (
                SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }



    public List<Product> getProductsByDescription(){

        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product where DESCRIPTION IS NULL");


            return getList(resultSet);
        } catch (
                SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private List<Product> getList(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4));
            products.add(product);
        }
        return products;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private boolean isWord(String word) {
        if (word.length() < 3 || word.contains(" "))
            return false;

        byte[] bytes = word.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (!Character.isLetter(bytes[i])) {
                return false;
            }
        }
        return true;
    }
}
