package bigcircle.travel.repository.jdbc.mapper;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.Item;
import bigcircle.travel.domain.ItemImage;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class ItemResultSetExtractor implements ResultSetExtractor<List<Item>> {
    @Override
    public List<Item> extractData(ResultSet rs) throws DataAccessException, SQLException {
        Map<Long, Item> itemMap = new HashMap<>();

        while(rs.next()){
            Long id = rs.getLong("id");
                if(id != 0){
                    Item item = itemMap.get(id);
                    if(item == null){
                        //아직 없는 item인 경우 생성
                        String title = rs.getString("title");
                        String thumbnail = rs.getString("thumbnail");
                        Category category = Category.valueOf(rs.getString("category_en"));
                        Address address = new Address(rs.getInt("zonecode"), rs.getString("address"));
                        String addressDetail = rs.getString("address_detail");
                        String description = rs.getString("description");
                        LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));
                        LocalDateTime updatedAt = LocalDateTime.parse(rs.getString("updated_at"));
                        String storeFileName = rs.getString("store_file_name");
                        List<ItemImage> imageList = new LinkedList<>();
                        if(storeFileName != null){
                            imageList.add(new ItemImage(id, storeFileName));
                        }
                        Item newItem = new Item(id, title, thumbnail, category, address, addressDetail, description, imageList, createdAt, updatedAt);
                        itemMap.put(id, newItem);
                    }else{
                        //item이 있는 경우 -> image만 저장하면 됨
                        String storeFileName = rs.getString("store_file_name");
                        item.getItemImages().add(new ItemImage(id, storeFileName));
                    }
                }
        }

        return new ArrayList<>(itemMap.values());
    }
}
