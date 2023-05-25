package xyz.cherish.test.model;

import xyz.cherish.beans.factory.context.ApplicationContext;
import xyz.cherish.beans.factory.context.ApplicationContextAware;
import xyz.cherish.exception.BeansException;

public class SimpleService implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
