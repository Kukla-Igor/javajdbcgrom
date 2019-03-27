package lesson4.homework.DAO;

import lesson4.homework.IdEntity;

import java.sql.*;

public abstract class GeneralDAO<T extends IdEntity> {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "********";

    abstract int prepare(PreparedStatement preparedStatement, T t) throws SQLException;
    abstract String getSQLInsertRequest();
    abstract String getSQLDeleteRequest();
    abstract String getSQLUpdateRequest();
    abstract String getSQLSearchRequest();
    abstract T getObject(ResultSet resultSet) throws SQLException;


    public T generalSave (T t){
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(getSQLInsertRequest())) {

            int res = prepare(preparedStatement, t);
            System.out.println("save was finished with result " + res);
            return t;

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    return null;
    }

    public void generalDelete (long id){
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(getSQLDeleteRequest())) {

            preparedStatement.setLong(1, id);

            int res = preparedStatement.executeUpdate();
            System.out.println("delete was finished with result " + res);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public T generalUpdate (T t){
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(getSQLUpdateRequest() +  + t.getId())) {


            int res = prepare(preparedStatement, t);

            System.out.println("update was finished with result " + res);
            if (res != 0)
                return t;

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null ;
    }

    public T generalFindById (long id) {
        try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(getSQLSearchRequest())) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                T t = getObject(resultSet);
                return t;
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
