package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.FactoryBean;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.BeanPostProcessor;
import xyz.cherish.beans.factory.config.ConfigurableBeanFactory;
import xyz.cherish.exception.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean的抽象工厂，有单例获取单例，否则通过工厂出产
 */
public abstract class AbstractBeanFactory extends DefaultSingletonRegistry implements ConfigurableBeanFactory {
    private final Map<String, Object> factoryBeanCached = new HashMap<>();
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();


    @Override
    public Object getBean(String beanName) throws BeansException {
        // 首先尝试获取单例
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }
        // 获取单例失败，尝试获取bean工厂或者是bean实例
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

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void addBeanPostProcessors(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor); // 删除原有的processor，再进行一次添加，进行覆盖操作
        beanPostProcessors.add(beanPostProcessor);
    }
}
