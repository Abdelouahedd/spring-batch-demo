package com.example.springbatchdemo.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class JobExecutionListener {

    @BeforeJob
    void beforeJob(JobExecution jobExecution) {
            log.info("Job Started !!");
    }

    @AfterJob
    void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED by success !");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("!!! JOB Failed !");
        }
    }

}
