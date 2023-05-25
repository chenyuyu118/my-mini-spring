package xyz.cherish.beans.factory.config;

import xyz.cherish.exception.BeansException;

/**
 * 用于修改实例化bean的扩展修改点
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
