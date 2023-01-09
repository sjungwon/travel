package bigcircle.travel.web.interceptor;

import bigcircle.travel.domain.Role;
import bigcircle.travel.domain.User;
import bigcircle.travel.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Deprecated
public class AdminCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.USER_SESSION.name()) == null){
            response.sendRedirect("/members/login?redirectURL=" + requestURI);
            return false;
        }

        User user = (User) session.getAttribute(SessionConst.USER_SESSION.name());

        log.info("member={}", user);

        Role role = user.getRole();

        if(role != Role.ADMIN){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "권한 없음");
            return false;
        }

        return true;
    }
}
