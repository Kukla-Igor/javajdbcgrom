package lesson1_2;

import java.sql.*;

public class JDBCFirstStep {

private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";

private static final String USER = "main";
private static final String PASS = "********";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Class" + JDBC_DRIVER + " not found");
                return;
            }


            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM DEAL where AMOUNT > 500")){

                while (resultSet.next()) {
                    long id = resultSet.getInt(1);
                    long customerId = resultSet.getInt(2);
                    int amount = resultSet.getInt(3);
                    Date dateDeal = resultSet.getDate(4);
                    Order order = new Order(id, customerId, amount, dateDeal);

                    System.out.println(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
