package bigcircle.travel.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @ToString @RequiredArgsConstructor
public class User {
    private final Role role;
    private final Long id;
    private final String account;
    private final String hashedPassword;
    private final String nickname;

    //암호 찾기 시에 이메일 코드 발송으로 발전 가능 - 현재는 간단한 확인 용도로 사용할 예정
    private final String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;
}
