package lesson3;

import lesson1_2.Homework.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "********";

    public Product save (Product product){

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO product VALUES (?, ?, ?, ?)")) {

            int res = prepare(preparedStatement, product);
            System.out.println("save was finished with result " + res);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return product;
    }


    public Product update (Product product){
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("UPDATE product  set id = ?, NAME = ?, DESCRIPTION = ?, PRICE = ? WHERE ID =" + product.getId())) {

           int res = prepare(preparedStatement, product);
            System.out.println("update was finished with result " + res);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return product;
    }


    public List<Product> getProduct (){
        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");

            List<Product> products = new ArrayList<>();
           while (resultSet.next()){
            Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4));
            products.add(product);
           }

           return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public void delete (long id){
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM product  WHERE ID =" + id)) {

            int res = preparedStatement.executeUpdate();
            System.out.println("delete was finished with result " + res);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private int prepare(PreparedStatement preparedStatement, Product product) throws SQLException{
        preparedStatement.setLong(1, product.getId());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setString(3, product.getDescriprion());
        preparedStatement.setDouble(4, product.getPrice());
        int res = preparedStatement.executeUpdate();
        return res;
    }
}
