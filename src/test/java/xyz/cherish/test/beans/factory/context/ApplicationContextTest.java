package xyz.cherish.test.beans.factory.context;

import org.junit.Test;
import xyz.cherish.beans.factory.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.User;
import xyz.cherish.test.model.UserWithInitMethod;

public class ApplicationContextTest {

    @Test
    public void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        User user = (User) classPathXmlApplicationContext.getBean("user");
        System.out.println(user);
    }

    @Test
    public void testInitAndDestroyMethod() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:initAndDestroyMethod.xml");
        applicationContext.registerShutdownHook();
        UserWithInitMethod test = (UserWithInitMethod) applicationContext.getBean("test");
    }


}
