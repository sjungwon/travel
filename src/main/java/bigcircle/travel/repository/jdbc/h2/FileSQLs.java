package bigcircle.travel.repository.jdbc.h2;

public class FileSQLs {
    public final String FIND_BY_STORE_FILE_NAME = "SELECT * FROM UPLOAD_FILE WHERE store_file_name = :storeFileName";
    public final String SAVE = "INSERT INTO UPLOAD_FILE (store_file_name, upload_file_name) " + "VALUES (:storeFileName, :uploadFileName)";
    public final String DELETE = "DELETE FROM UPLOAD_FILE WHERE store_file_name = :storeFileName";
}
