package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.repository.FileRepository;
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

    public FileJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public UploadFile findByStoreFileName(String storeFileName) {
        String sql = "SELECT * FROM UPLOAD_FILE WHERE store_file_name = :storeFileName";

        SqlParameterSource param = new MapSqlParameterSource().addValue("storeFileName", storeFileName);

        UploadFileDao uploadFileDao = this.template.queryForObject(sql, param, getFileDtoMapper());
        return new UploadFile(uploadFileDao.getUploadFileName(), uploadFileDao.getStoreFileName());
    }

    @Override
    public void saveFiles(List<UploadFile> uploadFiles) {
        //db에 저장
        for (UploadFile uploadFile : uploadFiles) {
            this.saveFile(uploadFile);
        }
    }

    @Override
    public void saveFile(UploadFile uploadFile){
        String sql = "INSERT INTO UPLOAD_FILE (store_file_name, upload_file_name) " + "VALUES (:storeFileName, :uploadFileName)";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(uploadFile);
        this.template.update(sql, param);
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
