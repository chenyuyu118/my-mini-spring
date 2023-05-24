package xyz.cherish.core.config;

import xyz.cherish.exception.BeansException;

/**
 * 实例工厂
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
    <T> T getBean(String name, Class<T> clazz) throws BeansException;
//    <T> T getBean(Class<T> clazz) throws BeansException;
    boolean containBean(String name);
}
