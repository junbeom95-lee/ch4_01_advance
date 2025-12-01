package com.example.advance.user.controller;

import com.example.advance.user.model.reqeust.LoginRequest;
import com.example.advance.user.model.response.LoginResponse;
import com.example.advance.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = userService.login(request);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
