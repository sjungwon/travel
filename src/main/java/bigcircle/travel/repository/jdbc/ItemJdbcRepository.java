package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.ItemImage;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.dto.ItemDto;
import bigcircle.travel.repository.dto.ItemSaveDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemJdbcRepository implements ItemRepository {

    private final NamedParameterJdbcTemplate template;

    public ItemJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(ItemSaveDto itemSaveDto) {
        String sql = "INSERT INTO ITEM (title, thumbnail, zonecode, address_detail, description, category_id, created_at, updated_at) " + "VALUES (:title, :thumbnail,:zonecode, :addressDetail, :description, :categoryId, :createdAt, :updatedAt)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(itemSaveDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        Long key = keyHolder.getKey().longValue();

        return key;
    }

    @Override
    public ItemDto findById(Long id) {
        String sql = "SELECT {I.id, I.title,  I.address_detail, I.thumbnail, I.description, I.created_at, I.updated_at, A.zonecode, A.address, C.id as category_id, C.kr as category_kr, C.en as category_en, M.store_file_name} FROM ITEM I " +
                "JOIN ADDRESS A ON I.zonecode =  A.zonecode " +
                "JOIN CATEGORY C ON I.category_id = C.id " +
                "LEFT OUTER JOIN ITEM_IMAGE M ON I.id = M.item_id " +
                "WHERE I.id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        List<ItemDao> joinedItems = template.query(sql,param,joinedItemRowMapper());

        ItemDto itemDto = joinedItemToItemDto(joinedItems);

        return itemDto;
    }

    @Override
    public List<ItemDto> findAll() {
        return null;
    }

    @Override
    public void update(Long id, ItemSaveDto itemSaveDto) {

    }

    @Override
    public void delete(Long id) {

    }


    private ItemDto joinedItemToItemDto(List<ItemDao> joinedItems){
        if(joinedItems.size() < 1){
            return null;
        }

        ItemDao mainData = joinedItems.get(0);
        Address address = new Address(mainData.getZonecode(), joinedItems.get(0).getAddress());
        Category category = Category.valueOf(joinedItems.get(0).getCategoryEn());

        List<ItemImage> itemImages = new ArrayList<>(joinedItems.size());
        for (ItemDao joinedItem : joinedItems) {
            if(joinedItem.getStoreFileName() != null){
                ItemImage itemImage = new ItemImage(mainData.getId(), joinedItem.getStoreFileName());
                itemImages.add(itemImage);
            }
        }

        return new ItemDto(mainData.getId(), mainData.title, mainData.thumbnail, category, address, mainData.addressDetail, mainData.description, LocalDateTime.parse(mainData.createdAt), LocalDateTime.parse(mainData.updatedAt),itemImages);
    }

    private RowMapper<ItemDao> joinedItemRowMapper(){
        return BeanPropertyRowMapper.newInstance(ItemDao.class);
    }

    @Getter @Setter
    private static class ItemDao {
        private Long id;
        private String title;
        private String thumbnail;
        private Long categoryId;
        private String categoryKr;
        private String categoryEn;
        private Integer zonecode;
        private String address;
        private String addressDetail;
        private String description;
        private String createdAt;
        private String updatedAt;
        private String storeFileName;

        public ItemDao() {
        }

        public ItemDao(Long id, String title, String thumbnail, Long categoryId, String categoryKr, String categoryEn, Integer zonecode, String address, String addressDetail, String description, String createdAt, String updatedAt, String storeFileName) {
            this.id = id;
            this.title = title;
            this.thumbnail = thumbnail;
            this.categoryId = categoryId;
            this.categoryKr = categoryKr;
            this.categoryEn = categoryEn;
            this.zonecode = zonecode;
            this.address = address;
            this.addressDetail = addressDetail;
            this.description = description;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.storeFileName = storeFileName;
        }
    }
}
