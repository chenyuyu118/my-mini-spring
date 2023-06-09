package xyz.cherish.test.context;

import org.junit.Test;
import xyz.cherish.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.SimpleService;
import xyz.cherish.test.model.User;

public class ApplicationContextAwareTest {

    @Test
    public void testApplicationContextAware() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContextAware.xml");
        SimpleService service = (SimpleService) applicationContext.getBean("service");
        User user = (User) service.getApplicationContext().getBean("user");
        System.out.println(user);
    }
}
