package xyz.cherish.test.ioc;

import org.junit.Test;
import xyz.cherish.beans.factory.support.DefaultListableBeanFactory;
import xyz.cherish.beans.factory.xml.XmlBeanDefinitionReader;
import xyz.cherish.test.beans.factory.config.CustomBeanPostProcessor;
import xyz.cherish.test.model.User;

public class BeanFactoryPostProcessorTest {

    @Test
    public void testBeanFactoryPostProcessor() {
        /*
        初始化ioc容器
         */
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);
        definitionReader.loadBeanDefinition("classpath:spring.xml");
        /*
        添加后置处理器
         */
        CustomBeanPostProcessor customBeanPostProcessor = new CustomBeanPostProcessor();
        beanFactory.addBeanPostProcessors(customBeanPostProcessor);
        /*
        检查后置处理结果
         */
        User user = (User) beanFactory.getBean("user");
        System.out.println(user);
    }
}
