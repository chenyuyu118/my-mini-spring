package xyz.cherish.context;

import xyz.cherish.exception.BeansException;

/**
 * 可配置的ApplicationContext
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新ApplicationContext
     *
     * @throws BeansException 刷新失败
     */
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();
}
