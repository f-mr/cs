package com.cs.service.impl;

import com.cs.dto.LogEntry;
import com.cs.service.LogAnalyzerService;
import com.cs.dao.LogEntity;
import com.cs.dao.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static java.lang.Math.abs;

@Service
public class LogAnalyzerServiceImpl implements LogAnalyzerService {

    final private Logger logger = LoggerFactory.getLogger(LogAnalyzerService.class);

    @Autowired
    private LogRepository repository;


    @Override
    public void processLog(LogEntry logEntry, Map<String, Long> logEntriesMap) {
        if (logEntriesMap.containsKey(logEntry.getId())) {
            updateLogEntity(logEntry, logEntriesMap);
        } else {
            saveNewLogEntity(logEntry, logEntriesMap);
        }
    }

    private void updateLogEntity(LogEntry logEntry, Map<String, Long> logEntriesMap) {
        final Long timestamp = logEntriesMap.get(logEntry.getId());
        final Optional<LogEntity> log = repository.findById(logEntry.getId());

        log.ifPresent(entity -> {
            long eventDuration = abs(timestamp - logEntry.getTimestamp());
            boolean raiseAlert = eventDuration >= 4;
            entity.updateLogInfo(
                    logEntry.getHost(),
                    logEntry.getType(),
                    eventDuration,
                    raiseAlert);
            if (raiseAlert)
                logger.info("Log (ID: {}) took more than 4ms (eventDuration: {})", logEntry.getId(), eventDuration);

            repository.save(entity);
            logEntriesMap.remove(logEntry.getId());
        });
    }

    private void saveNewLogEntity(LogEntry logEntry, Map<String, Long> logEntriesMap) {
        logEntriesMap.put(logEntry.getId(), logEntry.getTimestamp());
        repository.save(
                LogEntity.builder()
                        .id(logEntry.getId())
                        .host(logEntry.getHost())
                        .type(logEntry.getType())
                        .build());
    }

}
