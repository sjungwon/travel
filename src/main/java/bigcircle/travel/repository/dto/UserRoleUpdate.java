package bigcircle.travel.repository.dto;

import bigcircle.travel.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserRoleUpdate {
    @NotNull
    private Long userId;

    @NotNull
    private Role newRole;
}
