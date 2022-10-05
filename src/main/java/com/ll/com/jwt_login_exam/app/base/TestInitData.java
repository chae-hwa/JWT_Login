package com.ll.com.jwt_login_exam.app.base;

import com.ll.com.jwt_login_exam.app.member.entity.Member;
import com.ll.com.jwt_login_exam.app.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestInitData {
    @Bean
    CommandLineRunner initData(MemberService memberService, PasswordEncoder passwordEncoder){
        String password = passwordEncoder.encode("1234");
        return args -> {
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");
        };
    }
}
