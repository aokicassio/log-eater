package com.log.eater.business.core;

import com.log.eater.model.LogLine;
import com.log.eater.model.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EventProcessorTest {

    private EventProcessor eventProcessor;

    @Before
    public void init(){
        eventProcessor = new EventProcessor();
        ReflectionTestUtils.setField(eventProcessor, "alert", 4);

    }

    @Test
    public void testEventHasStarted(){
        LogLine logLine = startedEvent();
        eventProcessor.addStartedEvent(logLine);

        boolean eventHasStarted = eventProcessor.eventHasStarted("eventa");

        Assert.assertTrue(eventHasStarted);

        LogLine startedEvent = eventProcessor.getStartedEvent("eventa");
        Assert.assertNotNull(startedEvent);
        Assert.assertEquals("eventa", startedEvent.getId());
    }

    @Test
    public void testEventHasFinished(){
        LogLine logLine = finishedEvent();
        eventProcessor.addFinishedEvent(logLine);
        boolean eventHasFinished = eventProcessor.eventHasFinished("eventa");
        Assert.assertTrue(eventHasFinished);

        LogLine finishedEvent = eventProcessor.getFinishedEvent("eventa");

        Assert.assertNotNull(finishedEvent);
        Assert.assertEquals("eventa", finishedEvent.getId());
    }

    @Test
    public void testFlagEvent(){
        boolean flag = eventProcessor.flagEvent(1491377495212L, 1491377495217L);
        Assert.assertTrue(flag);
    }

    private LogLine startedEvent(){
        LogLine logLine = new LogLine();
        logLine.setId("eventa");
        logLine.setTimestamp(1491377495212L);
        logLine.setState(State.STARTED);
        logLine.setHost("localhost");

        return logLine;
    }

    private LogLine finishedEvent(){
        LogLine logLine = new LogLine();
        logLine.setId("eventa");
        logLine.setTimestamp(1491377495217L);
        logLine.setState(State.FINISHED);
        logLine.setHost("localhost");

        return logLine;
    }
}
