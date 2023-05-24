package xyz.cherish.core.config;

import xyz.cherish.core.beans.BeanDefinition;
import xyz.cherish.exception.BeansException;

/**
 * bean定义注册
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
