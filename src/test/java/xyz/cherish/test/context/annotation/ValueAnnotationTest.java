package xyz.cherish.test.context.annotation;

import org.junit.Test;
import xyz.cherish.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.User;

public class ValueAnnotationTest {
    @Test
    public void testValueAnnotation() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:value-annotation.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
    }
}
