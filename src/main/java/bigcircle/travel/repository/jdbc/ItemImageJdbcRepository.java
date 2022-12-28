package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.repository.ItemImageRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemImageJdbcRepository implements ItemImageRepository {
    private final NamedParameterJdbcTemplate template;

    public ItemImageJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void saveItemImages(List<ItemImage> itemImages){
        for (ItemImage itemImage : itemImages) {
            this.saveItemImage(itemImage);
        }
    }
    @Override
    public void saveItemImage(ItemImage itemImage){
        String sql = "INSERT INTO ITEM_IMAGE(store_file_name, item_id) VALUES (:storeFileName, :itemId)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(itemImage);

        this.template.update(sql, param);
    }

    @Override
    public List<ItemImage> findByItemId(Long itemId) {
        String sql = "SELECT * FROM ITEM_IMAGE WHERE item_id = :id";

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", itemId);

        List<ItemImageDao> query = this.template.query(sql, param, itemImageDaoRowMapper());

        List<ItemImage> itemImages = new ArrayList<>(query.size());
        for (ItemImageDao itemImageDao : query) {
            itemImages.add(itemImageDaoToItemImage(itemImageDao));
        }

        return itemImages;
    }

    @Override
    public void deleteByStoreFileName(String storeFileName) {
        String sql = "DELETE FROM ITEM_IMAGE WHERE store_file_name = :storeFileName";

        SqlParameterSource param = new MapSqlParameterSource().addValue("storeFileName",storeFileName);

        this.template.update(sql,param);
    }

    private ItemImage itemImageDaoToItemImage(ItemImageDao itemImageDao){
        return new ItemImage(itemImageDao.itemId, itemImageDao.storeFileName);
    }

    private RowMapper<ItemImageDao> itemImageDaoRowMapper(){
        return BeanPropertyRowMapper.newInstance(ItemImageDao.class);
    }

    @Getter @Setter
    private static class ItemImageDao{
        private Long itemId;
        private String storeFileName;

        public ItemImageDao() {
        }

        public ItemImageDao(Long itemId, String storeFileName) {
            this.itemId = itemId;
            this.storeFileName = storeFileName;
        }
    }

}
