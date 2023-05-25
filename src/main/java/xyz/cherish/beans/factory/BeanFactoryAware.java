package xyz.cherish.beans.factory;

import xyz.cherish.exception.BeansException;

/**
 * 感知BeanFactory的接口
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
