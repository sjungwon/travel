package bigcircle.travel.web.controller;

import bigcircle.travel.domain.User;
import bigcircle.travel.domain.Role;
import bigcircle.travel.domain.UserBanInfo;
import bigcircle.travel.repository.dto.UserBanInfoCreate;
import bigcircle.travel.repository.dto.UserNicknameUpdate;
import bigcircle.travel.service.AdminService;
import bigcircle.travel.service.UserService;
import bigcircle.travel.web.auth.Authorization;
import bigcircle.travel.web.session.LoginUser;
import bigcircle.travel.repository.dto.UserRoleUpdate;
import bigcircle.travel.web.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@Slf4j
@Authorization(roles={Role.ADMIN, Role.MANAGER})
public class AdminController {

    private final AdminService adminService;

    private final UserService userService;

    public AdminController(AdminService adminService, UserService memberService) {
        this.adminService = adminService;
        this.userService = memberService;
    }

    @GetMapping
    public String index(){
        return "admin/admin";
    }

    @GetMapping("/users")
    public String getUsers(@LoginUser UserSession userSession, Model model){
        User requestUser = this.userService.getUserById(userSession.getUserId());
        List<User> users = this.adminService.getUsers();

        model.addAttribute("loginUser", requestUser);
        model.addAttribute("users", users);

        return "admin/user-list";
    }

    @GetMapping("/users/banned")
    public String getBannedUsers(@LoginUser UserSession userSession, Model model){
        User requestUser = this.userService.getUserById(userSession.getUserId());
        List<User> bannedUser = this.adminService.getBannedUsers();

        model.addAttribute("loginUser", requestUser);
        model.addAttribute("users", bannedUser);

        return "admin/user-list-banned";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id, @RequestParam(defaultValue = "") String listPath,Model model){
        User user = this.userService.getUserById(id);
        List<UserBanInfo> userBanInfoList = this.adminService.getBanInfoByUserId(id);

        model.addAttribute("listPath", listPath);
        model.addAttribute("user", user);
        model.addAttribute("userBanInfoList",userBanInfoList);

        return "admin/user-info";
    }

    @GetMapping("/users/nickname/{id}")
    public String getUpdateNicknameForm(@PathVariable Long id, Model model){
        User userById = userService.getUserById(id);
        model.addAttribute("userNicknameUpdateDto",new UserNicknameUpdate(userById.getId(),userById.getNickname(),""));

        return "admin/user-nickname-form";
    }

    @PostMapping("/users/nickname")
    public String updateUserNickname(@LoginUser UserSession userSession, @Validated @ModelAttribute UserNicknameUpdate userNicknameUpdate, BindingResult bindingResult){

        if(userNicknameUpdate.isEqualNickname()){
            bindingResult.addError(new FieldError("userNicknameUpdateDto", "newNickname", "동일한 닉네임을 작성했습니다."));
        }

        if(bindingResult.hasErrors()){
            return "admin/user-nickname-form";
        }

        try{
            this.adminService.updateNickname(userSession.getUserId(), userNicknameUpdate);
        }catch(DuplicateKeyException e){
            bindingResult.addError(new FieldError("userNicknameUpdateDto","newNickname","중복된 닉네임입니다."));
            return "admin/user-nickname-form";
        }

        return "redirect:/admin/users/" + userNicknameUpdate.getUserId();
    }

    @GetMapping("/users/role/{id}")
    public String getUserRoleForm(@LoginUser UserSession userSession, @PathVariable Long id, Model model){
        User requestUser = this.userService.getUserById(userSession.getUserId());
        User userById = userService.getUserById(id);

        model.addAttribute("user",userById);
        model.addAttribute("userRoleUpdateDto",new UserRoleUpdate(userById.getId(),userById.getRole()));

        List<Role> roles = Arrays.stream(Role.values()).filter(r -> r.getId() > requestUser.getRole().getId() && !r.name().equals("BANNED")).collect(Collectors.toList());
        model.addAttribute("roles", roles);

        return "admin/user-role-form";
    }

    @PostMapping("/users/role")
    public String updateUserRole(@LoginUser UserSession userSession, @Validated @ModelAttribute UserRoleUpdate userRoleUpdateDto, BindingResult bindingResult, Model model){
        User requestUser = this.userService.getUserById(userSession.getUserId());

        if(bindingResult.hasErrors()){
            List<Role> roles = Arrays.stream(Role.values()).filter(r -> r.getId() > requestUser.getRole().getId() && !r.name().equals("BANNED")).collect(Collectors.toList());
            model.addAttribute("roles", roles);
            return "admin/user-role-form";
        }

        this.adminService.updateRole(userSession.getUserId(), userRoleUpdateDto);

        return "redirect:/admin/users/" + userRoleUpdateDto.getUserId();
    }

    @GetMapping("/ban/{id}")
    public String getBanForm(@PathVariable Long id, Model model){
        User user = this.userService.getUserById(id);

        UserBanInfoCreate userBanInfoDto = new UserBanInfoCreate(user.getId(),user.getAccount(),user.getNickname(),"");

        model.addAttribute("userBanInfoDto",userBanInfoDto);

        return "admin/user-banned";
    }

    @PostMapping("/ban/{id}")
    public String banUser(@LoginUser UserSession userSession, @PathVariable Long id, @Validated @ModelAttribute UserBanInfoCreate userBanInfoDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "admin/user-banned";
        }

        this.adminService.banUser(userSession.getUserId(), userBanInfoDto);

        return "redirect:/admin/users";
    }

    @PostMapping("/unban/{id}")
    public String unbanUser(@LoginUser UserSession userSession, @PathVariable Long id){
        User user = this.userService.getUserById(id);

        this.adminService.unBan(userSession.getUserId(), user.getId());

        return "redirect:/admin/users/banned";
    }
}
