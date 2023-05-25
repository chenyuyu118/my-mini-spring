package xyz.cherish.beans.factory.context;

import java.util.EventObject;

/**
 * 容器事件
 */
public class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
