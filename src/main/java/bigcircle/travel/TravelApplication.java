package bigcircle.travel;

import bigcircle.travel.hotel.config.HotelMemoryConfig;
import bigcircle.travel.hotel.repository.HotelRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import(HotelMemoryConfig.class)
@SpringBootApplication(scanBasePackages = "bigcircle.travel.hotel.web")
public class TravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(HotelRepository repository){return new TestDataInit(repository);}
}
