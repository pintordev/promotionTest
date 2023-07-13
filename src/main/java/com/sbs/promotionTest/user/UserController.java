package com.sbs.promotionTest.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(UserForm userForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserForm userForm, BindingResult bindingResult) {

        // form validation
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        boolean isReject = false;

        // password confirm validation
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "password not matched", "입력한 비밀번호가 일치하지 않습니다.");
            isReject = true;
        }

        // nickname unique validation
        if (userService.hasNickName(userForm.getNickname())) {
            bindingResult.rejectValue("nickname", "nickname already exists", "이미 존재하는 닉네임입니다.");
            isReject = true;
        }

        // email unique validation
        if (userService.hasEmail(userForm.getEmail())) {
            bindingResult.rejectValue("email", "email already exists", "이미 존재하는 이메일입니다.");
            isReject = true;
        }

        if (isReject) {
            return "signup";
        }

        // signup
        this.userService.create(userForm.getNickname(), userForm.getPassword(), userForm.getEmail());

        return "redirect:/user/login";
    }
}
