package bigcircle.travel.service;

import bigcircle.travel.domain.User;
import bigcircle.travel.domain.Role;
import bigcircle.travel.lib.PasswordEncryptUtils;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.repository.dto.UserCreate;
import bigcircle.travel.service.dto.LoginDto;
import bigcircle.travel.service.dto.RegisterDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncryptUtils passwordEncryptUtils;

    public UserService(UserRepository memberRepository, PasswordEncryptUtils passwordEncryptHelper) {
        this.userRepository = memberRepository;
        this.passwordEncryptUtils = passwordEncryptHelper;
    }

    public User getUserById(Long id) {return this.userRepository.findById(id);}

    public Long save(RegisterDto registerDto){
        String hashedPassword = passwordEncryptUtils.encodePassword(registerDto.getRawPassword());
        String now = LocalDateTime.now().toString();
        UserCreate memberCreateDto = new UserCreate(registerDto.getAccount(), hashedPassword, registerDto.getNickname(), registerDto.getEmail(), Role.USER.getId(),now,now);
        return this.userRepository.save(memberCreateDto);
    }

    public User login(LoginDto loginDto){
        User byId = this.userRepository.findByAccount(loginDto.getAccount());

        //암호가 틀린 경우
        if(!passwordEncryptUtils.matchPassword(loginDto.getRawPassword(), byId.getHashedPassword())){
            throw new EmptyResultDataAccessException(1);
        }

        return byId;
    }

    public void updateNickname(Long requestUserId, String newNickname){
        this.userRepository.updateNickname(requestUserId, newNickname);
    }

    public void updateEmail(Long requestUserId, String newEmail){
        this.userRepository.updateEmail(requestUserId, newEmail);
    }

    public boolean confirmPrevPassword(Long requestUserId, String prevRawPassword){
        User byId = this.userRepository.findById(requestUserId);

        return passwordEncryptUtils.matchPassword(prevRawPassword, byId.getHashedPassword());
    }

    public void updatePassword(Long requestUserId, String newRawPassword){
        String hashedPassword = passwordEncryptUtils.encodePassword(newRawPassword);

        this.userRepository.updatePassword(requestUserId, hashedPassword);
    }

}
