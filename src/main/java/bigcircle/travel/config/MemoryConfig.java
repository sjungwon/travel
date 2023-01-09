package bigcircle.travel.config;

import bigcircle.travel.repository.*;
import bigcircle.travel.repository.memory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MemoryConfig {

    @Bean
    public UserBanInfoRepository userBanInfoRepository(){return new UserBanInfoMemoryRepository();
    }

    @Bean
    public UserMemoryRepository userMemoryRepository(){
        return new UserMemoryRepository();
    }

    @Bean
    public ItemRepository itemRepository(DataSource dataSource){
        return new ItemMemoryRepository(addressRepository(), itemImageRepository());
    }

    @Bean
    public ItemImageRepository itemImageRepository(){
        return new ItemImageMemoryRepository();
    }

    @Bean
    public AddressRepository addressRepository(){return new AddressMemoryRepository();}

    @Bean
    public CategoryRepository categoryRepository(){
        return new CategoryEnumRepository();
    }

    @Bean
    public FileRepository fileRepository() {return new FileMemoryRepository();}
}
