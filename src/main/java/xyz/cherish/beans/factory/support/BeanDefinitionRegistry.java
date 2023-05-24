package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.factory.config.BeanDefinition;

/**
 * bean定义注册
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
    boolean containBeanDefinition(String beanName);
}
