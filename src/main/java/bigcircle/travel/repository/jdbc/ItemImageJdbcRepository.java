package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.repository.ItemImageRepository;
import bigcircle.travel.repository.jdbc.h2.ItemImageSQLs;
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
    private final ItemImageSQLs sql = new ItemImageSQLs();

    public ItemImageJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public void saveItemImage(ItemImage itemImage){

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(itemImage);

        this.template.update(sql.SAVE, param);
    }

    @Override
    public List<ItemImage> findByItemId(Long itemId) {

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", itemId);

        List<ItemImageDao> query = this.template.query(sql.FIND_BY_ITEM_ID, param, itemImageDaoRowMapper());

        List<ItemImage> itemImages = new ArrayList<>(query.size());
        for (ItemImageDao itemImageDao : query) {
            itemImages.add(itemImageDaoToItemImage(itemImageDao));
        }

        return itemImages;
    }

    @Override
    public void deleteByStoreFileName(String storeFileName) {

        SqlParameterSource param = new MapSqlParameterSource().addValue("storeFileName",storeFileName);

        this.template.update(sql.DELETE,param);
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
