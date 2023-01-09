package bigcircle.travel.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBanInfoCreate {
    @NotNull
    private Long userId;

    @NotBlank
    private String userAccount;

    @NotBlank
    private String userNickname;

    @Length(min= 20, max=300)
    private String description;
}
