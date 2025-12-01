package com.example.advance.common.filter;

import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(3)
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // JWT 토큰이 있는지 없는지 검사
        String requestUrl = request.getRequestURI();

        if (requestUrl.equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            log.info("JWT token이 필요합니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다.");
            return;
        }

        // JWT 토큰이 있다면 유효한 토큰인지 검사

        String jwt = authorizationHeader.substring(7);

        if (!jwtUtil.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Unauthorized\"}");
        }

        // JWT 토큰에서 복호화 한 데이터 저장하기

        String username = jwtUtil.extractUsername(jwt);

//        request.setAttribute("username", username); -> Spring Security 방식에 맞는 방법으로 만들 예정

        String auth = jwtUtil.extractRole(jwt);

        UserRoleEnum userRoleEnum = UserRoleEnum.valueOf(auth);

        //Spring Security에서 사용하는 User객체를
        User user = new User(username, "", List.of(userRoleEnum::getRole));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

        filterChain.doFilter(request, response);
    }
}
