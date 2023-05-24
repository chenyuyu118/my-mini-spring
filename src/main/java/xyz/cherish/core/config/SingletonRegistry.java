package xyz.cherish.core.config;

/**
 * 单例模式的Bean注册
 */
public interface SingletonRegistry {
    Object getSingleton(String beanName);
    void addSingleton(String beanName, Object singletonObj);
}
