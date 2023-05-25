package xyz.cherish.beans.factory.context;

/**
 * 发布ApplicationEvent
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
