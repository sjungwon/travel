package bigcircle.travel.repository;

import bigcircle.travel.domain.User;
import bigcircle.travel.repository.dto.UserCreate;
import bigcircle.travel.repository.dto.UserRoleUpdate;

import java.util.List;

public interface UserRepository {
    public Long save(UserCreate memberCreateDto);

    public User findById(Long id);

    public User findByAccount(String account);

    public User findByNickname(String nickname);

    public List<User> findAll(Boolean banned);

    public void update(UserCreate memberCreateDto);

    public void updatePassword(Long userId, String newHashedPassword);

    public void updateNickname(Long userId, String newNickname);

    public void updateEmail(Long userId, String newEmail);

    public void updateRole(UserRoleUpdate userRoleUpdateDto);
}
