package bigcircle.travel.item.config;

import bigcircle.travel.item.repository.ItemMemoryRepository;
import bigcircle.travel.item.repository.ItemRepository;
import bigcircle.travel.item.service.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemMemoryConfig {

    @Bean
    public ItemService hotelService(){
        return new ItemService(hotelRepository());
    }

    @Bean
    public ItemRepository hotelRepository(){
        return new ItemMemoryRepository();
    }

}
