package com.log.eater.batch.processor;

import com.log.eater.business.core.EventProcessor;
import com.log.eater.model.Event;
import com.log.eater.model.LogLine;
import com.log.eater.model.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class LogLineProcessor implements ItemProcessor<LogLine, Event> {

    private static final Logger LOGGER = LogManager.getLogger(LogLineProcessor.class);

    @Autowired
    private EventProcessor eventProcessor;

    @Override
    public Event process(LogLine item) throws Exception {
        LOGGER.info("Processing item: " + item.toString());
        Event event = null;

        if(item.getState().equals(State.STARTED) && !eventProcessor.eventHasStarted(item.getId())){
            eventProcessor.addStartedEvent(item);

            if(eventProcessor.eventHasFinished(item.getId())){
                event = getEvent(item, eventProcessor.getFinishedEvent(item.getId()));
                eventProcessor.removeMapEntry(item.getId());
            }
        }

        if(item.getState().equals(State.FINISHED) && !eventProcessor.eventHasFinished(item.getId())){
            eventProcessor.addFinishedEvent(item);

            if(eventProcessor.eventHasStarted(item.getId())){
                event = getEvent(eventProcessor.getStartedEvent(item.getId()), item);
                eventProcessor.removeMapEntry(item.getId());
            }
        }

        return event;
    }

    private Event getEvent(LogLine startedEvent, LogLine finishedEvent){
        Event event = new Event();

        event.setEventId(startedEvent.getId());
        event.setDuration(finishedEvent.getTimestamp() - startedEvent.getTimestamp());
        event.setHost(startedEvent.getHost());
        event.setType(startedEvent.getType());

        event.setAlert(eventProcessor.flagEvent(startedEvent.getTimestamp(), finishedEvent.getTimestamp()));
        return event;
    }
}
