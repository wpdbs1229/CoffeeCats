package org.hidevelop.coffeecats.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hidevelop.coffeecats.annotation.JwtAuth;
import org.hidevelop.coffeecats.model.dto.RegisterCafeReqDto;
import org.hidevelop.coffeecats.service.CafeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class CafeController {

    private final CafeService cafeService;
    @JwtAuth
    @PostMapping("/register")
    public ResponseEntity<String> registerCafe(
            @Valid @RequestBody RegisterCafeReqDto registerCafeReqDto,
            HttpServletRequest request){
        // HttpServletRequest의 getAttribute() 메서드는 Object 타입을 반환하기 때문에 원시 타입으로 바로 사용할 수 없음
        Long memberId = (Long)request.getAttribute("memberId");
        cafeService.registerCafe(registerCafeReqDto, memberId);
        return ResponseEntity.ok("카페가 등록되었습니다.");
    }
    
}
