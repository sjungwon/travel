package bigcircle.travel.web.auth;

import bigcircle.travel.domain.Role;
import bigcircle.travel.domain.User;
import bigcircle.travel.web.session.SessionConst;
import bigcircle.travel.web.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("handler = {}, {}", handler.getClass(), handler.toString());
        log.debug("request.getRequestURI() = " + request.getRequestURI());

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        //메서드 어노테이션 없으면 클래스 어노테이션 가져옴 -> 메서드의 우선 순위를 더 높게 설정
        //클래스, 메서드에 어노테이션을 붙인 경우 합쳐서 사용할지 우선 순위를 정해서 하나만 정할지 고민
        //클래스에 넓은 범위의 허용(ALL, USER)을 놓고 메서드에 좁은 범위의 허용(MANAGER)을 사용하는 경우가 많을 것으로 생각
        //클래스, 메서드에 붙은 경우 메서드를 우선 순위에 두고 메서드의 권한만 적용
        Authorization authInfo = method.getMethodAnnotation(Authorization.class) != null ? method.getMethodAnnotation(Authorization.class) : method.getBean().getClass().getAnnotation(Authorization.class);

        //둘 다 없으면
        if(authInfo == null){
            return true;
        }

        List<Role> roles = Arrays.asList(authInfo.roles());

        HttpSession session = request.getSession(false);

        if(session == null){
            if(authInfo.allowGuest()){
                return true;
            }

            //session = null -> 로그인 안한 상태
            //TODO - RestAPI로 변경하면 redirect가 아닌 예외 던져서 예외 정보 전달
            String requestURI = request.getRequestURI();
            response.sendRedirect( "/auth/login?redirectURL=" + requestURI);
            return false;
        }

        UserSession memberSession = (UserSession) session.getAttribute(SessionConst.USER_SESSION.name());

        if(!roles.contains(memberSession.getRole()) && !roles.contains(Role.ALL)){
            //TODO - RestAPI로 변경하면 sendError로 예외를 WAS로 전파 하는게 아닌 예외를 직접 던져서 예외 정보 전달
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "접근 권한이 없습니다.");
            return false;
        }

        return true;
    }
}
