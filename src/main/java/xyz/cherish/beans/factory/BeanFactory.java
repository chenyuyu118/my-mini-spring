package xyz.cherish.beans.factory;

import xyz.cherish.exception.BeansException;

/**
 * 实例工厂
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    default <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return (T) getBean(name);
    }
}
