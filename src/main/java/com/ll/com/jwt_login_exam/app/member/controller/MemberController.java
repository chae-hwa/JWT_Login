package com.ll.com.jwt_login_exam.app.member.controller;

import com.ll.com.jwt_login_exam.app.base.dto.RsData;
import com.ll.com.jwt_login_exam.app.member.entity.Member;
import com.ll.com.jwt_login_exam.app.member.request.dto.LoginDto;
import com.ll.com.jwt_login_exam.app.member.service.MemberService;
import com.ll.com.jwt_login_exam.app.security.entity.MemberContext;
import com.ll.com.jwt_login_exam.util.Util;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "MemberController", description = "로그인 기능과 로그인 된 회원의 정보를 제공 기능을 담당하는 컨트롤러")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal MemberContext memberContext) {
        return "안녕" + memberContext;
    }

    @GetMapping("/me")
    public ResponseEntity<RsData> me(@AuthenticationPrincipal MemberContext memberContext) {
        if ( memberContext == null ){ // 임시 코드, 나중에는 로그인 못하면 이곳으로 접근 못하게 설정할 예정
            return Util.spring.responseEntityOf(RsData.failOf(null));
        }
        return Util.spring.responseEntityOf(RsData.successOf(memberContext));
    }

    @PostMapping("/login")
    public ResponseEntity<RsData> login(@Valid @RequestBody LoginDto loginDto){

        loginDto.getUsername();

        Member member = memberService.findByUsername(loginDto.getUsername()).orElse(null);

        if( member == null ){
            return Util.spring.responseEntityOf(RsData.of("F-2", "일치하는 회원이 존재하지 않습니다."));
        }

        if( passwordEncoder.matches(loginDto.getPassword(), member.getPassword()) == false ){
            return Util.spring.responseEntityOf(RsData.of("F-3", "비밀번호가 일치하지 않습니다."));
        }

        log.debug("Util.json.toStr(member.getAccessTokenClaims()) : " + Util.json.toStr(member.getAccessTokenClaims()));

        String accessToken = memberService.genAccessToken(member);


        return Util.spring.responseEntityOf(
                RsData.of(
                        "S-1",
                        "로그인 성공, Access Token을 발급합니다.",
                        Util.mapOf(
                                "accessToken", accessToken
                        )
                ),
                Util.spring.httpHeadersOf("Authentication", accessToken)
        );
    }
}
