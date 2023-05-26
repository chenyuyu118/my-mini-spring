package xyz.cherish.context.support;

import xyz.cherish.beans.factory.config.BeanPostProcessor;
import xyz.cherish.context.ApplicationContext;
import xyz.cherish.context.ApplicationContextAware;
import xyz.cherish.exception.BeansException;

public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware aware) {
            aware.setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
