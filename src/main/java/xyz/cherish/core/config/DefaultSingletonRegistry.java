package xyz.cherish.core.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的单例注册
 */
public class DefaultSingletonRegistry implements SingletonRegistry {

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
