package xyz.cherish.test.context.annotation;

import org.junit.Test;
import xyz.cherish.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.MultiFiledObj;
import xyz.cherish.test.model.SimpleService;


public class PackageScanTest {
    @Test
    public void testPackageScan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:component-scan.xml");
        SimpleService simpleService = applicationContext.getBean("simpleService", SimpleService.class);
        simpleService.sayHello();
        MultiFiledObj multiFiledObj = applicationContext.getBean("multiFiledObj", MultiFiledObj.class);
        System.out.println(multiFiledObj);
    }

}
