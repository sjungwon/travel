package bigcircle.travel.web.exception;

import bigcircle.travel.exception.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = "bigcircle.travel.web")
public class BasicDbExceptionAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmptyResultDataAccessException.class, NotFoundException.class})
    public String EmptyResultDataAccessHandler(){
        return "error/404";
    }
}
