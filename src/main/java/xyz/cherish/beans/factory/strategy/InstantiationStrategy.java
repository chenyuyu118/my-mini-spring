package xyz.cherish.beans.factory.strategy;

import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.exception.BeansException;

/**
 * 实例化
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
