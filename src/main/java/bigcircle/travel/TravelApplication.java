package bigcircle.travel;

import bigcircle.travel.config.MemoryRepositoryConfig;
import bigcircle.travel.config.JdbcRepositoryConfig;
import bigcircle.travel.config.LibConfig;
import bigcircle.travel.config.ServiceConfig;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.service.ItemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import({ServiceConfig.class, LibConfig.class, JdbcRepositoryConfig.class})
@SpringBootApplication(scanBasePackages = "bigcircle.travel.web")
public class TravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemService service, ItemRepository itemRepository){return new TestDataInit(service,itemRepository);}
}
