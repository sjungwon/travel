package bigcircle.travel.config;

import bigcircle.travel.repository.memory.ItemMemoryRepository;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.service.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemMemoryConfig {

    @Bean
    public ItemService itemService(){
        return new ItemService(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository(){
        return new ItemMemoryRepository();
    }

}
