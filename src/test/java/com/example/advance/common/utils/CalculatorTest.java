package com.example.advance.common.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @AfterEach
    void clear() {
        System.out.println("각각의 test 끝");
    }

    @Test
    void add() {

        int result = calculator.add(2, 3);

        assertEquals(5, result);

    }

    @Test
    void sub() {

        int result = calculator.subtract(5, 3);

        assertEquals(2, result);

    }

    @Test
    @DisplayName("나누기 성공 케이스 테스트")
    void divide_success() {

        int result = calculator.divide(10, 2);

        assertEquals(5, result);

    }

    @Test
    @DisplayName("나누기 실패 케이스 테스트 - 0으로 나눌 때")
    void divide_fail() {

        //0으로 나눌 시 ArithmeticException 에러 발생

        assertThrows(ArithmeticException.class,
                () -> calculator.divide(10, 0));

        /*int result = calculator.divide(10, 0);

        assertEquals(5, result);*/
    }

}