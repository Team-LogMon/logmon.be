package com.cau.gdg.logmon.app.user;

import com.cau.gdg.logmon.annotation.AuthenticationUserId;
import com.cau.gdg.logmon.app.user.dto.GetMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public GetMeResponse getMe(@AuthenticationUserId(required = false) String userId) {
        if (userId == null) {
            return null;
        }

        try {
            User user = userService.getUser(userId);
            return new GetMeResponse(
                    true, user
            );
        } catch (Exception e) {
            return new GetMeResponse(
                    false, null
            );
        }
    }

    @GetMapping
    public List<User> getUsers(@RequestParam List<String> userIds) {
        return userService.getUsers(userIds);
    }
}
