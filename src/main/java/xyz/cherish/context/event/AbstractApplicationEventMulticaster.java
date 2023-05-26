package xyz.cherish.context.event;

import xyz.cherish.beans.factory.BeanFactory;
import xyz.cherish.beans.factory.BeanFactoryAware;
import xyz.cherish.context.ApplicationEvent;
import xyz.cherish.context.ApplicationListener;
import xyz.cherish.exception.BeansException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    protected final Set<ApplicationListener<? super ApplicationEvent>> applicationListeners = new HashSet<>();

    private BeanFactory beanFactory;

    public AbstractApplicationEventMulticaster(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<? super ApplicationEvent> listener) {
        applicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<? super ApplicationEvent> listener) {
        applicationListeners.remove(listener);
    }
}
