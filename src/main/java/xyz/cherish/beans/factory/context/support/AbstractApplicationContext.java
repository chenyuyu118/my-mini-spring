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

    /**
     * 初始化ApplicationContext，包括Factory创建，BeanDefinition处理，BeanPostProcessor的注册
     * 实现ApplicationContextAware对象的Context注入，最后是bean的初始化
     *
     * @throws BeansException 初始化失败
     */
    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // Bean实例化之前，先处理Bean工厂中BeanDefinition
        invokeBeanFactoryPostProcessors(beanFactory);

        // 添加ApplicationContextAware的前置处理器，确保实现了ApplicationContextAware的对象
        // 可以在注入时自动获得ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

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

    public void close() {
        doClose();
    }

    public void registerShutdownHook() {
        Runnable shutdownHook = this::close;
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
    }

    protected void doClose() {
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingleton();
    }
}
