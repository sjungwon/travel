package bigcircle.travel.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

//ResponseStatus로 처리하는 경우
//예외가 던져진 경우 http status code를 responseStatus에 작성한 코드로 변경
//이를 was에서 보고 던져진 예외와 Status 코드를 보고 basicErrorController를 호출함
//정확하게 어떻게 처리되는지는 좀 더 알아야 할듯 -> 일단 BasicErrorController가 호출되는 걸 디버깅 모드로 확인함
//Warning: when using this annotation on an exception class, or when setting the reason attribute of this annotation, the HttpServletResponse.sendError method will be used.
//ReseponseStatus 애노테이션에 설명이 나와있음
//예외 클래스에 어노테이션을 붙이면 HttpServletResponse.sendError가 사용됨
//response.sendError 사용하면 WAS에 에러를 전달함
//WAS에서 해당 에러 페이지를 내부로 다시 전송 -> BasicErrorController
//@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends HttpClientErrorException{

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "권한 없음");
    }
    public UnauthorizedException(String statusText) {
        super(HttpStatus.UNAUTHORIZED, statusText);
    }
}
