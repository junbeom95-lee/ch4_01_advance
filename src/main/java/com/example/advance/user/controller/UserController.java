package com.example.advance.user.controller;

import com.example.advance.common.utils.JwtUtil;
import com.example.advance.user.model.reqeust.UpdateUserEmailRequest;
import com.example.advance.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get")
    public String getUserInfo(@AuthenticationPrincipal User user) {
        String username = user.getUsername();

        log.info(username);

        log.info("호출");
        return username;
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

    @PutMapping("/{username}/email")
    public ResponseEntity<String> updateEmail(@PathVariable String username, @RequestBody UpdateUserEmailRequest request) {

        log.info("3번 째 : Interceptor 통과 후 Controller 로직 수행");

        userService.updateUserEmail(username, request.getEmail());

        log.info("7번 째 : Controller 수행 완료");

        return ResponseEntity.ok("수정 완료");

    }

}
