package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.Address;
import bigcircle.travel.domain.Category;
import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.jdbc.h2.ItemSQLs;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ItemJdbcRepository implements ItemRepository {

    private final NamedParameterJdbcTemplate template;
    private final ItemSQLs sql = new ItemSQLs();
    public ItemJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(ItemSaveDto itemSaveDto) {


        SqlParameterSource param = new BeanPropertySqlParameterSource(itemSaveDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql.SAVE, param, keyHolder);

        Long key = keyHolder.getKey().longValue();

        return key;
    }

    @Override
    public Item findById(Long id) {

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        //id로 접근한 경우엔 해당 데이터가 없으면 오류가 맞음 -> EmptyResultDataAccessException 전파
        ItemDao itemDao = template.queryForObject(sql.FIND_BY_ID, param, itemDaoRowMapper());
        log.info("test = {}", itemDao);

        return itemDaoToItemConverter(itemDao);
    }

    @Override
    public List<Item> findAll() {

        try{
            List<ItemDao> query = template.query(sql.FIND_ALL, itemDaoRowMapper());
            List<Item> items = new ArrayList<>(query.size());

            for (ItemDao itemDao : query) {
                items.add(itemDaoToItemConverter(itemDao));
            }

            return items;
        }catch(EmptyResultDataAccessException ignored){
            return new ArrayList<>();
        }

    }

    @Override
    public void update(Long id, ItemSaveDto itemSaveDto) {

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", itemSaveDto.getTitle())
                .addValue("thumbnail", itemSaveDto.getThumbnail())
                .addValue("zonecode", itemSaveDto.getZonecode())
                .addValue("address_detail", itemSaveDto.getAddressDetail())
                .addValue("description", itemSaveDto.getDescription())
                .addValue("category_id", itemSaveDto.getCategoryId())
                .addValue("updated_at", itemSaveDto.getUpdatedAt())
                .addValue("id", id);

        template.update(sql.UPDATE, param);
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);

        template.update(sql.DELETE, param);
    }

    private Item itemDaoToItemConverter(ItemDao itemDao){
        if(itemDao == null){
            return null;
        }

        String storeFileNameList = itemDao.getStoreFileNameList();
        String[] split = storeFileNameList.substring(1, storeFileNameList.length() - 1).split(", ");


        Address address = new Address(itemDao.getZonecode(), itemDao.getAddress());
        Category category = Category.valueOf(itemDao.categoryEn);


        return new Item(itemDao.id, itemDao.title, itemDao.thumbnail != null ? itemDao.thumbnail : null , category, address, itemDao.addressDetail, itemDao.description, LocalDateTime.parse(itemDao.createdAt), LocalDateTime.parse(itemDao.updatedAt), (split.length > 0 && !split[0].equals("null")) ? List.of(split) : new ArrayList<>());
    }

    private RowMapper<ItemDao> itemDaoRowMapper(){
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
        private String storeFileNameList;

        public ItemDao() {
        }

        public ItemDao(Long id, String title, String thumbnail, Long categoryId, String categoryKr, String categoryEn, Integer zonecode, String address, String addressDetail, String description, String createdAt, String updatedAt, String storeFileNameList) {
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
            this.storeFileNameList = storeFileNameList;
        }

        @Override
        public String toString() {
            return "ItemDao{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", categoryId=" + categoryId +
                    ", categoryKr='" + categoryKr + '\'' +
                    ", categoryEn='" + categoryEn + '\'' +
                    ", zonecode=" + zonecode +
                    ", address='" + address + '\'' +
                    ", addressDetail='" + addressDetail + '\'' +
                    ", description='" + description + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", storeFileNameList='" + storeFileNameList + '\'' +
                    '}';
        }
    }
}
