package xyz.cherish.beans.factory;

public interface DisposableBean {
    void destroy() throws Exception;
}
