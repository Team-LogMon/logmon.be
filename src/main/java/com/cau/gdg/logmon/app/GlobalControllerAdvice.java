package com.cau.gdg.logmon.app;

import com.cau.gdg.logmon.exception.ClientVisibleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception e) {
    }

    @ExceptionHandler(ClientVisibleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleClientVisibleException(ClientVisibleException e) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("errorMessage", e.getMessage());
        return responseBody;
    }

}
