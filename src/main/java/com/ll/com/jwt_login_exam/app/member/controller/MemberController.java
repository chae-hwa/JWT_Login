package com.ll.com.jwt_login_exam.app.member.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        response.addHeader("Authentication", "JWT토큰");
        return "username : %s, passoword : %s".formatted(loginDto.getUsername(), loginDto.getPassword());
    }

    @Data
    public static class LoginDto{
        private String username;
        private String password;
    }
}
