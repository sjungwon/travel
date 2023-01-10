package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.repository.FileRepository;
import bigcircle.travel.repository.jdbc.h2.FileSQLs;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

public class FileJdbcRepository implements FileRepository {

    private final NamedParameterJdbcTemplate template;
    private final FileSQLs sql = new FileSQLs();

    public FileJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public UploadFile findByStoreFileName(String storeFileName) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("storeFileName", storeFileName);

        UploadFileDao uploadFileDao = this.template.queryForObject(sql.FIND_BY_STORE_FILE_NAME, param, getFileDtoMapper());
        return new UploadFile(uploadFileDao.getUploadFileName(), uploadFileDao.getStoreFileName());
    }

    @Override
    public void saveFile(UploadFile uploadFile){
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(uploadFile);
        this.template.update(sql.SAVE, param);
    }

    @Override
    public void deleteFile(String storeFileName) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("storeFileName", storeFileName);
        this.template.update(sql.DELETE, param);
    }

    private RowMapper<UploadFileDao> getFileDtoMapper(){
        return BeanPropertyRowMapper.newInstance(UploadFileDao.class);
    }

    @Getter @Setter
    private static class UploadFileDao{
        private String uploadFileName;
        private String storeFileName;

        public UploadFileDao() {
        }

        public UploadFileDao(String uploadFileName, String storeFileName) {
            this.uploadFileName = uploadFileName;
            this.storeFileName = storeFileName;
        }
    }
}
