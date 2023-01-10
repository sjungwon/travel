package bigcircle.travel.repository.jdbc.h2;

public class ItemSQLs {
    public final String SAVE = "INSERT INTO ITEM (title, thumbnail, zonecode, address_detail, description, category_id, created_at, updated_at) "
                 + "VALUES (:title, :thumbnail,:zonecode, :addressDetail, :description, :categoryId, :createdAt, :updatedAt)";

    public final String FIND_BY_ID = "SELECT {I.id, I.title,  I.address_detail, I.thumbnail, I.description, I.created_at, I.updated_at, A.zonecode, A.address, C.id as category_id, C.kr as category_kr, C.en as category_en, ARRAY_AGG(M.store_file_name ORDER BY M.id) as store_file_name_list} " +
                       "FROM ITEM I " +
                       "LEFT OUTER JOIN ADDRESS A ON I.zonecode =  A.zonecode " +
                       "JOIN CATEGORY C ON I.category_id = C.id " +
                       "LEFT OUTER JOIN ITEM_IMAGE M ON I.id = M.item_id " +
                       "WHERE I.id = :id AND I.deleted != 1 " +
                       "GROUP BY I.id";

    public final String FIND_ALL = "SELECT {I.id, I.title,  I.address_detail, I.thumbnail, I.description, I.created_at, I.updated_at, A.zonecode, A.address, C.id as category_id, C.kr as category_kr, C.en as category_en, ARRAY_AGG(M.store_file_name ORDER BY M.id) as store_file_name_list} FROM ITEM I " +
                     "JOIN ADDRESS A ON I.zonecode =  A.zonecode " +
                     "JOIN CATEGORY C ON I.category_id = C.id " +
                     "LEFT OUTER JOIN ITEM_IMAGE M ON I.id = M.item_id " +
                     "WHERE I.deleted != 1 " +
                     "GROUP BY I.id";

    public final String UPDATE = "UPDATE ITEM SET "+
                   "title = :title, "+
                   "thumbnail = :thumbnail, "+
                   "zonecode = :zonecode, "+
                   "address_detail = :address_detail, "+
                   "description = :description, "+
                   "category_id = :category_id, "+
                   "updated_at = :updated_at " +
                   "WHERE id = :id";

    public final String DELETE = "UPDATE ITEM SET " +
            "deleted = 1 " +
            "WHERE id = :id";

}
