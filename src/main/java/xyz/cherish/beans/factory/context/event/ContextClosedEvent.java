package xyz.cherish.beans.factory.context.event;

/**
 * 上下文关闭事件
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
