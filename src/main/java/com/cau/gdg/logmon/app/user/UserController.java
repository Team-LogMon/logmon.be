package com.cau.gdg.logmon.app.user;

import com.cau.gdg.logmon.annotation.AuthenticationUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public User getMe(@AuthenticationUserId String userId){
        return userService.getUser(userId);
    }

    @GetMapping
    public List<User> getUsers(List<String> userIds) {
        return userService.getUsers(userIds);
    }
}
