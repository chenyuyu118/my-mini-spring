package xyz.cherish.beans.factory.context;

import java.util.EventListener;

/**
 * 事件监听
 *
 * @param <E> 继承了ApplicationEvent的容器事件
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
