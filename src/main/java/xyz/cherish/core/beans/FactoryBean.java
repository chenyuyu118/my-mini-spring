package xyz.cherish.core.beans;

public interface FactoryBean<T> {
    T getObject() throws Exception;
    boolean isSingleton();
}
