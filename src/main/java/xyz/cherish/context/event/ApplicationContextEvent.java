package xyz.cherish.context.event;

import xyz.cherish.context.ApplicationContext;
import xyz.cherish.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取应用上下文
     *
     * @return 上下文ApplicationContext
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
