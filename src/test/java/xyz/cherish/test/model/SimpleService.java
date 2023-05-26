package xyz.cherish.test.model;

import xyz.cherish.context.ApplicationContext;
import xyz.cherish.context.ApplicationContextAware;
import xyz.cherish.exception.BeansException;

public class SimpleService implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void sayHello() {
        System.out.println("Hello");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
