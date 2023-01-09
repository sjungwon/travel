package bigcircle.travel.repository.jdbc;

import bigcircle.travel.domain.User;
import bigcircle.travel.domain.Role;
import bigcircle.travel.repository.dto.UserCreate;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.repository.dto.UserRoleUpdate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public class UserJdbcRepository implements UserRepository {

    private final NamedParameterJdbcTemplate template;
    public UserJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(UserCreate userCreateDto) {
        String sql = "INSERT INTO USERS (account, hashed_password, nickname, email, role_id, created_at, updated_at) VALUES (:account, :hashedPassword, :nickname, :email, :roleId, :createdAt, :updatedAt)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource param = new BeanPropertySqlParameterSource(userCreateDto);

        this.template.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT {M.id, M.account, M.hashed_password, M.nickname, M.email, R.type as role_type, M.created_at, M.updated_at} " +
                "FROM USERS M " +
                "JOIN ROLE R ON M.role_id = R.id  " +
                "WHERE M.id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        UserDao userDao = this.template.queryForObject(sql, param, userDaoRowMapper());

        if(userDao == null){
            throw new EmptyResultDataAccessException(1);
        }

        return userDao.userDaoToUser();
    }

    @Override
    public User findByAccount(String account) {
        String sql = "SELECT {M.id, M.account, M.hashed_password, M.nickname, M.email, R.type as role_type, M.created_at, M.updated_at} " +
                "FROM USERS M " +
                "JOIN ROLE R ON M.role_id = R.id " +
                "WHERE M.account = :account " +
                "AND M.role_id != 5";;


        SqlParameterSource param = new MapSqlParameterSource().addValue("account", account);

        UserDao userDao = this.template.queryForObject(sql, param, userDaoRowMapper());

        if(userDao == null){
            throw new EmptyResultDataAccessException(1);
        }

        return userDao.userDaoToUser();
    }

    @Override
    public User findByNickname(String nickname) {
        String sql = "SELECT {M.id, M.account, M.hashed_password, M.nickname, M.email, R.type as role_type, M.created_at, M.updated_at} " +
                "FROM USERS M " +
                "JOIN ROLE R ON M.role_id = R.id " +
                "WHERE M.nickname = :nickname " +
                "AND M.role_id != 5";


        SqlParameterSource param = new MapSqlParameterSource().addValue("nickname", nickname);

        UserDao userDao = this.template.queryForObject(sql, param, userDaoRowMapper());

        if(userDao == null){
            throw new EmptyResultDataAccessException(1);
        }

        return userDao.userDaoToUser();
    }

    @Override
    public List<User> findAll(Boolean banned) {
        String sql = "SELECT {M.id, M.account, M.hashed_password, M.nickname, M.email, R.type as role_type, M.created_at, M.updated_at} " +
                "FROM USERS M " +
                "JOIN ROLE R ON M.role_id = R.id ";

        if(banned == null || !banned){
            sql += "WHERE M.role_id != 5";
        }else{
            sql += "WHERE M.role_id = 5";
        }

        List<UserDao> query = this.template.query(sql, userDaoRowMapper());

        List<User> users = new ArrayList<>(query.size());
        for (UserDao userDao : query) {
            users.add(userDao.userDaoToUser());
        }

        return users;
    }

    @Override
    public void update(UserCreate userCreateDto) {
        String sql = "UPDATE USERS SET " +
                "hashed_password = :hashedPassword, " +
                "nickname = :nickname, " +
                "email = :email, " +
                "role_id = :roleId, " +
                "updated_at = :updatedAt " +
                "WHERE account = :account";

        SqlParameterSource param = new BeanPropertySqlParameterSource(userCreateDto);

        this.template.update(sql, param);
    }

    @Override
    public void updatePassword(Long userId, String newHashedPassword) {
        String sql = "UPDATE USERS SET " +
                "hashed_password = :hashedPassword, " +
                "updated_at = :updatedAt " +
                "WHERE id = :userId";

        SqlParameterSource param = new MapSqlParameterSource().addValue("hashedPassword", newHashedPassword)
                .addValue("updatedAt", LocalDateTime.now().toString())
                .addValue("userId",userId);

        this.template.update(sql,param);
    }

    @Override
    public void updateNickname(Long userId, String newNickname) {
        String sql = "UPDATE USERS SET " +
                "nickname = :nickname, " +
                "updated_at = :updatedAt " +
                "WHERE id = :id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("nickname",newNickname)
                .addValue("updatedAt",LocalDateTime.now().toString())
                .addValue("id", userId);

        this.template.update(sql, param);
    }

    @Override
    public void updateEmail(Long userId, String newEmail) {
        String sql = "UPDATE USERS SET " +
                "email = :email, " +
                "updated_at = :updatedAt " +
                "WHERE id = :userId";

        SqlParameterSource param = new MapSqlParameterSource().addValue("email", newEmail)
                .addValue("updatedAt",LocalDateTime.now().toString())
                .addValue("userId",userId);

        this.template.update(sql, param);
    }

    @Override
    public void updateRole(UserRoleUpdate userRoleUpdateDto){
        String sql = "UPDATE USERS SET " +
                "role_id = :roleId " +
                "WHERE id = :userId";

        SqlParameterSource param = new MapSqlParameterSource().addValue("roleId", userRoleUpdateDto.getNewRole().getId()).addValue("userId",userRoleUpdateDto.getUserId());

        this.template.update(sql,param);
    }

    private RowMapper<UserDao> userDaoRowMapper(){
        return BeanPropertyRowMapper.newInstance(UserDao.class);
    }

    @Getter @Setter @ToString
    private static class UserDao {
        private Long id;
        private String account;
        private String hashedPassword;
        private String nickname;
        private String email;
        private String roleType;
        private String createdAt;
        private String updatedAt;

        public UserDao() {
        }

        public UserDao(Long id, String account, String hashedPassword, String nickname, String email, String roleType, String createdAt, String updatedAt) {
            this.account = account;
            this.hashedPassword = hashedPassword;
            this.nickname = nickname;
            this.email = email;
            this.roleType = roleType;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public User userDaoToUser(){
            return new User(Role.valueOf(this.roleType), this.id, this.account, this.hashedPassword, this.nickname, this.email, LocalDateTime.parse(this.createdAt), LocalDateTime.parse(this.updatedAt));
        }
    }
}
