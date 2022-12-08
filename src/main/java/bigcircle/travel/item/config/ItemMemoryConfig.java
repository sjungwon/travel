package bigcircle.travel.item.config;

import bigcircle.travel.item.repository.ItemMemoryRepository;
import bigcircle.travel.item.repository.ItemRepository;
import bigcircle.travel.item.service.ItemService;
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
