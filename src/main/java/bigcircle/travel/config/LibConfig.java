package bigcircle.travel.config;

import bigcircle.travel.lib.PasswordEncryptUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibConfig {
    @Bean
    public PasswordEncryptUtils passwordEncryptUtils(){
        return new PasswordEncryptUtils();
    }
}
