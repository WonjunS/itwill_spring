package com.itwill.spring1.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwill.spring1.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

// POJO(Plain Old Java Object): 평범한 자바 객체
// 특정 클래스를 상속해야만 하거나, 상속 후에 메서드들을 override해야만 하는 클래스가 아님
// 스프링 프레임워크는 POJO로 작성된 클래스를 컨트롤러로 사용할 수 있음

@Slf4j // Logger 객체(log)를 생성
@Controller 
// Spring MVC 컴포넌트 애너테이션(@Component, @Controller, @Service, @Repository, ...)들 중 하나
// Controller 컴포넌트라는 것을 dispatcherServlet에게 알려줌
public class ExampleController {

    @GetMapping("/") // GET 방식의 요청 주소가 "/"(context root)인 요청을 처리하는 메서드
    public String home(Model model) {
        log.info("home()");
        
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now);
        
        return "index"; // 뷰의 이름
    }
    
    @GetMapping("/ex1")
    public void example1() {
        log.info("example1() 호출");
        // 컨트롤러 메서드가 void인 경우(뷰의 이름을 리턴하지 않는 경우)                                                                                         
        // 요청 주소의 이름이 뷰의 이름이 됨
    }
    
    @GetMapping("/ex2")
    public void getParamEx(String username, int age) {
        log.info("getParamEx(username={}, age={})", username, age);
    }
    
    @PostMapping("/ex3")
    public String getParamEx2(
            @RequestParam("username") String username, 
            @RequestParam(defaultValue = "0") int age
    ) {
        log.info("getParamEx2(username={}, age={})", username, age);
        
        return "ex2";
    }
    
    @GetMapping("/ex4")
    public String getParamEx3(UserDto user) {
        log.info("getParamEx3({})", user);
        
        return "ex2";
    }
    
    @GetMapping("/sample")
    public void sample() {
        log.info("sample()");
    }
    
    @GetMapping("/forward")
    public String forwardTest() {
        log.info("forwardTest()");
        
        // 컨트롤러 메서드에서 다른 페이지로 forward하는 방법:
        // "forward:"으로 시작하는 문자열을 리턴하면,
        // DispatcherServlet은 리턴값이 뷰의 이름이 아니라 포워드 이동할 페이지 주소로 인식
        return "forward:/sample";
    }
    
    @GetMapping("/redirect")
    public String redirectTest(RedirectAttributes attrs) {
        log.info("redirectTest()");
        
        // 컨트롤러 메서드에서 리다이렉트되는 페이지까지 유지되는 정보를 저장할 때:
        attrs.addFlashAttribute("redAttr", "테스트");
        
        // 컨트롤러 메서드에서 다른 페이지로 redirect하는 방법:
        // "redirect:"으로 시작하는 문자열을 리턴하면,
        // DispatcherServlet은 리턴값이 뷰의 이름이 아니라 리다이렉트 이동할 페이지 주소로 인식
        return "redirect:/sample";
    }
    
}
