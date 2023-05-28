package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.PropertyValues;
import xyz.cherish.exception.BeansException;

/**
 * Bean实例化之前执行
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在实例化类对象之前进行处理（通常用于创建代理对象）
     *
     * @param beanClass bean的类
     * @param beanName  bean的名称
     * @return 代理对象或者为空
     * @throws BeansException 错误
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 实例化之前处理属性值
     *
     * @param pv       实行值
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 处理后的属性键值对
     * @throws BeansException 创建错误
     */
    default PropertyValues postProcessPropertyValues(PropertyValues pv, Object bean, String beanName) throws BeansException {
        return pv;
    }
}
