package com.cau.gdg.logmon.app.user.dto;

import com.cau.gdg.logmon.app.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetMeResponse {
    private boolean isLogined;
    private User user;
}
