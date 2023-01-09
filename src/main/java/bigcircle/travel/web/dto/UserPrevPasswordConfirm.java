package bigcircle.travel.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserPrevPasswordConfirm {
    @NotNull
    private Long userId;

    @NotBlank
    private String account;

    @NotBlank
    private String rawPassword;
}
