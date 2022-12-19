package bigcircle.travel.config;

import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.repository.*;
import bigcircle.travel.repository.jdbc.AddressJdbcRepository;
import bigcircle.travel.repository.jdbc.FileJdbcRepository;
import bigcircle.travel.repository.jdbc.ItemImageJdbcRepository;
import bigcircle.travel.repository.jdbc.ItemJdbcRepository;
import bigcircle.travel.repository.memory.CategoryEnumRepository;
import bigcircle.travel.service.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcRepositoryConfig {

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

    //예외
    @Bean
    public CategoryRepository categoryRepository(){
        return new CategoryEnumRepository();
    }
}
