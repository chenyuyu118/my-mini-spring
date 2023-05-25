package xyz.cherish.test.beans.factory.context.event;

import org.junit.Test;
import xyz.cherish.beans.factory.context.ApplicationEvent;
import xyz.cherish.beans.factory.context.ApplicationListener;
import xyz.cherish.beans.factory.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ApplicationEventTest {

    /**
     * 测试泛型分配
     */
    @Test
    public void testGenericType() {
        TestEventListener testEventListener = new TestEventListener();
        Type[] genericInterfaces = testEventListener.getClass().getGenericInterfaces();

        String actualTypeName = ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments()[0].getTypeName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(actualTypeName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        TestEvent testEvent = new TestEvent("111");
        TestEvent1 testEvent1 = new TestEvent1("111");
        if (testEvent.getClass().isAssignableFrom(clazz)) {
            System.out.println(testEvent.getClass().getName() + " ok");
        } else {
            System.out.println(testEvent.getClass().getName() + " failed");
        }
        if (testEvent1.getClass().isAssignableFrom(clazz)) {
            System.out.println(testEvent1.getClass().getName() + " ok");
        } else {
            System.out.println(testEvent1.getClass().getName() + " failed");
        }
    }

    @Test
    public void testCustomEventListener() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:event.xml");
        context.registerShutdownHook();
    }

    private static class TestEvent extends ApplicationEvent {
        public TestEvent(Object source) {
            super(source);
        }
    }

    private static class TestEvent1 extends ApplicationEvent {

        public TestEvent1(Object source) {
            super(source);
        }
    }

    private static class TestEventListener implements ApplicationListener<TestEvent> {
        @Override
        public void onApplicationEvent(TestEvent event) {
            System.out.println(1);
        }
    }
}
