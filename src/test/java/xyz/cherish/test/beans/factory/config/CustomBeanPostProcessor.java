package xyz.cherish.test.beans.factory.config;

import xyz.cherish.beans.factory.config.BeanPostProcessor;
import xyz.cherish.exception.BeansException;
import xyz.cherish.test.model.User;

public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof User userBean) {
            userBean.setName("processed " + userBean.getName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
