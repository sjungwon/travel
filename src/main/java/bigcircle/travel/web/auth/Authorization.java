package bigcircle.travel.web.auth;

import bigcircle.travel.domain.Role;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {
    Role[] roles() default Role.ALL;
    boolean allowGuest() default false;

}
