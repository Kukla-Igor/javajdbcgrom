package lesson3.Homework.Task2;


import java.sql.*;
import java.util.Date;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "********";


    //135 658
    public  Long testSavePerformance (){
        long res = 0;

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)")) {

            Date start = new Date();
            for (int i = 0; i < 1000; i++) {
                preparedStatement.setLong(1, i);
                preparedStatement.setString(2, "Some string " + i);
                preparedStatement.setInt(3, 1000 + i);
                preparedStatement.executeUpdate();
            }
            Date finish = new Date();

            res = finish.getTime() - start.getTime();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return res;
    }

    //139 132
    public  Long testDeleteByIdPerformance (){
        long res = 0;

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("DELETE from TEST_SPEED where id = ?")) {

            res = cycle(preparedStatement);

          } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return res;
    }

    //204
    public  Long testDeletePerformance (){
        long res = 0;

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("DELETE from TEST_SPEED")) {

            res = getRes(preparedStatement);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return res;
    }

    //134561
    public  Long testSelectByIdPerformance (){
        long res = 0;

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * from TEST_SPEED where id = ?")) {

          res = cycle(preparedStatement);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return res;
    }

    //232
    public  Long testSelectPerformance (){
        long res = 0;

        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement("SELECT * from TEST_SPEED")) {

            res = getRes(preparedStatement);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return res;
    }

    private Long getRes(PreparedStatement preparedStatement) throws SQLException {
        Date start = new Date();
        preparedStatement.executeUpdate();

        Date finish = new Date();

        return finish.getTime() - start.getTime();
    }


    private Long cycle (PreparedStatement preparedStatement) throws SQLException{
        Date start = new Date();
        for (int i = 0; i < 1000; i++) {
            preparedStatement.setLong(1, i);
            preparedStatement.executeUpdate();
        }
        Date finish = new Date();

        return finish.getTime() - start.getTime();
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
