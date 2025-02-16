package com.cau.gdg.logmon.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<User> getUsers(List<String> userIds) {
        return userRepository.findByIdIn(userIds);
    }
}
