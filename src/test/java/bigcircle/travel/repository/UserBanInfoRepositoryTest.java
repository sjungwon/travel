package bigcircle.travel.repository;

import bigcircle.travel.domain.Role;
import bigcircle.travel.domain.UserBanInfo;
import bigcircle.travel.lib.PasswordEncryptUtils;
import bigcircle.travel.repository.dto.UserBanInfoCreate;
import bigcircle.travel.repository.dto.UserCreate;
import bigcircle.travel.repository.memory.UserBanInfoMemoryRepository;
import bigcircle.travel.repository.memory.UserMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserBanInfoRepositoryTest {

    @Autowired
    UserBanInfoRepository userBanInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncryptUtils passwordEncryptUtils;

    @AfterEach
    void afterEach(){
        if(userBanInfoRepository instanceof UserBanInfoMemoryRepository){
            ((UserBanInfoMemoryRepository) userBanInfoRepository).clear();
        }
        if(userRepository instanceof UserMemoryRepository){
            ((UserMemoryRepository) userRepository).clear();
        }
    }

    @Test
    void saveAndFind() {
        //given
        String password = "qwer1234";
        String hashedPassword = passwordEncryptUtils.encodePassword(password);
        UserCreate userCreate = new UserCreate("test", hashedPassword, "nickname", "test@test.com", Role.USER.getId(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
        Long id = userRepository.save(userCreate);
        UserBanInfoCreate userBanInfoCreate = new UserBanInfoCreate(id, userCreate.getAccount(), userCreate.getNickname(), "서버 해킹 시도함");

        //when
        userBanInfoRepository.save(userBanInfoCreate);
        List<UserBanInfo> allByUserId = userBanInfoRepository.findAllByUserId(id);

        assertThat(allByUserId.size()).isEqualTo(1);
        UserBanInfo userBanInfo = allByUserId.get(0);

        assertThat(userBanInfo.getUserId()).isEqualTo(userBanInfoCreate.getUserId());
        assertThat(userBanInfo.getUserAccount()).isEqualTo(userBanInfoCreate.getUserAccount());
        assertThat(userBanInfo.getUserNickname()).isEqualTo(userBanInfoCreate.getUserNickname());
        assertThat(userBanInfo.getDescription()).isEqualTo(userBanInfoCreate.getDescription());
    }
}