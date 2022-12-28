package bigcircle.travel.config;

import bigcircle.travel.lib.file.FileStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibConfig {
    @Bean
    public FileStore fileStore(){return new FileStore();}
}
