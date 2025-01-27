package com.cau.gdg.logmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LogmonApplication {

    @RestController
    class LogmonController {
        @GetMapping("/")
        String hello() {
            return "Hello " + "yunji" + "!";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(LogmonApplication.class, args);
    }

}
