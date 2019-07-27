package com.log.eater.batch.config;

import com.log.eater.batch.listener.NotificationListener;
import com.log.eater.batch.mapper.LogLineJsonMapperWrapper;
import com.log.eater.batch.processor.LogLineProcessor;
import com.log.eater.model.Event;
import com.log.eater.model.LogLine;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;

/**
 * Spring Boot batch configuration
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Value("${file.sourcefolder}")
    public String sourceFolder;

    @Bean
    FlatFileItemReader<LogLine> flatFileItemReader(){
        FlatFileItemReader<LogLine> reader = new FlatFileItemReader<>();
        LogLineJsonMapperWrapper lineMapper = new LogLineJsonMapperWrapper();

        reader.setResource(new FileSystemResource(sourceFolder));
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public LogLineProcessor processor() {
        return new LogLineProcessor();
    }

    @Bean
    public JpaItemWriter<Event> jpaItemWriter() {
        JpaItemWriter<Event> writer = new JpaItemWriter();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step step1(JpaItemWriter<Event> writer) {
        return stepBuilderFactory.get("step1")
                .<LogLine, Event> chunk(10)
                .reader(flatFileItemReader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public Job storeEventJob(NotificationListener listener, Step step1) {
        return jobBuilderFactory.get("storeEventJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
