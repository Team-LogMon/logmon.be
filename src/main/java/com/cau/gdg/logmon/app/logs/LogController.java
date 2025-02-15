package com.cau.gdg.logmon.app.logs;

import com.cau.gdg.logmon.app.logs.dto.LogCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping
    public void saveLog(@RequestBody LogCreateRequest request) {
        logService.saveLog(request);
    }

    @GetMapping
    public List<Log> getLogs(
            @RequestParam String projectId,
            @RequestParam Long start,
            @RequestParam Long end
    ) {

        if(projectId == null || start == null || end == null) {
            throw new IllegalArgumentException();
        }

        return logService.findByRange(projectId, start, end);
    }
}
