package com.cau.gdg.logmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LogmonApplication {

    @RestController
    class LogmonController {
        @GetMapping("/")
        String hello() {
            return "Hello " + "beomseok" + "!";
        }
    }

    public static void main(String[] args) {


        SpringApplication.run(LogmonApplication.class, args);
    }

}
