package com.log.eater.batch.mapper;

import com.log.eater.model.LogLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LogLineJsonMapperWrapperTest {

    private LogLineJsonMapperWrapper logLineJsonMapperWrapper;

    @Before
    public void init(){
        logLineJsonMapperWrapper = new LogLineJsonMapperWrapper();
    }

    @Test
    public void testMapLine() throws Exception {
        String json = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\",\"host\":\"12345\", \"timestamp\":1491377495212}";
        LogLine logline = logLineJsonMapperWrapper.mapLine(json, 1);

        Assert.assertNotNull(logline);

        Assert.assertNotNull(logline.getId());
    }
}
