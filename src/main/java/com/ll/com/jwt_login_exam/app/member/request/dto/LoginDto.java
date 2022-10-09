package com.ll.com.jwt_login_exam.app.member.request.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {
    @NotEmpty(message = "username을(를) 입력해주세요.")
    private String username;
    @NotEmpty(message = "password을(를) 입력해주세요.")
    private String password;
}