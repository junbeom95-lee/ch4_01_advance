package com.example.advance.user.controller;

import com.example.advance.common.utils.JwtUtil;
import com.example.advance.user.model.reqeust.LoginRequest;
import com.example.advance.user.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    //    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/get")
    public String getUserInfo() {
        log.info("호출");
        return "호출되었음";
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token));
    }
}
