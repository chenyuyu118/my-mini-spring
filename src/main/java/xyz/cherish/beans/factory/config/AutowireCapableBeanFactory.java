package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.factory.BeanFactory;
import xyz.cherish.exception.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException;
}
