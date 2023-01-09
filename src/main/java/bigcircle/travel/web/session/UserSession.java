package bigcircle.travel.web.session;

import bigcircle.travel.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UserSession {
    private Long userId;
    private Role role;
}
