package xyz.cherish.context.event;

import xyz.cherish.context.ApplicationEvent;
import xyz.cherish.context.ApplicationListener;

/**
 * 事件多播器
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<? super ApplicationEvent> listener);

    void removeApplicationListener(ApplicationListener<? super ApplicationEvent> listener);

    void multicastEvent(ApplicationEvent event);
}
