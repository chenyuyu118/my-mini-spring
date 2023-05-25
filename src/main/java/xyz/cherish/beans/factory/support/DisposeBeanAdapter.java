package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.factory.DisposableBean;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.utils.ClassUtils;

import java.lang.reflect.Method;

public class DisposeBeanAdapter implements DisposableBean {
    public final String destroyMethodName;
    private final String beanName;
    private Object bean;

    public DisposeBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean disposableBean) {
            disposableBean.destroy();
        }
        if (!destroyMethodName.isEmpty() && !(bean instanceof DisposableBean disposableBean && "destroy".equals(destroyMethodName))) {
            Method destoryMethod = ClassUtils.getPublicMethod(bean, destroyMethodName);
            destoryMethod.invoke(bean);
        }
    }
}
