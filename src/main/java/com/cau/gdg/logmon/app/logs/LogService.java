package com.cau.gdg.logmon.app.logs;

import com.cau.gdg.logmon.app.logs.dto.LogCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void saveLog(LogCreateRequest request) {
        logRepository.save(Log.of(
                request.getProjectId(),
                request.getMessage(),
                request.getSource(),
                request.getTimeStamp(),
                request.getSeverity(),
                request.getJsonPayload()
        ));

        /**
         * todo: 로그 생성 이벤트 발행
         */
    }

    public List<Log> findByRange(String projectId, long start, long end) {
        return logRepository.findByTimeRange(projectId, start, end);
    }
}
