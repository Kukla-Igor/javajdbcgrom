package lesson4.homework;

import lesson4.homework.DAO.FileDAO;
import lesson4.homework.exception.BadRequestException;
import lesson4.homework.exception.InternalServerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final String DB_URL = "jdbc:oracle:thin:@gromecode-lessons.cbgpstxxjmkw.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "*******";


    public File put(Storage storage, File file) throws Exception {
        nullCheck(storage, file);
        formatCheck(storage, file);
        idCheckToPut(storage, file);

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            sizeCheck(file.getSize(), storage, statement);

            int res = statement.executeUpdate("UPDATE files SET STORAGE_ID = " + storage.getId() + " where id = " + file.getId());
            if (res == 1) {
                file.setStorage(storage);
                return file;
            } else
                throw new InternalServerException("Something wrong");
        }

    }

    public void putAll(Storage storage, List<File> files) throws Exception {
        long sizeAllFiles = 0;
        for (File file : files) {
            nullCheck(storage, file);
            formatCheck(storage, file);
            idCheckToPut(storage, file);
            sizeAllFiles += file.getSize();
        }

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            sizeCheck(sizeAllFiles, storage, statement);
            connection.setAutoCommit(false);

            for (int i = 0; i < files.size(); i++) {
                int res = statement.executeUpdate("UPDATE files SET STORAGE_ID = " + storage.getId() + " where id = " + files.get(i).getId());
                if (res == 1) {
                    files.get(i).setStorage(storage);
                } else
                    throw new InternalServerException("Something wrong");
            }
            connection.commit();
        }
    }

    public File delete(Storage storage, File file) throws Exception {
        nullCheck(storage, file);
        idCheckToDelete(storage, file);

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE files SET STORAGE_ID = null  where id = ?")) {
            ps.setLong(1, file.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                file.setStorage(null);
                return file;
            } else
                throw new InternalServerException("Something wrong");
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception{
        nullCheck(storageFrom, storageTo);

        ArrayList<File> files = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * from files where storage_id = ?")) {
            connection.setAutoCommit(false);
            ps.setLong(1,storageFrom.getId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                File file = new File(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4), storageFrom);
                files.add(file);
            }

            putAll(storageTo, files);

            connection.commit();
        }
    }

    public File transferFile (Storage storageFrom, Storage storageTo, long id) throws Exception{
        nullCheck(storageFrom, storageTo);
        FileDAO fileDAO = new FileDAO();
        File file = fileDAO.findById(id);
        idCheckToDelete(storageFrom, file);

        return put(storageTo, file);
    }


    private void nullCheck(IdEntity object1, IdEntity object2) throws BadRequestException {
        if (object1 == null)
            throw new BadRequestException("Your`s object is null");
        if (object2 == null)
            throw new BadRequestException("Your`s object1 is null");
    }

    private void idCheckToPut(Storage storage, File file) throws BadRequestException {
        if (file.getStorage() != null && file.getStorage().getId() == storage.getId())
            throw new BadRequestException(file.getId() + " file is already in " + storage.getId() + " storage");
    }

    private void idCheckToDelete(Storage storage, File file) throws BadRequestException {
        if (file.getStorage() == null || file.getStorage().getId() != storage.getId())
            throw new BadRequestException(file.getId() + " file is not in " + storage.getId() + " storage");
    }


    private void formatCheck(Storage storage, File file) throws BadRequestException {
        for (String format : storage.getFormatsSupported()) {
            if (format.equals(file.getFormat()))
                return;
        }
        throw new BadRequestException(file.getId() + " file format is not supported by " + storage.getId() + " storage");

    }

    private void sizeCheck(long sizeAllFiles, Storage storage, Statement statement) throws InternalServerException {

        if (sizeAllFiles > getFreeSize(storage, statement))
            throw new InternalServerException("There is not enough space in " + storage.getId() + " storage for file");
    }

    private long getFreeSize(Storage storage, Statement statement) {
        long size = 0;
        try (ResultSet resultSet = statement.executeQuery("SELECT sizing FROM FILES WHERE STORAGE_ID = " + storage.getId())) {
            while (resultSet.next()) {
                size += resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storage.getStorageMaxSize() - size;

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}




