package bigcircle.travel.web.controller;

import bigcircle.travel.domain.User;
import bigcircle.travel.service.UserService;
import bigcircle.travel.service.dto.LoginDto;
import bigcircle.travel.service.dto.RegisterDto;
import bigcircle.travel.web.session.SessionConst;
import bigcircle.travel.web.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService memberService;

    public AuthController(UserService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(defaultValue = "") String redirectURL, Model model){
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("redirectURL",redirectURL);
        return "/auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(defaultValue = "") String redirectURL, @Validated  @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request){

        //기본 validation
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);

            return "/auth/login";
        }

        //회원 검증
        try{
            User login = this.memberService.login(loginDto);
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.USER_SESSION.name(), new UserSession(login.getId(),login.getRole()));
        }catch(EmptyResultDataAccessException e){
            bindingResult.addError(new ObjectError("loginDto", "가입되지 않은 회원이거나 잘못된 암호를 입력하셨습니다."));
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);

            return "/auth/login";
        }

        log.info("requestURI = {}", redirectURL);

        return StringUtils.hasText(redirectURL) ? "redirect:" + redirectURL : "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("registerDto", new RegisterDto());

        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam(defaultValue = "") String redirectURL, @Validated @ModelAttribute RegisterDto registerDto, BindingResult bindingResult){

        //기본 validation
        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "/auth/register";
        }

        //중복에 대한 처리
        try{
            this.memberService.save(registerDto);
        }catch(DataIntegrityViolationException e){
            String message = e.getMessage();
            log.info("err message = {}",message);
            if(message != null && message.contains("PUBLIC.USERS(ID)")){
                bindingResult.addError(new FieldError("registerDto","id","중복된 아이디 입니다."));
            }
            if(message != null && message.contains("PUBLIC.USERS(EMAIL NULLS FIRST)")){
                bindingResult.addError(new FieldError("registerDto", "email","중복된 이메일 입니다."));
            }
            if(message !=null && message.contains("PUBLIC.USERS(NICKNAME NULLS FIRST)")){
                bindingResult.addError(new FieldError("registerDto","nickname", "중복된 닉네임 입니다."));
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "/auth/register";
        }

        if(StringUtils.hasText(redirectURL)){
            redirectURL = "?redirectURL=" + redirectURL;
        }

        return "redirect:/auth/login" + redirectURL;
    }
}
