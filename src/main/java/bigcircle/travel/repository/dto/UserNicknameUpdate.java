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
@NoArgsConstructor
@AllArgsConstructor
public class UserNicknameUpdate {
    @NotNull
    private Long userId;
    @NotBlank
    private String prevNickname;
    @Length(min=4, max=20)
    private String newNickname;

    public boolean isEqualNickname(){
        return prevNickname.equals(newNickname);
    }
}
