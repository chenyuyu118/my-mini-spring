package xyz.cherish.beans.factory.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.exception.BeansException;

import java.lang.reflect.Constructor;

/**
 * 使用构造函数初始化实例
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    public static final Logger logger = LoggerFactory.getLogger(SimpleInstantiationStrategy.class);

    @Override
    public Object instantiate(BeanDefinition beanDefinition) {
        Class<?> beanClazz = beanDefinition.getBeanClass();
        try {
            Constructor<?> constructor = beanClazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            String s = "Failed to instantiate [" + beanClazz.getName() + "]";
            logger.error(s + e);
            throw new BeansException(s);
        }
    }
}
