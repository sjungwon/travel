package bigcircle.travel.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class UserBanInfo {
    private final Long id;
    private final Long userId;
    private final String userAccount;
    private final String userNickname;
    private final String description;
    private final LocalDateTime createdAt;
}
