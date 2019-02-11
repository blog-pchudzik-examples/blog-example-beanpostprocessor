package com.pchudzik.blog.example.spring.internals.beanpostprocessor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static com.pchudzik.blog.example.spring.internals.beanpostprocessor.Measured.DEFAULT_PERFORMANCE_LOGGER;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Component
class MeasuringBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (hasAnyMeasuredMethod(bean)) {
            return measuredProxy(bean);
        }

        return bean;
    }

    private Object measuredProxy(Object bean) {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(new MeasuringMethodInterceptor());
        return proxyFactory.getProxy();
    }

    private boolean hasAnyMeasuredMethod(Object bean) {
        return Stream
                .of(ReflectionUtils.getAllDeclaredMethods(bean.getClass()))
                .anyMatch(method -> AnnotationUtils.getAnnotation(method, Measured.class) != null);
    }

    private static class MeasuringMethodInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            final Method method = invocation.getMethod();
            final Measured annotation = AnnotationUtils.getAnnotation(method, Measured.class);
            return annotation == null
                    ? invocation.proceed()
                    : proceedMeasured(invocation, annotation.value());
        }

        private Object proceedMeasured(MethodInvocation invocation, String loggerName) throws Throwable {
            final Logger logger = LoggerFactory.getLogger(loggerName);
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            try {
                return invocation.proceed();
            } finally {
                stopWatch.stop();
                logger.warn(
                        "Execution of {} took {} ms",
                        resolveLogMethod(invocation), stopWatch.getTotalTimeMillis());
            }
        }

        private String resolveLogMethod(MethodInvocation invocation) {
            return invocation.getMethod().getDeclaringClass().getCanonicalName() + "#" + invocation.getMethod().getName();
        }
    }
}
