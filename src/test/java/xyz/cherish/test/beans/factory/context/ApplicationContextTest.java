package xyz.cherish.test.beans.factory.context;

import org.junit.Test;
import xyz.cherish.beans.factory.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.User;

public class ApplicationContextTest {

    @Test
    public void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        User user = (User) classPathXmlApplicationContext.getBean("user");
        System.out.println(user);
    }


}
