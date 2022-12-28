package bigcircle.travel;

import bigcircle.travel.config.ItemMemoryConfig;
import bigcircle.travel.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import(ItemMemoryConfig.class)
@SpringBootApplication(scanBasePackages = "bigcircle.travel.web")
public class TravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository repository){return new TestDataInit(repository);}
}
