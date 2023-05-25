package xyz.cherish.beans.factory.context.event;

import xyz.cherish.beans.factory.BeanFactory;
import xyz.cherish.beans.factory.context.ApplicationEvent;
import xyz.cherish.beans.factory.context.ApplicationListener;
import xyz.cherish.exception.BeansException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<? super ApplicationEvent> applicationListener : applicationListeners) {
            if (supportsEvent(applicationListener, event)) {
                applicationListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 查看监听器是否支持事件
     *
     * @param applicationListener 事件监听器
     * @param event               事件
     * @return true or false
     */
    private boolean supportsEvent(ApplicationListener<? super ApplicationEvent> applicationListener, ApplicationEvent event) {
        Type type = applicationListener.getClass().getGenericInterfaces()[0]; // 获取监视器实际监视的Event泛型
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0]; // 获取ApplicationEvent的子Event的实际类型

        String typeName = actualTypeArgument.getTypeName();
        Class<?> eventClass; // 事件类名称
        try {
            eventClass = Class.forName(typeName); // 获取实际类型的Class对象
        } catch (ClassNotFoundException ex) {
            throw new BeansException("wrong event class name: " + typeName);
        }
        return eventClass.isAssignableFrom(event.getClass()); // 将这个对象分配给当前Event测试
    }
}
