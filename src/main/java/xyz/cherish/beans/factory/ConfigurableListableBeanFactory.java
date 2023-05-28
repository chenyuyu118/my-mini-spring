package xyz.cherish.beans.factory;

import xyz.cherish.beans.factory.config.AutowireCapableBeanFactory;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.ConfigurableBeanFactory;
import xyz.cherish.exception.BeansException;

public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory, ConfigurableBeanFactory, ListableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
    void preInstantiateSingletons() throws BeansException;
}
