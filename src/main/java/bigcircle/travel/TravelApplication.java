package bigcircle.travel;

import bigcircle.travel.item.config.ItemMemoryConfig;
import bigcircle.travel.item.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

//exclude = SecurityAutoConfiguration.class -> spring security에서 bcrypt만 사용하기 위해서 기본 설정 제외
//@Import({MemoryConfig.class, LibConfig.class, ServiceConfig.class, MvcConfig.class})
@Import({JdbcConfig.class, LibConfig.class, ServiceConfig.class, MvcConfig.class})
@SpringBootApplication(scanBasePackages = "bigcircle.travel.web", exclude = SecurityAutoConfiguration.class)
public class TravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository repository){return new TestDataInit(repository);}
}
