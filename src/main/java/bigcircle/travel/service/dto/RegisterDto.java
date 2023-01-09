package bigcircle.travel.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ToString
public class RegisterDto {
    @Length(min=4, max=20)
    @Pattern(regexp = "[0-9A-Za-z-]+")
    private String account;
    @Length(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@!%*#?&])[A-Za-z\\d@!%*#?&]{3,}$")
    private String rawPassword;

    @Length(min=4, max=20)
    private String nickname;

    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Length(min=3, max = 50)
    private String email;
    public RegisterDto() {
    }

    public RegisterDto(String account, String rawPassword, String nickname, String email) {
        this.account = account;
        this.rawPassword = rawPassword;
        this.nickname = nickname;
        this.email = email;
    }
}
