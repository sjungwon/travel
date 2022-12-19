package bigcircle.travel.config;

import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.lib.PrefixViewPathGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibConfig {
    @Bean
    public PrefixViewPathGenerator prefixViewPathGenerator(){
        return new PrefixViewPathGenerator();
    }


    @Bean
    public FileStore fileStore(){return new FileStore();}
}
