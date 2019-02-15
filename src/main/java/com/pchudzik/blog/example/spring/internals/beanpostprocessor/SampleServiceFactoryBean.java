package com.pchudzik.blog.example.spring.internals.beanpostprocessor;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
class SampleServiceFactoryBean implements FactoryBean<SampleService> {
    @Override
    public SampleService getObject() throws Exception {
        return new SampleService();
    }

    @Override
    public Class<?> getObjectType() {
        return SampleService.class;
    }
}
