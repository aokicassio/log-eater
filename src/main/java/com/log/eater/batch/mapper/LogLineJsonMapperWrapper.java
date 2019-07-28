package com.log.eater.batch.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.eater.batch.processor.LogLineProcessor;
import com.log.eater.model.LogLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.file.LineMapper;

import java.io.IOException;

public class LogLineJsonMapperWrapper implements LineMapper<LogLine> {

    private static final Logger LOGGER = LogManager.getLogger(LogLineJsonMapperWrapper.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public LogLine mapLine(String line, int lineNumber)  {
        LOGGER.debug("Reading JSON line " + lineNumber);

        LogLine logLine = null;
        try {
            logLine = mapper.readValue(line, LogLine.class);
        } catch (IOException e) {
            LOGGER.error("Failed to read provided JSON String in line: " + lineNumber);
            e.printStackTrace();
        }

        return logLine;
    }
}
