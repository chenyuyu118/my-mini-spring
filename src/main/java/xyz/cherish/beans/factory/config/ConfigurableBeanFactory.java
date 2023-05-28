package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.factory.HierarchicalBeanFactory;
import xyz.cherish.utils.StringValueResolver;

/**
 * 可配置的BeanFactory
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    void destroySingleton();

    String resolveEmbeddedValue(String value);

    void addEmbeddedValueResolver(StringValueResolver valueResolver);
}
