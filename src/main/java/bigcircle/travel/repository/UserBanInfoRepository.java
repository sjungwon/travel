package bigcircle.travel.repository;

import bigcircle.travel.domain.UserBanInfo;
import bigcircle.travel.repository.dto.UserBanInfoCreate;

import java.util.List;

public interface UserBanInfoRepository {
    public void save(UserBanInfoCreate userBanInfoDto);

    public List<UserBanInfo> findAllByUserId(Long userId);
}
