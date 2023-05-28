package xyz.cherish.context.annotation;

import xyz.cherish.beans.PropertyValues;
import xyz.cherish.beans.factory.BeanFactory;
import xyz.cherish.beans.factory.BeanFactoryAware;
import xyz.cherish.beans.factory.config.ConfigurableBeanFactory;
import xyz.cherish.beans.factory.config.InstantiationAwareBeanPostProcessor;
import xyz.cherish.exception.BeansException;
import xyz.cherish.utils.BeanUtils;

import java.lang.reflect.Field;

/**
 * 后置处理器，负责处理注解的对应属性注入
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtils.setFileValue(bean, field.getName(), value);
            }
        }
        return pvs;
    }
}
