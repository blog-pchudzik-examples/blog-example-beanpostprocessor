package com.pchudzik.blog.example.spring.internals.beanpostprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
class FactoryBeanPostProcessor implements BeanPostProcessor {
    private static Logger log = LoggerFactory.getLogger(FactoryBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(SampleServiceFactoryBean.class.isAssignableFrom(bean.getClass())) {
            log.info("Preprocessing " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(SampleServiceFactoryBean.class.isAssignableFrom(bean.getClass())) {
            log.info("Postprocessing " + beanName);
        }
        return bean;
    }
}
