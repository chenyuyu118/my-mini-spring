package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.core.io.Resource;
import xyz.cherish.core.io.ResourceLoader;
import xyz.cherish.exception.BeansException;

public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();
    ResourceLoader getResourceLoader();
    void loadBeanDefinition(Resource resource) throws BeansException;
    default void loadBeanDefinition(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinition(resource);
    }
    default void loadBeanDefinition(String[] locations) throws BeansException {
        for (var location: locations) {
            loadBeanDefinition(location);
        }
    }
}
