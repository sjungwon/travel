package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.Item;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.dto.ItemSaveDto;
import bigcircle.travel.repository.jdbc.h2.ItemSQLs;
import bigcircle.travel.repository.jdbc.mapper.ItemResultSetExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

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
    public Optional<Item> findById(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        List<Item> query = template.query(sql.FIND_BY_ID, param, new ItemResultSetExtractor());

        if(query != null && query.size() > 0){
            return Optional.of(query.get(0));
        }

        return Optional.empty();
    }

    @Override
    public List<Item> findAll() {
        return template.query(sql.FIND_ALL, new ItemResultSetExtractor());
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
}
