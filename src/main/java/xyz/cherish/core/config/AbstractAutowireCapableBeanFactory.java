package xyz.cherish.core.config;

import xyz.cherish.core.beans.BeanDefinition;
import xyz.cherish.core.beans.BeanReference;
import xyz.cherish.core.beans.PropertyValue;
import xyz.cherish.core.strategy.InstantiationStrategy;
import xyz.cherish.core.strategy.SimpleInstantiationStrategy;
import xyz.cherish.exception.BeansException;
import xyz.cherish.utils.BeanUtils;

import java.util.Iterator;
import java.util.Objects;

/**
 * 工厂Bean注册
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    public static final InstantiationStrategy DEFAULT_STRATEGY = new SimpleInstantiationStrategy();
    private InstantiationStrategy instantiationStrategy;

    @Override
    /**
     * 创建bean的一般流程
     */
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception ex) {
            throw new BeansException("Instantiate failed", ex);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 为bean设置属性
     * @param beanName bean名称
     * @param bean bean实例
     * @param beanDefinition bean定义
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            Iterator<PropertyValue> properties = beanDefinition.getPropertyValues().getProperties();
            while (properties.hasNext()) {
                PropertyValue propertyValue = properties.next();
                Object filedValue;
                if ((filedValue = propertyValue.getValue()) instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) filedValue;
                    filedValue = getBean(beanReference.getName());
                }
                BeanUtils.setFileValue(bean, propertyValue.getFiled(), filedValue);
            }
        } catch (Exception exception) {
            throw new BeansException("Error setting property values for bean: " + beanName, exception);
        }

    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    private InstantiationStrategy getInstantiationStrategy() {
        return Objects.requireNonNullElse(instantiationStrategy, DEFAULT_STRATEGY);
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 通过代理创建对象
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        return bean;
    }

    /**
     * 在bean初始化之前进行的后置处理
     * @param beanClass bean的Class
     * @param beanName bean的名称
     * @return bean的实例，可能为空
     */
    private Object applyBeanPostProcessorBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }

}
