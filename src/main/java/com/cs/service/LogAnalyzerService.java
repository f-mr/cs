package com.cs.service;

import com.cs.dto.LogEntry;

import java.util.Map;

public interface LogAnalyzerService {

    void processLog(LogEntry logEntry, Map<String, Long> logEntriesMap);

}
