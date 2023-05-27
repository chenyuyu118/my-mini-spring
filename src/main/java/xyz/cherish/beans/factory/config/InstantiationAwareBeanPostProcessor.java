package xyz.cherish.beans.factory.config;

import xyz.cherish.exception.BeansException;

/**
 * Bean实例化之前执行
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
