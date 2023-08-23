package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.*;


@RestController
public class CalculatorController {
    /**
     * 덧셈 GET API spec
     * HTTP Method - GET
     * HTTP Path - /add
     * 데이터 전달방법 - 쿼리 Key와 Value - int number1, int number2
     * API의 반환결과 - 숫자, 덧셈결과
     */
    /* @RequestParam으로 API파라미터와 HTTP요청 쿼리파라미터 연결
    @GetMapping("/add")
    public int addTwoNumbers(@RequestParam int number1, @RequestParam int number2) {
        return number1 + number2;
    }
    */
    @GetMapping("/add") //@ModelAttribute로 HTTP 요청 쿼리파라미터를 객체로 받음,생략 가능
    public int addTwoNumbers(@ModelAttribute CalculatorAddRequest request) {
        return request.getNumber1() + request.getNumber2();
    }

    /**
     * 곱셈 POST API spec
     * HTTP Method - POST
     * HTTP Path - /multiply
     * 데이터 전달방법 - HTTP Body - {
     * "number1":숫자,
     * "number2":숫자
     * }
     * API의 반환결과 - 숫자, 곱셈결과
     */
    @PostMapping("multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }


}
