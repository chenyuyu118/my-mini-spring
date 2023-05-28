package xyz.cherish.test.context.annotation;

import org.junit.Test;
import xyz.cherish.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.UserService;

public class AutowiredAnnotationTest {

    @Test
    public void testAutowiredAnnotation() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:value-annotation.xml");
        UserService bean = applicationContext.getBean(UserService.class);
        System.out.println(bean);

    }
}
