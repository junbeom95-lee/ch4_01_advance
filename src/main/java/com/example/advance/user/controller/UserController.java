package com.example.advance.user.controller;

import com.example.advance.common.utils.JwtUtil;
import com.example.advance.user.model.reqeust.LoginRequest;
import com.example.advance.user.model.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
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
    public String getUserInfo(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        log.info(username);

        log.info("호출");
        return username;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> checkValidate(HttpServletRequest request) {

        String authorizationToken = request.getHeader("Authorization");

        String jwt = authorizationToken.substring(7);

        boolean validate = jwtUtil.validateToken(jwt);

        return ResponseEntity.ok(validate);
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        String jwt = authorizationHeader.substring(7);

        String username = jwtUtil.extractUsername(jwt);

        return ResponseEntity.ok(username);
    }
}
