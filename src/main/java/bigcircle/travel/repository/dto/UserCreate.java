package bigcircle.travel.repository.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserCreate {
    private String account;
    private String hashedPassword;
    private String nickname;
    private String email;
    private Long roleId;

    private String createdAt;

    private String updatedAt;

    public UserCreate() {
    }

    public UserCreate(String account, String hashedPassword, String nickname, String email, Long roleId, String createdAt, String updatedAt) {
        this.account = account;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.email = email;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
