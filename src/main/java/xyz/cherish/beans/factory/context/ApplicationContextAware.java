package xyz.cherish.beans.factory.context;

import xyz.cherish.beans.factory.Aware;
import xyz.cherish.exception.BeansException;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
