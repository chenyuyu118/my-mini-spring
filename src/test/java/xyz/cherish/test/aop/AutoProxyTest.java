package xyz.cherish.test.aop;

import org.junit.Test;
import xyz.cherish.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.UserInterface;

/**
 * 测试根据Xml自动创建代理类对象
 */
public class AutoProxyTest {

    @Test
    public void testAutoProxyBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto_proxy.xml");
        Object bean = applicationContext.getBean("user");
        UserInterface user = (UserInterface) bean;
        user.sayHello();
//        System.out.println(user);
    }
}
