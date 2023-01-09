package bigcircle.travel.service;

import bigcircle.travel.domain.Role;
import bigcircle.travel.domain.User;
import bigcircle.travel.lib.PasswordEncryptUtils;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.repository.memory.UserMemoryRepository;
import bigcircle.travel.service.dto.LoginDto;
import bigcircle.travel.service.dto.RegisterDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncryptUtils passwordEncryptUtils;

    @AfterEach
    void afterEach(){
        if(this.userRepository instanceof UserMemoryRepository){
            ((UserMemoryRepository)this.userRepository).clear();
        }
    }


    @Test
    @DisplayName("동일 계정 저장 시 예외 발생")
    void save() {
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        this.userService.save(registerDto);

        //when
        RegisterDto registerDto2 = new RegisterDto("testUser", "qwer1234", "nickname2","test2@test.com");

        //then
        assertThatThrownBy(()->this.userService.save(registerDto2)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(()->this.userService.save(registerDto2)).hasMessageContaining("PUBLIC.USERS(ACCOUNT NULLS FIRST)");
    }

    @Test
    @DisplayName("동일 닉네임 저장 시 예외 발생")
    void saveDupNickname(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        this.userService.save(registerDto);

        //when
        RegisterDto registerDto2 = new RegisterDto("testUser2", "qwer1234", "nickname","test2@test.com");

        //then
        assertThatThrownBy(()->this.userService.save(registerDto2)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(()->this.userService.save(registerDto2)).hasMessageContaining("PUBLIC.USERS(NICKNAME NULLS FIRST)");
    }

    @Test
    @DisplayName("동일 이메일 저장 시 에외 발생")
    void saveDupEmail(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        this.userService.save(registerDto);

        //when
        RegisterDto registerDto2 = new RegisterDto("testUser2", "qwer1234", "nickname2","test@test.com");

        //then
        assertThatThrownBy(()->this.userService.save(registerDto2)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(()->this.userService.save(registerDto2)).hasMessageContaining("PUBLIC.USERS(EMAIL NULLS FIRST)");
    }


    @Test
    @DisplayName("정상 로그인")
    void login() {
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        this.userService.save(registerDto);

        //when
        LoginDto loginDto = new LoginDto("testUser", "qwer1234");
        User login = this.userService.login(loginDto);
        log.info("login Member = {}", login);

        //then
        assertThat(login).isNotNull();
    }

    @Test
    @DisplayName("없는 회원 로그인 예외 발생")
    void login1() {
        //given
        LoginDto loginDto = new LoginDto("testAdmin", "qwer1234");

        //then
        assertThatThrownBy(()-> this.userService.login(loginDto)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    //회원이 없는 경우와 정보 오류로 인해 로그인 실패시 자세한 정보 없이 실패에 대한 정보만 반환할 것이기 때문에 동일한 예외로 그냥 처리했음

    @Test
    @DisplayName("회원 정보 오류로 로그인 실패 - 예외 발생")
    void login2() {
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        this.userService.save(registerDto);

        //when
        LoginDto loginDto = new LoginDto("testAdmin", "qwer12");

        //then
        assertThatThrownBy(()->this.userService.login(loginDto)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getUserById(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        Long id = this.userService.save(registerDto);

        //when
        User userById = this.userService.getUserById(id);

        //then
        assertThat(userById.getAccount()).isEqualTo(registerDto.getAccount());
        assertThat(passwordEncryptUtils.matchPassword(registerDto.getRawPassword(),userById.getHashedPassword())).isTrue();
        assertThat(userById.getNickname()).isEqualTo(registerDto.getNickname());
        assertThat(userById.getEmail()).isEqualTo(registerDto.getEmail());
        assertThat(userById.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void updateNickname(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        Long id = this.userService.save(registerDto);
        User prev = this.userService.getUserById(id);

        //when
        this.userService.updateNickname(id, "newNickname");
        User update = this.userService.getUserById(id);

        //then
        assertThat(prev.getNickname()).isEqualTo("nickname");
        assertThat(update.getNickname()).isEqualTo("newNickname");
    }

    @Test
    void updateEmail(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        Long id = this.userService.save(registerDto);
        User prev = this.userService.getUserById(id);

        //when
        this.userService.updateEmail(id, "new@test.com");
        User update = this.userService.getUserById(id);

        //then
        assertThat(prev.getEmail()).isEqualTo("test@test.com");
        assertThat(update.getEmail()).isEqualTo("new@test.com");
    }

    @Test
    void confirmPrevPassword(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        Long id = this.userService.save(registerDto);

        //when
        boolean confirm = this.userService.confirmPrevPassword(id, "qwer1234");

        //then
        assertThat(confirm).isTrue();
    }

    @Test
    void updatePassword(){
        //given
        RegisterDto registerDto = new RegisterDto("testUser", "qwer1234", "nickname","test@test.com");
        Long id = this.userService.save(registerDto);
        User prev = this.userService.getUserById(id);

        //when
        this.userService.updatePassword(id, "q1w2e3r4");
        User update = this.userService.getUserById(id);

        //then
        assertThat(passwordEncryptUtils.matchPassword(registerDto.getRawPassword(), prev.getHashedPassword())).isTrue();
        assertThat(passwordEncryptUtils.matchPassword("q1w2e3r4", update.getHashedPassword())).isTrue();
    }
}