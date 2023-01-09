package bigcircle.travel.web.session;

import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //어노테이션이 붙어있고
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginUser.class);

        //타입이 맞으면
        boolean hasUserType = UserSession.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        Assert.state(request != null, "No HttpServletRequest");

        HttpSession session = request.getSession(false);

        if(session == null){
            throw new IllegalArgumentException("세션 정보를 가져올 수 없습니다.");
        }

        UserSession user = (UserSession) session.getAttribute(SessionConst.USER_SESSION.name());

        if(user == null){
            throw new IllegalArgumentException("세션에 회원 정보가 없습니다.");
        }

        return user;
    }
}
