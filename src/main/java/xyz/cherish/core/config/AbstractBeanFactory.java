package xyz.cherish.core.config;

import xyz.cherish.core.beans.BeanDefinition;
import xyz.cherish.core.beans.FactoryBean;
import xyz.cherish.exception.BeansException;

import java.util.HashMap;
import java.util.Map;

/**
 * bean的抽象工厂，有单例获取单例，否则通过工厂出产
 */
public abstract class AbstractBeanFactory extends DefaultSingletonRegistry implements BeanFactory {
    private Map<String, Object> factoryBeanCached = new HashMap<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition);
        return getObjectForBeanInstance(bean, beanName);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    /**
     * 通过beanFactory获取实例
     * @param beanInstance bean，可能为工厂Bean
     * @param beanName bean名称
     * @return bean实例
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (beanInstance instanceof FactoryBean) {
            try {
                if (((FactoryBean<?>) beanInstance).isSingleton()) {
                    // 如果为单例的话，从缓存中获取工厂已经生产的单例
                    Object cached = factoryBeanCached.get(beanName);
                    if (cached == null) {
                        // 如果不存在已有实例，则新建一个放入缓存
                        Object object = ((FactoryBean<?>) beanInstance).getObject();
                        factoryBeanCached.put(beanName, object);
                    }
                } else {
                    // 非单例创建，直接创建对应实例即可
                    return ((FactoryBean<?>) beanInstance).getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean throw Exception on object[" + beanName + "] creation", e);
            }
        }
        return beanInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return (T) getBean(name);
    }


    @Override
    public boolean containBean(String beanName) {
        return containBeanDefinition(beanName);
    }

    protected abstract boolean containBeanDefinition(String beanName);
}
