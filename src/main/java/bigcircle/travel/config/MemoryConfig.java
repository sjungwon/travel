package bigcircle.travel.config;

import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.UserBanInfoRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.repository.memory.UserBanInfoMemoryRepository;
import bigcircle.travel.repository.memory.UserMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public UserBanInfoRepository userBanInfoRepository(){return new UserBanInfoMemoryRepository();
    }

    @Bean
    public ItemRepository itemRepository(){
        return new ItemMemoryRepository();
    }

    @Bean
    public UserMemoryRepository userMemoryRepository(){
        return new UserMemoryRepository();
    }
}
