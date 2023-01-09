package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.User;
import bigcircle.travel.domain.Role;
import bigcircle.travel.lib.LongIdGenerator;
import bigcircle.travel.repository.dto.UserCreate;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.repository.dto.UserRoleUpdate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserMemoryRepository implements UserRepository {
    private final Map<Long, User> db;
    private final LongIdGenerator idGenerator;

    public UserMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
        this.idGenerator = new LongIdGenerator();
    }

    @Override
    public Long save(UserCreate userCreateDto){
        //중복 처리
        String account = userCreateDto.getAccount();
        String nickname = userCreateDto.getNickname();
        String email = userCreateDto.getEmail();

        this.db.forEach((k,u)->{
            if(u.getAccount().equals(account)){
                throw new DuplicateKeyException("exist key" + account + "PUBLIC.USERS(ACCOUNT NULLS FIRST)");
            }
            if(u.getNickname().equals(nickname)){
                throw new DataIntegrityViolationException("Duplicate entry "+ nickname + " for PUBLIC.USERS(NICKNAME NULLS FIRST)");
            }
            if(u.getEmail().equals(email)){
                throw new DataIntegrityViolationException("Duplicate entry "+ email + " for PUBLIC.USERS(EMAIL NULLS FIRST)");
            }
        });

        //권한 지정
        Role role = getRoleById(userCreateDto.getRoleId());

        Long id = idGenerator.newId();
        User user = new User(role, id, userCreateDto.getAccount(), userCreateDto.getHashedPassword(), userCreateDto.getNickname(), userCreateDto.getEmail(), LocalDateTime.parse(userCreateDto.getCreatedAt()), LocalDateTime.parse(userCreateDto.getUpdatedAt()));
        this.db.put(id, user);

        return id;
    }

    @Override
    public User findById(Long id) {
        User user = this.db.get(id);

        if(user == null){
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    @Override
    public User findByAccount(String account){
        return this.db.values().stream().filter((u) -> u.getAccount().equals(account)).findFirst().orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public User findByNickname(String nickname) {
        return this.db.values().stream().filter((u) -> u.getNickname().equals(nickname)).findFirst().orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Override
    public List<User> findAll(Boolean banned) {
        if(banned == null || !banned){
            return this.db.values().stream().filter(u -> u.getRole() != Role.BANNED).collect(Collectors.toList());
        }else{
            return this.db.values().stream().filter(u->u.getRole() == Role.BANNED).collect(Collectors.toList());
        }
    }

    @Override
    public void update(UserCreate userCreateDto) {
        String account = userCreateDto.getAccount();

        User user = findByAccount(account);

        Role role = Arrays.stream(Role.values()).filter(r -> r.getId().equals(userCreateDto.getRoleId())).findFirst().orElseThrow(() -> new IllegalStateException("권한 정보을 찾을 수 없습니다."));

        Long id = user.getId();
        User updatedUser = new User(role, id,userCreateDto.getAccount(), userCreateDto.getHashedPassword(), userCreateDto.getNickname(), userCreateDto.getEmail(),user.getCreatedAt(), LocalDateTime.parse(userCreateDto.getUpdatedAt()));
        this.db.remove(id);
        this.db.put(id,updatedUser);
    }

    @Override
    public void updatePassword(Long userId, String newHashedPassword) {
        User user = findById(userId);

        if(user == null){
            throw new EmptyResultDataAccessException(1);
        }

        Long id = user.getId();
        User updatedMember = new User(user.getRole(), id, user.getAccount(), newHashedPassword, user.getNickname(), user.getEmail(),user.getCreatedAt(), LocalDateTime.now());

        this.db.remove(id);
        this.db.put(id,updatedMember);

    }

    @Override
    public void updateNickname(Long userId, String newNickname) {
        User user = findById(userId);

        User updatedMember = new User(user.getRole(), userId,user.getAccount(), user.getHashedPassword(),newNickname, user.getEmail(),user.getCreatedAt(),LocalDateTime.now());

        this.db.remove(userId);
        this.db.put(userId,updatedMember);
    }

    @Override
    public void updateEmail(Long userId, String newEmail) {
        User user = findById(userId);

        User updatedUser = new User(user.getRole(), userId, user.getAccount(), user.getHashedPassword(), user.getNickname(), newEmail, user.getCreatedAt(),LocalDateTime.now());

        this.db.remove(userId);
        this.db.put(userId,updatedUser);
    }

    @Override
    public void updateRole(UserRoleUpdate userRoleUpdateDto) {
        User user = findById(userRoleUpdateDto.getUserId());

        User updatedUser = new User(userRoleUpdateDto.getNewRole(), userRoleUpdateDto.getUserId(), user.getAccount(), user.getHashedPassword(), user.getNickname(), user.getEmail(),user.getCreatedAt(),LocalDateTime.now());
        this.db.remove(userRoleUpdateDto.getUserId());
        this.db.put(userRoleUpdateDto.getUserId(), updatedUser);
    }


    private Role getRoleById(Long id){
        switch (id.intValue()){
            case 1:
                return Role.ALL;
            case 2:
                return Role.ADMIN;
            case 3:
                return Role.MANAGER;
            case 4:
                return Role.USER;
            default:
                return Role.BANNED;
        }
    }

    public void clear(){
        this.db.clear();
    }
}
