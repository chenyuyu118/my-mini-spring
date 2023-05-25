package xyz.cherish.beans.factory.context.support;

import xyz.cherish.beans.factory.support.DefaultListableBeanFactory;
import xyz.cherish.exception.BeansException;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }


    /**
     * 初始化BeanFactory
     *
     * @throws BeansException 创建失败
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory newBeanFactory = createBeanFactory();
        loadBeanDefinition(newBeanFactory);
        this.beanFactory = newBeanFactory;
    }

    /**
     * 从Resource中加载BeanDefinition
     *
     * @param beanFactory 新建的BeanFactory
     */
    public abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }
}
