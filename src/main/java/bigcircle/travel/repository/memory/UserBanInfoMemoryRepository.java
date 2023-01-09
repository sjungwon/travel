package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.UserBanInfo;
import bigcircle.travel.lib.LongIdGenerator;
import bigcircle.travel.repository.UserBanInfoRepository;
import bigcircle.travel.repository.dto.UserBanInfoCreate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserBanInfoMemoryRepository implements UserBanInfoRepository {
    private final Map<Long, UserBanInfoCreate> db;
    private final Map<Long, LocalDateTime> dateDb;
    private final LongIdGenerator idGenerator;

    public UserBanInfoMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
        this.dateDb = new ConcurrentHashMap<>();
        this.idGenerator = new LongIdGenerator();
    }

    @Override
    public void save(UserBanInfoCreate userBanInfoDto) {
        Long id = idGenerator.newId();
        this.db.put(id, userBanInfoDto);
        this.dateDb.put(id, LocalDateTime.now());
    }

    @Override
    public List<UserBanInfo> findAllByUserId(Long userId) {
        List<UserBanInfo> results = new ArrayList<>(this.db.size());
        this.db.forEach((k,u)->{
            if(u.getUserId().equals(userId)){
                results.add(new UserBanInfo(k, u.getUserId(), u.getUserAccount(),u.getUserNickname(),u.getDescription(),this.dateDb.get(k)));
            }
        });

        return results;
    }

    public void clear(){
        this.db.clear();
    }
}
