package com.sbs.promotionTest.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    @NotEmpty(message = "닉네임은 필수 항목입니다.")
    @Size(min = 3, max = 8)
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email
    private String email;
}
