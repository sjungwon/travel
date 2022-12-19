package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.Address;
import bigcircle.travel.repository.AddressRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Slf4j
@Repository
public class AddressJdbcRepository implements AddressRepository {

    private NamedParameterJdbcTemplate template;

    public AddressJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Address address) {
        String sql = "INSERT INTO ADDRESS (zonecode, address) " + "VALUES (:zonecode, :address)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(address);

        try{
            template.update(sql, param);
        }catch(DuplicateKeyException e){
            //이미 저장된 경우 넘어감
            log.info("Address Duplicate 무시, {}", address.toString());
        }
    }

    @Override
    public Address findByZonecode(Integer zonecode) {
        String sql = "SELECT * FROM ADDRESS WHERE zonecode = :zonecode";

        SqlParameterSource param = new MapSqlParameterSource().addValue("zonecode", zonecode);

        try{
            AddressDao addressDto = template.queryForObject(sql, param, getAddressDtoMapper());
            return new Address(addressDto.getZonecode(), addressDto.getAddress());
        }catch(EmptyResultDataAccessException e){
            //데이터가 없는 경우
            return null;
        }

    }

    private RowMapper<AddressDao> getAddressDtoMapper(){
        return BeanPropertyRowMapper.newInstance(AddressDao.class);
    }

    @Getter @Setter
    private static class AddressDao {
        private Integer zonecode;
        private String address;

        public AddressDao() {
        }

        public AddressDao(Integer zonecode, String address) {
            this.zonecode = zonecode;
            this.address = address;
        }
    }
}
