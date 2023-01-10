package bigcircle.travel.repository.mapper;

import bigcircle.travel.domain.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        String category = rs.getString("category");
        return Category.valueOf(category);
    }
}
