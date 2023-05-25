package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.factory.ConfigurableListableBeanFactory;
import xyz.cherish.exception.BeansException;

/**
 * BeanFactory的前置处理器，用于修改BeanDefinition
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
