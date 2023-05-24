package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的单例注册
 */
public class DefaultSingletonRegistry implements  SingletonBeanRegistry {

    private final Map<String, Object> singletonMap = new HashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        return singletonMap.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object singletonObj) {
        singletonMap.put(beanName, singletonObj);
    }
}
