package xyz.cherish.beans.factory.context.event;

/**
 * Context刷新事件
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
