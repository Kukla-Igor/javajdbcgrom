package lesson4.homework.DAO;

import lesson4.homework.File;
import lesson4.homework.IdEntity;
import lesson4.homework.Storage;
import java.sql.*;

public class FileDAO extends GeneralDAO {


    public File save (File file){

        return (File) generalSave(file);
    }

    public void delete (long id){
        generalDelete(id);
    }

    public File update (File file){

        return (File) generalUpdate(file);
    }

    public File findById (Long id) {
        return (File) generalFindById(id);
        }


    @Override
    int prepare(PreparedStatement preparedStatement, IdEntity idEntity) throws SQLException {
        File file = (File) idEntity;
        preparedStatement.setLong(1, file.getId());
        preparedStatement.setString(2, file.getName());
        preparedStatement.setString(3, file.getFormat());
        preparedStatement.setLong(4, file.getSize());
        if (file.getStorage() != null)
            preparedStatement.setLong(5, file.getStorage().getId());
        else
            preparedStatement.setString(5, null);
        int res = preparedStatement.executeUpdate();

        return res;
    }

    @Override
    String getSQLInsertRequest() {
        return "INSERT INTO files VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    String getSQLDeleteRequest() {
        return "DELETE FROM files WHERE ID = ?";
    }

    @Override
    String getSQLUpdateRequest() {
        return "UPDATE files  set id = ?, NAME = ?, FORMAT = ?, SIZING = ?, STORAGE_ID = ? WHERE ID =";
    }

    @Override
    String getSQLSearchRequest() {
        return "SELECT * FROM files WHERE ID = ?";
    }

    @Override
    IdEntity getObject(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            StorageDAO storageDAO = new StorageDAO();
            Storage storage = storageDAO.findById(resultSet.getLong(5));
            File file = new File(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4), storage);
            return file;
        }
        return null;
    }
}
