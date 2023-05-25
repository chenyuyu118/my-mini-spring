package xyz.cherish.beans.factory;

public interface InitializingBean {
    void afterPropertySet() throws Exception;
}
