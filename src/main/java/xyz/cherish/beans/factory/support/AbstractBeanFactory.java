package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.FactoryBean;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.BeanPostProcessor;
import xyz.cherish.beans.factory.config.ConfigurableBeanFactory;
import xyz.cherish.exception.BeansException;
import xyz.cherish.utils.StringValueResolver;

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
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 首先尝试获取单例
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return getObjectForBeanInstance(singleton, beanName);
        }
        // 获取单例失败，尝试获取bean工厂或者是bean实例
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition);
        return getObjectForBeanInstance(bean, beanName);
    }

    public abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    /**
     * 通过beanFactory获取实例
     * @param beanInstance bean，可能为工厂Bean
     * @param beanName bean名称
     * @return bean实例
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object object = beanInstance;
        if (object instanceof FactoryBean factoryBean) {
            try {
                if (factoryBean.isSingleton()) {
                    // 如果为单例的话，从缓存中获取工厂已经生产的单例
                    object = factoryBeanCached.get(beanName);
                    if (object == null) {
                        // 如果不存在已有实例，则新建一个放入缓存
                        object = factoryBean.getObject();
                        factoryBeanCached.put(beanName, object);
                    }
                } else {
                    // 非单例创建，直接创建对应实例即可
                    return factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean throw Exception on object[" + beanName + "] creation", e);
            }
        }
        return object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return (T) getBean(name);
    }


    protected abstract boolean containBeanDefinition(String beanName);

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor); // 删除原有的processor，再进行一次添加，进行覆盖操作
        beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 处理嵌入式属性值
     *
     * @param value 值
     * @return 处理后的值
     */
    @Override
    public String resolveEmbeddedValue(String value) {
        String resolvedValue = value;
        for (StringValueResolver embeddedValueResolver : this.embeddedValueResolvers) {
            resolvedValue = embeddedValueResolver.resolveStringValue(resolvedValue);
        }
        return resolvedValue;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolvers.add(resolver);
    }
}
