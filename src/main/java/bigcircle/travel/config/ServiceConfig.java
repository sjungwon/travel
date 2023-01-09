package bigcircle.travel.config;

import bigcircle.travel.lib.PasswordEncryptUtils;
import bigcircle.travel.repository.ItemRepository;
import bigcircle.travel.repository.UserBanInfoRepository;
import bigcircle.travel.repository.UserRepository;
import bigcircle.travel.service.AdminService;
import bigcircle.travel.service.ItemService;
import bigcircle.travel.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {


    @Bean
    public ItemService itemService(ItemRepository itemRepository){
        return new ItemService(itemRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository, PasswordEncryptUtils passwordEncryptHelper){
        return new UserService(userRepository,passwordEncryptHelper);
    }

    @Bean
    public AdminService adminService(UserRepository userRepository, UserBanInfoRepository userBanInfoRepository){
        return new AdminService(userRepository, userBanInfoRepository);
    }
}
