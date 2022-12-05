package bigcircle.travel.hotel.config;

import bigcircle.travel.hotel.repository.HotelMemoryRepository;
import bigcircle.travel.hotel.repository.HotelRepository;
import bigcircle.travel.hotel.service.HotelService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelMemoryConfig {

    @Bean
    public HotelService hotelService(){
        return new HotelService(hotelRepository());
    }

    @Bean
    public HotelRepository hotelRepository(){
        return new HotelMemoryRepository();
    }

}
