package xyz.cherish.context;

import xyz.cherish.beans.factory.Aware;
import xyz.cherish.exception.BeansException;

/**
 * 感知ApplicationContext的接口
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
