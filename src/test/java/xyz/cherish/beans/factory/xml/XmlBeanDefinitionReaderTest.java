package xyz.cherish.beans.factory.xml;

import org.junit.Test;
import xyz.cherish.beans.factory.support.DefaultListableBeanFactory;
import xyz.cherish.core.io.ClassPathResource;
import xyz.cherish.core.io.DefaultResourceLoader;
import xyz.cherish.model.Manager;
import xyz.cherish.model.MultiFiledObj;
import xyz.cherish.model.User;

public class XmlBeanDefinitionReaderTest {
    @Test
    public void testReadXml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        xmlBeanDefinitionReader.loadBeanDefinition("classpath:spring.xml");

        User user = (User) beanFactory.getBean("user");
        System.out.println(user);

        Manager manager = (Manager) beanFactory.getBean("manager");
        System.out.println(manager);

        MultiFiledObj multiFiledObj = (MultiFiledObj) beanFactory.getBean("multi");
        System.out.println(multiFiledObj);
    }
}
