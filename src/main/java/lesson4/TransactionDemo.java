package lesson4;

import lesson1_2.Homework.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDemo {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "******";



    public void save(List<Product> products) {

        try (Connection conn = getConnection()) {

            saveList(products, conn);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void saveList(List<Product> products, Connection conn) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO product VALUES (?, ?, ?, ?)")) {

            conn.setAutoCommit(false);

            for (Product product : products) {


                preparedStatement.setLong(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getDescriprion());
                preparedStatement.setDouble(4, product.getPrice());
                int res = preparedStatement.executeUpdate();

                System.out.println("save was finished with result " + res);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;

        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
