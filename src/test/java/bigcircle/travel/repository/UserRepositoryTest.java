package bigcircle.travel.repository;

import bigcircle.travel.domain.User;
import bigcircle.travel.domain.Role;
import bigcircle.travel.repository.dto.UserCreate;
import bigcircle.travel.repository.memory.UserMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    void afterEach(){
        if(this.userRepository instanceof UserMemoryRepository){
            ((UserMemoryRepository)this.userRepository).clear();
        }
    }

    @Test
    @DisplayName("동일 Id 저장 시 예외 발생")
    void saveDupId() {
        //given
        String now = LocalDateTime.now().toString();

        UserCreate userCreateDto = new UserCreate("testAdmin", "qwer1234", "닉네임","test@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);

        //when
        UserCreate userCreateDto1 = new UserCreate("testAdmin", "qwer12", "닉네임2","test2@test.com",Role.ADMIN.getId(),now,now);

        //then
        assertThatThrownBy(()->this.userRepository.save(userCreateDto1)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(()->this.userRepository.save(userCreateDto1)).hasMessageContaining("PUBLIC.USERS(ACCOUNT");
    }

    @Test
    @DisplayName("중복된 닉네임 예외 발생")
    void saveDupNickname(){
        //given
        String now = LocalDateTime.now().toString();

        UserCreate userCreateDto = new UserCreate("testAdmin", "qwer1234", "닉네임","test@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);

        //when
        UserCreate userCreateDto1 = new UserCreate("testAdmin2", "qwer1234", "닉네임","test2@test.com",Role.ADMIN.getId(),now,now);

        //then
        assertThatThrownBy(()->this.userRepository.save(userCreateDto1)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(()->this.userRepository.save(userCreateDto1)).hasMessageContaining("PUBLIC.USERS(NICKNAME");
    }

    @Test
    @DisplayName("중복된 이메일 예외 발생")
    void saveDupEmail(){
        //given
        String now = LocalDateTime.now().toString();

        UserCreate userCreateDto = new UserCreate("testAdmin", "qwer1234", "닉네임","test@test.com", Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);

        //when
        UserCreate userCreateDto1 = new UserCreate("testAdmin2", "qwer1234", "닉네임2","test@test.com",Role.ADMIN.getId(),now,now);

        //then
        assertThatThrownBy(()->this.userRepository.save(userCreateDto1)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(()->this.userRepository.save(userCreateDto1)).hasMessageContaining("PUBLIC.USERS(EMAIL");
    }

    @Test
    @DisplayName("저장 후 id로 회원 조회")
    void findById() {
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate("testAdmin", "qwer1234", "nickname1","test@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);

        //when
        User byId = this.userRepository.findByAccount(userCreateDto.getAccount());

        //then
        assertThat(byId.getAccount()).isEqualTo(userCreateDto.getAccount());
        assertThat(byId.getHashedPassword()).isEqualTo("qwer1234");
        assertThat(byId.getNickname()).isEqualTo(userCreateDto.getNickname());
        assertThat(byId.getRole().getId()).isEqualTo(userCreateDto.getRoleId());
        assertThat(byId.getCreatedAt().toString()).isEqualTo(now);
        assertThat(byId.getUpdatedAt().toString()).isEqualTo(now);
    }

    @Test
    @DisplayName("저장 후 닉네임으로 회원 조회")
    void findByNickname(){
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate( "testAdmin", "qwer1234", "nickname","test@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);

        //when
        User byNickname = this.userRepository.findByNickname(userCreateDto.getNickname());

        //then
        assertThat(byNickname).isNotNull();
        assertThat(byNickname.getAccount()).isEqualTo(userCreateDto.getAccount());
        assertThat(byNickname.getHashedPassword()).isEqualTo("qwer1234");
        assertThat(byNickname.getNickname()).isEqualTo(userCreateDto.getNickname());
        assertThat(byNickname.getRole().getId()).isEqualTo(userCreateDto.getRoleId());
        assertThat(byNickname.getCreatedAt().toString()).isEqualTo(now);
        assertThat(byNickname.getUpdatedAt().toString()).isEqualTo(now);
    }

    @Test
    void findAll() {
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate( "testAdmin", "qwer1234", "nickname","test@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);
        UserCreate userCreateDto1 = new UserCreate( "testAdmin2", "qwer1234", "nickname2","test2@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto1);

        //when
        List<User> all = this.userRepository.findAll(false);

        //then
        assertThat(all.size()).isEqualTo(2);
        Map<String, User> collect = all.stream().collect(Collectors.toMap(a -> a.getAccount(), a -> a));

        User member = collect.get(userCreateDto.getAccount());
        assertThat(member.getAccount()).isEqualTo(userCreateDto.getAccount());
        assertThat(member.getRole().getId()).isEqualTo(userCreateDto.getRoleId());
        assertThat(member.getHashedPassword()).isEqualTo(userCreateDto.getHashedPassword());
        assertThat(member.getNickname()).isEqualTo(userCreateDto.getNickname());
        assertThat(member.getEmail()).isEqualTo(userCreateDto.getEmail());
        assertThat(member.getCreatedAt().toString()).isEqualTo(now);
        assertThat(member.getUpdatedAt().toString()).isEqualTo(now);
        User member2 = collect.get(userCreateDto1.getAccount());
        assertThat(member2.getAccount()).isEqualTo(userCreateDto1.getAccount());
        assertThat(member2.getRole().getId()).isEqualTo(userCreateDto1.getRoleId());
        assertThat(member2.getHashedPassword()).isEqualTo(userCreateDto1.getHashedPassword());
        assertThat(member2.getNickname()).isEqualTo(userCreateDto1.getNickname());
        assertThat(member2.getEmail()).isEqualTo(userCreateDto1.getEmail());
        assertThat(member2.getCreatedAt().toString()).isEqualTo(now);
        assertThat(member2.getUpdatedAt().toString()).isEqualTo(now);
    }

    @Test
    void update() {
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate( "testAdmin", "qwer1234", "nickname","test@test.com",Role.ADMIN.getId(),now,now);
        this.userRepository.save(userCreateDto);

        //when
        String now2 = LocalDateTime.now().toString();
        UserCreate update = new UserCreate(userCreateDto.getAccount(), "qwer5678", "nickname2","test2@test.com",Role.USER.getId(),now2,now2);
        this.userRepository.update(update);

        //then
        User byId = this.userRepository.findByAccount(userCreateDto.getAccount());
        assertThat(byId.getHashedPassword()).isEqualTo(update.getHashedPassword());
        assertThat(byId.getNickname()).isEqualTo(update.getNickname());
        assertThat(byId.getEmail()).isEqualTo(update.getEmail());
        assertThat(byId.getRole().getId()).isEqualTo(update.getRoleId());
        assertThat(byId.getCreatedAt().toString()).isEqualTo(now);
        assertThat(byId.getUpdatedAt().toString()).isEqualTo(now2);
    }

    @Test
    void updatePassword(){
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate( "testAdmin", "qwer1234", "nickname","test@test.com",Role.ADMIN.getId(),now,now);
        Long id = this.userRepository.save(userCreateDto);

        //when
        String newPassword = "qwer4444";
        this.userRepository.updatePassword(id, newPassword);

        //then
        User byId = this.userRepository.findByAccount(userCreateDto.getAccount());
        assertThat(byId.getHashedPassword()).isEqualTo(newPassword);
        assertThat(byId.getCreatedAt().toString()).isEqualTo(now);
        assertThat(byId.getUpdatedAt().toString()).isNotEqualTo(now);
    }

    @Test
    void updateNickname(){
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate( "testAdmin", "qwer1234", "nickname","test@test.com",Role.ADMIN.getId(),now,now);
        Long id = this.userRepository.save(userCreateDto);

        //when
        String newNickname = "nickname23";
        this.userRepository.updateNickname(id, newNickname);

        //then
        User byId = this.userRepository.findByAccount(userCreateDto.getAccount());
        assertThat(byId.getNickname()).isEqualTo(newNickname);
        assertThat(byId.getCreatedAt().toString()).isEqualTo(now);
        assertThat(byId.getUpdatedAt().toString()).isNotEqualTo(now);
    }

    @Test
    void updateEmail(){
        //given
        String now = LocalDateTime.now().toString();
        UserCreate userCreateDto = new UserCreate( "testAdmin", "qwer1234", "nickname","test@test.com",Role.ADMIN.getId(),now,now);
        Long id = this.userRepository.save(userCreateDto);

        //when
        String newEmail = "test123@test.com";
        this.userRepository.updateEmail(id, newEmail);

        //then
        User byId = this.userRepository.findByAccount(userCreateDto.getAccount());
        assertThat(byId.getEmail()).isEqualTo(newEmail);
        assertThat(byId.getCreatedAt().toString()).isEqualTo(now);
        assertThat(byId.getUpdatedAt().toString()).isNotEqualTo(now);
    }
}
