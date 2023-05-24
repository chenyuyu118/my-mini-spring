package xyz.cherish.core.config;

import org.junit.Test;
import xyz.cherish.core.beans.BeanDefinition;
import xyz.cherish.core.beans.PropertyValues;
import xyz.cherish.core.strategy.User;

public class DefaultListableBeanFactoryTest {

    @Test
    public void testProperties() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(User.class);
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addProperty("name", "Lily");
        beanDefinition.setPropertyValues(propertyValues);
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinition);
        User userBean = (User) defaultListableBeanFactory.getBean("user");
        System.out.println(userBean.getName());
    }
}
