package xyz.cherish.beans.factory.config;

import org.junit.Test;
import xyz.cherish.beans.PropertyValues;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.BeanReference;
import xyz.cherish.beans.factory.support.DefaultListableBeanFactory;
import xyz.cherish.model.Manager;
import xyz.cherish.model.MultiFiledObj;
import xyz.cherish.model.User;
import xyz.cherish.utils.BeanUtils;

public class DefaultListableBeanFactoryTest {

    @Test
    public void beanUtilsTest() throws NoSuchFieldException, IllegalAccessException {
        MultiFiledObj multiFiledObj = new MultiFiledObj();
//        System.out.println(multiFiledObj.getClass().getField("intFiled").getType());
        BeanUtils.setFileValue(multiFiledObj, "intFiled", "1");
        BeanUtils.setFileValue(multiFiledObj, "booleanFiled", "true");
        BeanUtils.setFileValue(multiFiledObj, "byteFiled", 1);
        BeanUtils.setFileValue(multiFiledObj, "stringFiled", 123.1);
        BeanUtils.setFileValue(multiFiledObj, "longFiled", "13123123123");
        BeanUtils.setFileValue(multiFiledObj, "doubleFiled", 1.334);
        System.out.println(multiFiledObj);
    }

    @Test
    public void testProperties() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        BeanDefinition beanDefinition = getUserLilyBeanDefinition();
        defaultListableBeanFactory.registerBeanDefinition("user", beanDefinition);
        User userBean = (User) defaultListableBeanFactory.getBean("user");
        System.out.println(userBean.getName());
    }

    private static BeanDefinition getUserLilyBeanDefinition() {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(User.class);
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addProperty("name", "Lily");
        beanDefinition.setPropertyValues(propertyValues);
        return beanDefinition;
    }

    @Test
    public void testBeanReferenceProperties() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        /*
        注册一个用户Bean
         */
        BeanDefinition beanDefinition = getUserLilyBeanDefinition();
        beanFactory.registerBeanDefinition("user", beanDefinition);
        /*
        注册ManagerBean，其中包含User Bean的引用
         */
        BeanDefinition definition = new BeanDefinition();
        definition.setBeanClass(Manager.class);
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addProperty("name", "M. Jane");
        propertyValues.addProperty("childUser", new BeanReference("user"));
        definition.setPropertyValues(propertyValues);
        beanFactory.registerBeanDefinition("manager", definition);
        /*
        测试获取链条下的两个bean
         */
        Manager bean = (Manager) beanFactory.getBean("manager");
        System.out.println("manager: " + bean.getName() + ", child: " + bean.getChildUser().getName());
    }

}
