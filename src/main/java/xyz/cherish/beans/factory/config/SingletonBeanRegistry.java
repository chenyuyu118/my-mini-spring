package xyz.cherish.beans.factory.config;

/**
 * 单例模式的Bean注册
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);
    void addSingleton(String beanName, Object singletonObj);
}
