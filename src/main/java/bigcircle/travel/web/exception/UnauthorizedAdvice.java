package bigcircle.travel.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


//advice로 처리하는 경우
//예외가 던져진 경우 해당 예외를 먹고 응답으로 에러 템플릿 렌더해줌
//정확하게 예외를 먹고 넘어가는 건지는 모름
//BasicErrorController가 호출되지 않는 걸 디버깅 모드로 확인
//ResponseStatus가 있지만 SendError를 사용하지 않음
//WAS에서 에러 페이지로 다시 보내지 않음
@ControllerAdvice(basePackages = "bigcircle.travel.web")
public class UnauthorizedAdvice {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedExHandler(){
        return "error/401";
    }
}
