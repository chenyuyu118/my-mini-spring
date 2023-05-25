package xyz.cherish.beans.factory.context.event;

import xyz.cherish.beans.factory.context.ApplicationEvent;
import xyz.cherish.beans.factory.context.ApplicationListener;

/**
 * 事件多播器
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<? super ApplicationEvent> listener);

    void removeApplicationListener(ApplicationListener<? super ApplicationEvent> listener);

    void multicastEvent(ApplicationEvent event);
}
