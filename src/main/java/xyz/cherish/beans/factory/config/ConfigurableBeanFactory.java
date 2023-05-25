package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.factory.HierarchicalBeanFactory;

/**
 * 可配置的BeanFactory
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    void destroySingleton();
}
