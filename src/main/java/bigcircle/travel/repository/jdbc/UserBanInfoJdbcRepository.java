package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.UserBanInfo;
import bigcircle.travel.repository.UserBanInfoRepository;
import bigcircle.travel.repository.dto.UserBanInfoCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserBanInfoJdbcRepository implements UserBanInfoRepository {
    private final NamedParameterJdbcTemplate template;

    public UserBanInfoJdbcRepository (DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(UserBanInfoCreate userBanInfoDto) {
        String sql = "INSERT INTO USERS_BAN_INFO (user_id, description, created_at) " +
                "VALUES (:userId, :description, :created_at)";


        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userBanInfoDto.getUserId())
                .addValue("description", userBanInfoDto.getDescription())
                .addValue("created_at", LocalDateTime.now().toString());

        this.template.update(sql, param);
    }

    @Override
    public List<UserBanInfo> findAllByUserId(Long userId) {
        String sql = "SELECT {B.id, B.user_id, U.account as user_account,U.nickname as user_nickname ,B.description, B.created_at} " +
                "FROM USERS_BAN_INFO B " +
                "JOIN USERS U " +
                "ON U.id = B.user_id " +
                "WHERE user_id = :userId";

        SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);

        List<UserBanInfoDao> query = this.template.query(sql, param, userBanInfoDaoRowMapper());

        return query.stream().map(UserBanInfoDao::daoToUserBanInfo).collect(Collectors.toList());
    }

    private RowMapper<UserBanInfoDao> userBanInfoDaoRowMapper(){
        return BeanPropertyRowMapper.newInstance(UserBanInfoDao.class);
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    private static class UserBanInfoDao{
        private Long id;
        private Long userId;
        private String userAccount;
        private String userNickname;
        private String description;
        private String createdAt;

        public UserBanInfo daoToUserBanInfo(){
            return new UserBanInfo(this.id, this.userId, this.userAccount, this.userNickname, this.description, LocalDateTime.parse(this.createdAt));
        }
    }
}
