package com.log.eater.batch.listener;

import com.log.eater.model.Event;
import com.log.eater.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void afterJob(final JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Job execution completed successfully.");

            LOGGER.info("The following events have been recorded to database:");

            List<Event> list = new ArrayList<>();
            eventRepository.findAll().forEach(e -> list.add(e));

            for(Event event: list){
                LOGGER.info(event.toString());
            }
        }
    }
}
