package bigcircle.travel.config;

import bigcircle.travel.repository.*;
import bigcircle.travel.repository.jdbc.*;
import bigcircle.travel.repository.memory.CategoryEnumRepository;
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
    public ItemRepository itemRepository(DataSource dataSource){return new ItemJdbcRepository(dataSource);
    }

    @Bean
    public AddressRepository addressRepository(DataSource dataSource){
        return new AddressJdbcRepository(dataSource);
    }

    @Bean
    public FileRepository fileRepository(DataSource dataSource){
        return new FileJdbcRepository(dataSource);
    }

    @Bean
    public ItemImageRepository itemImageRepository(DataSource dataSource){
        return new ItemImageJdbcRepository(dataSource);
    }

    //예외 - ENUM
    @Bean
    public CategoryRepository categoryRepository(){
        return new CategoryEnumRepository();
    }
}
