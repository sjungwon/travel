package bigcircle.travel.service;

import bigcircle.travel.domain.Role;
import bigcircle.travel.domain.User;
import bigcircle.travel.domain.UserBanInfo;
import bigcircle.travel.repository.UserBanInfoRepository;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.repository.dto.UserBanInfoCreate;
import bigcircle.travel.repository.dto.UserNicknameUpdate;
import bigcircle.travel.repository.dto.UserRoleUpdate;
import bigcircle.travel.web.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserBanInfoRepository userBanInfoRepository;

    public AdminService(UserRepository userRepository, UserBanInfoRepository userBanInfoRepository) {
        this.userRepository = userRepository;
        this.userBanInfoRepository = userBanInfoRepository;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll(false);
    }

    public List<User> getBannedUsers() {return this.userRepository.findAll(true);}

    public List<UserBanInfo> getBanInfoByUserId(Long id) {return this.userBanInfoRepository.findAllByUserId(id);}

    @Transactional
    public void banUser(Long requestUserId, UserBanInfoCreate userBanInfoDto){
        User requestUser = this.userRepository.findById(requestUserId);
        User banUser = this.userRepository.findById(userBanInfoDto.getUserId());

        hierarchyAuthCheck(requestUser, banUser);

        this.userBanInfoRepository.save(userBanInfoDto);
        this.userRepository.updateRole(new UserRoleUpdate(userBanInfoDto.getUserId(), Role.BANNED));
    }

    public void unBan(Long requestUserId, Long userId){
        User requestUser = this.userRepository.findById(requestUserId);
        User unBanUser = this.userRepository.findById(userId);

        hierarchyAuthCheck(requestUser, unBanUser);

        this.userRepository.updateRole(new UserRoleUpdate(userId, Role.USER));
    }

    public void updateRole(Long requestUserId, UserRoleUpdate userRoleUpdateDto){
        User requestUser = this.userRepository.findById(requestUserId);
        User resourceUser = this.userRepository.findById(userRoleUpdateDto.getUserId());

        hierarchyAuthCheck(requestUser, resourceUser);

        this.userRepository.updateRole(userRoleUpdateDto);
    }

    public void updateNickname(Long requestUserId, UserNicknameUpdate userNicknameUpdate){
        User requestUser = this.userRepository.findById(requestUserId);
        User resourceUser = this.userRepository.findById(userNicknameUpdate.getUserId());

        hierarchyAuthCheck(requestUser, resourceUser);

        this.userRepository.updateNickname(resourceUser.getId(), userNicknameUpdate.getNewNickname());
    }

    public void hierarchyAuthCheck(User requestUser, User resourceUser) {
        Long requestUserId = requestUser.getId();
        Long resourceUserId = resourceUser.getId();

        //다른 사용자 정보에 접근하려는 경우
        //id가 동일하지 않다면
        if (!requestUserId.equals(resourceUserId)) {
            Role requestUserRole = requestUser.getRole();
            Role resourceUserRole = resourceUser.getRole();
            //권한도 낮으면 -> 관리자가 유저 정보에 접근하려는 게 아니라면
            if (requestUserRole.isLowerRole(resourceUserRole)) {
                throw new UnauthorizedException();
            }
        }
    }
}
