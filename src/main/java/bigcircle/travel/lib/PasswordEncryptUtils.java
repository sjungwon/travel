package bigcircle.travel.lib;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptUtils {

    public String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public String encodePassword(String password, int strength){
        return new BCryptPasswordEncoder(strength).encode(password);
    }

    public boolean matchPassword(String rawPassword,String encodedPassword){
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    public boolean matchPassword(String rawPassword,String encodedPassword, int strength){
        return new BCryptPasswordEncoder(strength).matches(rawPassword, encodedPassword);
    }
}
