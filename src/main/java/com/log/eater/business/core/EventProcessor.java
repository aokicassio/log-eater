package com.log.eater.business.core;

import com.log.eater.model.LogLine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventProcessor {

    private Map<String, LogLine> startedEvents;
    private Map<String, LogLine> finishedEvents;

    @Value("${alert.duration}")
    public int alert;

    public EventProcessor(){
        startedEvents = new HashMap<String, LogLine>();
        finishedEvents = new HashMap<String, LogLine>();
    }

    public boolean eventHasStarted(String eventId){
        return startedEvents.containsKey(eventId);
    }

    public boolean eventHasFinished(String eventId){
        return finishedEvents.containsKey(eventId);
    }

    public void addStartedEvent(LogLine logLine){
        startedEvents.put(logLine.getId(), logLine);
    }

    public void addFinishedEvent(LogLine logLine){
        finishedEvents.put(logLine.getId(), logLine);
    }

    public LogLine getStartedEvent(String id){
        return startedEvents.get(id);
    }

    public LogLine getFinishedEvent(String id){
        return finishedEvents.get(id);
    }

    public void removeMapEntry(String id){
        startedEvents.remove(id);
        finishedEvents.remove(id);
    }

    public boolean flagEvent(Long startTimestamp, Long finishTimestamp){
        Long diff = finishTimestamp - startTimestamp;
        return diff > alert;
    }

}
