package bigcircle.travel.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class LoginDto {


    @NotBlank
    private String account;

    @NotBlank
    private String rawPassword;

    public LoginDto() {
    }

    public LoginDto(String account, String rawPassword) {
        this.account = account;
        this.rawPassword = rawPassword;
    }
}
