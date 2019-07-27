package com.log.eater.batch.processor;

import com.log.eater.business.core.EventProcessor;
import com.log.eater.model.Event;
import com.log.eater.model.LogLine;
import com.log.eater.model.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LogLineProcessorTest {

    @Mock
    private EventProcessor eventProcessor;

    private LogLineProcessor logLineProcessor;

    @Before
    public void init(){
        logLineProcessor = new LogLineProcessor();
        ReflectionTestUtils.setField(logLineProcessor, "eventProcessor", eventProcessor);
    }

    @Test
    public void testProcess_returnEvent_scenario1() throws Exception {
        LogLine logLine = startedEvent();

        when(eventProcessor.eventHasStarted(anyString())).thenReturn(false);
        when(eventProcessor.eventHasFinished(anyString())).thenReturn(true);
        when(eventProcessor.getFinishedEvent(anyString())).thenReturn(finishedEvent());
        doNothing().when(eventProcessor).addStartedEvent(any());
        doNothing().when(eventProcessor).removeMapEntry(anyString());

        Event event = logLineProcessor.process(logLine);

        Assert.assertNotNull(event);
        Assert.assertEquals(5L, event.getDuration());
        System.out.println(event.toString());
    }

    @Test
    public void testProcess_returnEvent_scenario2() throws Exception {
        LogLine logLine = finishedEvent();

        when(eventProcessor.eventHasStarted(anyString())).thenReturn(true);
        when(eventProcessor.eventHasFinished(anyString())).thenReturn(false);
        when(eventProcessor.getStartedEvent(anyString())).thenReturn(startedEvent());
        doNothing().when(eventProcessor).addFinishedEvent(any());
        doNothing().when(eventProcessor).removeMapEntry(anyString());

        Event event = logLineProcessor.process(logLine);

        Assert.assertNotNull(event);
        Assert.assertEquals(5L, event.getDuration());
        System.out.println(event.toString());
    }

    @Test
    public void testProcess_returnNull_scenario1() throws Exception {
        LogLine logLine = startedEvent();

        when(eventProcessor.eventHasStarted(anyString())).thenReturn(true);
        Event event = logLineProcessor.process(logLine);

        Assert.assertNull(event);
    }

    @Test
    public void testProcess_returnNull_scenario2() throws Exception {
        LogLine logLine = finishedEvent();

        when(eventProcessor.eventHasFinished(anyString())).thenReturn(true);
        Event event = logLineProcessor.process(logLine);

        Assert.assertNull(event);
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
