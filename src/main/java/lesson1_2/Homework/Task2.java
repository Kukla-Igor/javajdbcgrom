package lesson1_2.Homework;

import java.sql.*;

public class Task2 {

    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "*******";

    public static void main(String[] args) {
        //increasePrice();
        changeDescription();
    }

    private static void increasePrice(){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            statement.executeUpdate("UPDATE  PRODUCT SET PRICE = PRICE + 100 WHERE  PRICE < 970");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void changeDescription(){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String desc = resultSet.getString(3);
                    if (desc.length() > 100) {
                        Statement newStatement = conn.createStatement() ;
                        desc = desc.substring(0, desc.lastIndexOf('.', desc.length() - 2) + 1);
                        newStatement.execute("UPDATE  PRODUCT SET DESCRIPTION = '" + desc + "' WHERE  ID = " + id);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
