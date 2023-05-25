package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.PropertyValue;
import xyz.cherish.beans.factory.DisposableBean;
import xyz.cherish.beans.factory.InitializingBean;
import xyz.cherish.beans.factory.config.AutowireCapableBeanFactory;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.BeanPostProcessor;
import xyz.cherish.beans.factory.config.BeanReference;
import xyz.cherish.beans.factory.strategy.InstantiationStrategy;
import xyz.cherish.beans.factory.strategy.SimpleInstantiationStrategy;
import xyz.cherish.exception.BeansException;
import xyz.cherish.utils.BeanUtils;
import xyz.cherish.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Objects;

/**
 * 工厂Bean注册
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private static final InstantiationStrategy DEFAULT_STRATEGY = new SimpleInstantiationStrategy();
    private InstantiationStrategy instantiationStrategy;

    @Override
    /**
     * 创建bean的一般流程
     */
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 实际创建一个Bean
     *
     * @param beanName       bean名称
     * @param beanDefinition bean的定义
     * @return 创建的bean实例
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition); // 创建一个空实例
            applyPropertyValues(beanName, bean, beanDefinition); // 赋值属性
            bean = initializeBean(beanName, bean, beanDefinition); // 进行前置和后置处理
        } catch (Exception ex) {
            throw new BeansException("Instantiate failed", ex);
        }
        // 注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton()) {
            // 只对单例进行销毁注册
            if (bean instanceof DisposableBean disposableBean || !beanDefinition.getDestroyMethodName().isEmpty()) {
                registerDisposeBean(beanName, bean, beanDefinition);
            }
        }
    }

    private void registerDisposeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        registerDisposeBean(beanName, new DisposeBeanAdapter(bean, beanName, beanDefinition));
    }

    /**
     * 进行bean的前置和后置处理
     *
     * @param beanName       bean名称
     * @param bean           bean对象
     * @param beanDefinition bean的定义
     * @return bean处理后的结果
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        Object processedObj = applyBeanPostProcessorBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, processedObj, beanDefinition); // 执行我们需要的bean-init方法
        } catch (Exception e) {
            throw new BeansException("Invoke init method failed on bean[" + beanName + "]", e);
        }

        processedObj = applyBeanPostProcessorAfterInitialization(bean, beanName);
        return processedObj;
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean initializingBean) {
            initializingBean.afterPropertySet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (initMethodName != null && !initMethodName.isEmpty()) {
            Method initMethod = ClassUtils.getPublicMethod(bean, initMethodName);
            initMethod.invoke(bean);
        }
    }

    /**
     * 为bean设置属性
     *
     * @param beanName       bean名称
     * @param bean           bean实例
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

//    /**
//     * 通过代理创建对象
//     * @param beanName
//     * @param beanDefinition
//     * @return
//     */
//    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
//        Object bean = applyBeanPostProcessorBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
//        return bean;
//    }

    /**
     * 在bean初始化之前进行的后置处理
     *
     * @param bean     bean的Object
     * @param beanName bean的名称
     * @return bean的实例，可能为空
     */
    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object processedObj = bean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object afterProcess = processor.postProcessBeforeInitialization(processedObj, beanName);
            if (afterProcess == null) {
                return processedObj;
            }
            processedObj = afterProcess;
        }
        return processedObj;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        Object processedObj = bean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object afterProcess = processor.postProcessAfterInitialization(processedObj, beanName);
            if (afterProcess == null) {
                return processedObj;
            }
            processedObj = afterProcess;
        }
        return processedObj;
    }
}
