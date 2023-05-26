package xyz.cherish.context;

/**
 * 发布ApplicationEvent
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
