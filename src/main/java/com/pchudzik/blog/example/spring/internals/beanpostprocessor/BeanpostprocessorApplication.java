package com.pchudzik.blog.example.spring.internals.beanpostprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
class BeanpostprocessorApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(BeanpostprocessorApplication.class, args);
        final SampleService bean = ctx.getBean(SampleService.class);

        for (int i = 0; i < 5; i++) {
            bean.doWork(TimeUnit.SECONDS.toMillis(1));
        }
    }

}

