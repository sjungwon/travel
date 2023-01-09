package bigcircle.travel.web.controller;

import bigcircle.travel.domain.User;
import bigcircle.travel.web.dto.UserEmailUpdate;
import bigcircle.travel.repository.dto.UserNicknameUpdate;
import bigcircle.travel.service.UserService;
import bigcircle.travel.web.dto.UserPasswordUpdate;
import bigcircle.travel.web.auth.Authorization;
import bigcircle.travel.web.dto.UserPrevPasswordConfirm;
import bigcircle.travel.web.session.LoginUser;
import bigcircle.travel.web.session.UserSession;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@Authorization()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public String getUserInfo(@LoginUser UserSession userSession, Model model){
        User user = this.userService.getUserById(userSession.getUserId());
        model.addAttribute("user",user);
        return "user/info";
    }

    @GetMapping("/nickname")
    public String getUserNickNameUpdateForm(@LoginUser UserSession userSession, Model model){
        User userById = userService.getUserById(userSession.getUserId());

        model.addAttribute("userNicknameUpdate",new UserNicknameUpdate(userById.getId(), userById.getNickname(),""));

        return "user/nickname-form";
    }

    @PostMapping("/nickname")
    public String updateNickname(@LoginUser UserSession userSession,@Validated @ModelAttribute UserNicknameUpdate userNicknameUpdateDto, BindingResult bindingResult){
        //id가 임의로 바뀌어 들어온 경우
        if(!userSession.getUserId().equals(userNicknameUpdateDto.getUserId())){
            userNicknameUpdateDto.setUserId(userSession.getUserId());
            return "user/nickname-form";
        }

        if(userNicknameUpdateDto.isEqualNickname()){
            bindingResult.addError(new FieldError("userNicknameUpdate", "newNickname", "동일한 닉네임을 작성했습니다."));
        }

        if(bindingResult.hasErrors()){
            return "user/nickname-form";
        }

        try{
            this.userService.updateNickname(userSession.getUserId(), userNicknameUpdateDto.getNewNickname());
        }catch(DuplicateKeyException e){
            bindingResult.addError(new FieldError("userNicknameUpdate","newNickname","중복된 닉네임입니다."));
            return "user/nickname-form";
        }

        return "redirect:/users/info";
    }

    @GetMapping("/email")
    public String updateEmail(@LoginUser UserSession userSession,Model model){
        User userById = userService.getUserById(userSession.getUserId());

        model.addAttribute(new UserEmailUpdate(userById.getId(), userById.getEmail(),""));

        return "user/email-form";
    }

    @PostMapping("/email")
    public String updateEmail(@LoginUser UserSession userSession,@Validated @ModelAttribute UserEmailUpdate userEmailUpdateDto, BindingResult bindingResult){
        //id가 임의로 바뀌어 들어온 경우
        if(!userSession.getUserId().equals(userEmailUpdateDto.getUserId())){
            userEmailUpdateDto.setUserId(userSession.getUserId());
            return "user/email-form";
        }

        if(userEmailUpdateDto.isEqualEmail()){
            bindingResult.addError(new FieldError("userEmailUpdate","newEmail","동일한 이메일을 작성했습니다"));
        }
        if(bindingResult.hasErrors()){
            return "user/email-form";
        }

        try{
            this.userService.updateEmail(userSession.getUserId(), userEmailUpdateDto.getNewEmail());
        }catch(DuplicateKeyException e){
            bindingResult.addError(new FieldError("userEmailUpdate","newEmail","중복된 이메일입니다."));
            return "user/email-form";
        }

        return "redirect:/users/info";
    }

    @GetMapping("/password")
    public String confirmPassword(@LoginUser UserSession userSession, Model model){
        User userById = userService.getUserById(userSession.getUserId());

        model.addAttribute(new UserPrevPasswordConfirm(userById.getId(), userById.getAccount(),""));

        return "user/prev-password-form";
    }

    @PostMapping("/password")
    public String confirmPassword(@LoginUser UserSession userSession, @Validated @ModelAttribute UserPrevPasswordConfirm prevPasswordConfirm, BindingResult bindingResult, Model model){
        //id가 임의로 바뀌어 들어온 경우
        if(!userSession.getUserId().equals(prevPasswordConfirm.getUserId())){
            prevPasswordConfirm.setUserId(userSession.getUserId());
            return "user/prev-password-form";
        }

        if(bindingResult.hasErrors()){
            return "user/prev-password-form";
        }

        boolean result = this.userService.confirmPrevPassword(userSession.getUserId(), prevPasswordConfirm.getRawPassword());

        if(!result){
            bindingResult.addError(new ObjectError("prevPasswordConfirm","다시 입력해주세요."));
            return "user/prev-password-form";
        }

        //이전 암호에 대한 인증이 된 경우에만 post에 대한 응답으로 뷰를 내려줌
        User userById = userService.getUserById(userSession.getUserId());

        model.addAttribute(new UserPasswordUpdate(userById.getId(), userById.getAccount(),""));

        return "user/password-form";
    }

//    @GetMapping("/password/new")
//    public String updatePassword(@LoginUser UserSession userSession, Model model){
//
//
//
//    }

    @PostMapping("/password/new")
    public String updatePassword(@LoginUser UserSession userSession, @Validated @ModelAttribute UserPasswordUpdate passwordUpdate, BindingResult bindingResult){
        //id가 임의로 바뀌어 들어온 경우
        if(!userSession.getUserId().equals(passwordUpdate.getUserId())){
            passwordUpdate.setUserId(userSession.getUserId());
            return "user/password-form";
        }

        if(bindingResult.hasErrors()){
            return "user/password-form";
        }

        this.userService.updatePassword(userSession.getUserId(), passwordUpdate.getRawPassword());

        return "redirect:/users/info";
    }
}
