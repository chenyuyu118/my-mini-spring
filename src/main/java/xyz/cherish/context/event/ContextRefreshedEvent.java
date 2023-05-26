package xyz.cherish.context.event;

/**
 * Context刷新事件
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
