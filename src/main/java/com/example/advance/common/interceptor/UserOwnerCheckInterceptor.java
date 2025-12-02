package com.example.advance.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class UserOwnerCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 현재 로그인한 사용자 이름을 꺼냄
        //JWTFilter에서 넣어준 현재 로그인 사용자 꺼내기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();   //
        String currentUsername = auth.getName();

        //2. 요청한 URL -> /api/user/{username}/email 에서 어떤 username을 넣어줬는지 추출
        String path = request.getRequestURI();
        String decodePath = URLDecoder.decode(path, StandardCharsets.UTF_8);

        String[] parts = decodePath.split("/");
        String username = parts[parts.length - 2];

        //3. 로그인한 사용자와 이메일 변경을 하려는 사용자가 일치하는지 검사
        if (!currentUsername.equals(username)) {
            //
            log.info("소유자가 아닙니다. 접근 거부");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "소유자만 수정할 수 있습니다");
            return false;
        }

        log.info("2번 째 : Interceptor에서 Controller 들어가기 전 마지막 권한 검사 실행");

        //4. 만약에 일치하면 변경 -> controller 접근 허용

        return true;

        //5. 일치하지 않으면 -> controller 접근 불가

//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
