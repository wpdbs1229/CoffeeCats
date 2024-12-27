package org.hidevelop.coffeecats.controller;


import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.model.dto.LoginReqDto;
import org.hidevelop.coffeecats.service.LoginService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginReqDto loginReqDto) {
        String token = loginService.login(loginReqDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_STRING, TOKEN_PREFIX + token);

        return ResponseEntity.ok().headers(headers).body("Login Successful");
    }
}

