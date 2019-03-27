package lesson4.homework.DAO;

import lesson4.homework.IdEntity;
import lesson4.homework.Storage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class StorageDAO extends GeneralDAO {


    public Storage save (Storage storage){

        return (Storage) generalSave(storage);
    }

    public void delete (long id){
        generalDelete(id);
    }

    public Storage update (Storage storage){

        return (Storage) generalUpdate(storage);
    }

    public Storage findById (Long id) {
        return (Storage) generalFindById(id);
        }


    @Override
    int prepare(PreparedStatement preparedStatement, IdEntity idEntity) throws SQLException {
        Storage storage = (Storage) idEntity;
        preparedStatement.setLong(1, storage.getId());
        String string = Arrays.deepToString(storage.getFormatsSupported()).replace("[", "");
        preparedStatement.setNString(2,string.replace("]", ""));
        preparedStatement.setString(3, storage.getStorageCountry());
        preparedStatement.setLong(4, storage.getStorageMaxSize());

        int res = preparedStatement.executeUpdate();

        return res;
    }

    @Override
    String getSQLInsertRequest() {
        return "INSERT INTO storage VALUES (?, ?, ?, ?)";
    }

    @Override
    String getSQLDeleteRequest() {
        return "DELETE FROM storage WHERE ID = ?";
    }

    @Override
    String getSQLUpdateRequest() {
        return "UPDATE storage  set id = ?, FORMATSSUPPORTED = ?, STORAGECOUNTRY = ?, STORAGEMAXSIZE = ? WHERE ID =";
    }

    @Override
    String getSQLSearchRequest() {
        return "SELECT * FROM storage WHERE ID = ?";
    }

    @Override
    IdEntity getObject(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String[] arr = resultSet.getString(2).split(", ");
            Storage storage = new Storage(resultSet.getLong(1), arr, resultSet.getString(3), resultSet.getLong(4));
            return storage;
        }
        return null;
    }
}
