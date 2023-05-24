package xyz.cherish.beans;

public interface FactoryBean<T> {
    T getObject() throws Exception;
    boolean isSingleton();
}
