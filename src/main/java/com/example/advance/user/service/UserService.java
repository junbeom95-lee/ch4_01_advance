package com.example.advance.user.service;

import com.example.advance.common.entity.User;
import com.example.advance.common.utils.JwtUtil;
import com.example.advance.user.model.reqeust.LoginRequest;
import com.example.advance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public User save(User user) {
        return userRepository.save(user);
    }

    public String login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("중복된 사용자가 있습니다"));

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        if (!matches) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        return jwtUtil.generateToken(username, user.getRoleEnum());
    }

    @Transactional
    public void updateUserEmail(String username, String email) {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        user.updateEmail(email);
        userRepository.save(user);

    }
}
