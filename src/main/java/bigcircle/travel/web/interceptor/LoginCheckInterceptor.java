package bigcircle.travel.web.interceptor;

import bigcircle.travel.domain.User;
import bigcircle.travel.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Deprecated
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        log.info("requestURI = {}",requestURI);

        if(session == null){
            response.sendRedirect("/members/login?redirectURL="+requestURI);
            return false;
        }

        User user = (User) session.getAttribute(SessionConst.USER_SESSION.name());

        if(user == null){
            response.sendRedirect("/members/login?redirectURL="+requestURI);
            return false;
        }

        log.info("session = {}", session.getAttribute(SessionConst.USER_SESSION.name()));

        return true;
    }
}
