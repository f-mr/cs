package com.cs.service.impl;

import com.cs.dto.LogEntry;
import com.cs.service.LogAnalyzerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cs.service.LogFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class LogFileReaderImpl implements LogFileReader {

    final private Logger logger = LoggerFactory.getLogger(LogFileReader.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private LogAnalyzerService logAnalyzerService;

    @Override
    public void readFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            Scanner sc = new Scanner(fis);
            Map<String, Long> logEntriesMap = new HashMap<>();

            while (sc.hasNext()) {
                final LogEntry logEntry = mapper.readValue(sc.nextLine(), LogEntry.class);
                final String logId = logEntry.getId();
                logger.debug("Starting log [ID: {}] analysis", logId);
                logAnalyzerService.processLog(logEntry, logEntriesMap);
                logger.debug("Finished log [ID: {}] analysis", logId);
            }

        } catch (FileNotFoundException e) {
            logger.error("Wrong path to file.");
        } catch (JsonProcessingException e) {
            logger.error("Could not read log entry.");
        }
    }

}
