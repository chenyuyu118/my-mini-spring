package xyz.cherish.beans.factory.context.support;

import xyz.cherish.beans.factory.ConfigurableListableBeanFactory;
import xyz.cherish.beans.factory.config.BeanFactoryPostProcessor;
import xyz.cherish.beans.factory.config.BeanPostProcessor;
import xyz.cherish.beans.factory.context.ConfigurableApplicationContext;
import xyz.cherish.core.io.DefaultResourceLoader;
import xyz.cherish.exception.BeansException;

import java.util.Map;

/**
 * 抽象的应用上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> clazz) throws BeansException {
        return getBeanFactory().getBeansOfType(clazz);
    }

    @Override
    public <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return getBeanFactory().getBean(name, clazz);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // Bean实例化之前，先处理Bean工厂中BeanDefinition
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor在Bean初始化之前进行初始化
        registerBeanPostProcessors(beanFactory);

        // 实例化单例Bean
        beanFactory.preInstantiateSingletons();
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 对BeanFactory进行后置处理
     *
     * @param beanFactory 需要处理的bean工厂
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract void refreshBeanFactory() throws BeansException;


}
