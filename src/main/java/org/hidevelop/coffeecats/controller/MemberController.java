package org.hidevelop.coffeecats.controller;


import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.model.dto.SignUpReqDto;
import org.hidevelop.coffeecats.model.dto.SignUpResDto;
import org.hidevelop.coffeecats.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResDto> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        SignUpResDto result = memberService.signUp(signUpReqDto);
        return ResponseEntity.ok(result);
    }


}

