package com.example.advance.common.utils;

import com.example.advance.common.enums.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {


    private JwtUtil jwtUtil;

    private static final String SECRET_KEY = "myscertkeyfortestcodepracticemyscertkeyfortestcodepracticemyscertkeyfortestcodepractice";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        //실제 코드를 건들지 않고 객체의 값을 임의적으로 함
        ReflectionTestUtils.setField(jwtUtil, "secretKeyString", SECRET_KEY);
        jwtUtil.init();
    }


    //JWT 토큰 생성 메서드 test
    @Test
    @DisplayName("JWT 토큰 생성 시 username과 role 정보가 정상적으로 포함이 되었는지 테스트")
    void generateToken_정상케이스() {
        //given
        String username = "이서준";
        UserRoleEnum role = UserRoleEnum.ADMIN;

        //when
        String jwtToken = jwtUtil.generateToken(username, role);
        String jwt = jwtToken.substring(JwtUtil.BEARER_PREFIX.length());

        //then
        //1. jwtToken 시작이 Bearer로 시작하는지 검증
        assertThat(jwtToken).startsWith("Bearer ");
        JwtParser parser = (JwtParser) ReflectionTestUtils.getField(jwtUtil, "parser");

        //2. jwt가 유효한지 검증
        assert parser != null;
        Claims claims = parser.parseSignedClaims(jwt).getPayload();

        assertThat(claims.get("username", String.class)).isEqualTo(username);
        assertThat(claims.get("role", String.class)).isEqualTo(role.name());
    }

    //JWT 코드는 언제나 sub
    //Main을 도와주는 보조 역할


    //내가 테스트를 무엇을 하려는지 명심해야할 것
    //유효한 토큰이 들어온 경우 그 토큰이 유효한지 아닌지 검사
    @Test
    @DisplayName("유효한 토큰인지 아닌지 검사 유효하면 true 반환 - 성공 케이스")
    void validateToken_성공케이스() {
        //given
        String username = "이서준";
        UserRoleEnum role = UserRoleEnum.ADMIN;

        String token = jwtUtil.generateToken(username, role).substring(JwtUtil.BEARER_PREFIX.length());

        //when
        boolean result = jwtUtil.validateToken(token);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유효한 토큰인지 아닌지 검사 유효하면 true 반환 - 실패 케이스 - 잘못된 jwt 토큰 제공")
    void validateToken_싪패케이스_01() {
        //given
        String token = "thisisworngtoken";

        //when
        boolean result = jwtUtil.validateToken(token);

        //then
        assertThat(result).isFalse();
    }


}