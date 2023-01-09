package bigcircle.travel.config;

import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.UserBanInfoRepository;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.repository.jdbc.UserBanInfoJdbcRepository;
import bigcircle.travel.repository.jdbc.UserJdbcRepository;
import bigcircle.travel.repository.memory.ItemMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Bean
    UserBanInfoRepository userBanInfoRepository(DataSource dataSource){return new UserBanInfoJdbcRepository(dataSource);
    }

    @Bean
    UserRepository userRepository(DataSource dataSource){
        return new UserJdbcRepository(dataSource);
    }

    @Bean
    public ItemRepository itemRepository(){
        return new ItemMemoryRepository();
    }
}
