package bigcircle.travel.repository.jdbc.h2;

public class ItemImageSQLs {
    public final String SAVE = "INSERT INTO ITEM_IMAGE(store_file_name, item_id) VALUES (:storeFileName, :itemId)";
    public final String FIND_BY_ITEM_ID = "SELECT * FROM ITEM_IMAGE WHERE item_id = :id";
    public final String DELETE = "DELETE FROM ITEM_IMAGE WHERE store_file_name = :storeFileName";
}
