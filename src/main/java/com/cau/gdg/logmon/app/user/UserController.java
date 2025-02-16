package com.cau.gdg.logmon.app.user;

import com.cau.gdg.logmon.annotation.AuthenticationUserId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/test")
    public String test(@AuthenticationUserId String userId){
        return userId;
    }
}
