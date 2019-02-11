package com.pchudzik.blog.example.spring.internals.beanpostprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class SampleService {
    private static final Logger log = LoggerFactory.getLogger(SampleService.class);

    @Measured
    public void doWork(long sleepTime) {
        try {
            log.info("Starting work");
            Thread.sleep(sleepTime);
            log.info("Finishing work");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
