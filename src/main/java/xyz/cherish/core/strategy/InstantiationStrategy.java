package xyz.cherish.core.strategy;

import xyz.cherish.core.beans.BeanDefinition;
import xyz.cherish.exception.BeansException;

/**
 * 实例化
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
