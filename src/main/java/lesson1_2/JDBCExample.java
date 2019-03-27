package lesson1_2;

import java.sql.*;

public class JDBCExample {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "********";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {

//             boolean res = statement.execute("INSERT INTO product VALUES (2, 'toy111', 'for children', 60 )");
//            System.out.println(res);

//            boolean res = statement.execute("DELETE FROM PRODUCT WHERE NAME = 'toy111'");
//            System.out.println(res);

//            int response = statement.executeUpdate("INSERT INTO product VALUES (5, 'car', 'for children', 770 )");
//            System.out.println(response);

            int response = statement.executeUpdate("DELETE FROM PRODUCT WHERE NAME = 'car'");
            System.out.println(response);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

}
