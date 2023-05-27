package xyz.cherish.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import xyz.cherish.aop.*;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcutAdvisor;
import xyz.cherish.aop.framework.ProxyFactory;
import xyz.cherish.beans.factory.BeanFactory;
import xyz.cherish.beans.factory.BeanFactoryAware;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.InstantiationAwareBeanPostProcessor;
import xyz.cherish.beans.factory.support.DefaultListableBeanFactory;
import xyz.cherish.exception.BeansException;

import java.util.Collection;

/**
 * 自动创建代理的BeanPostProcessor
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    private boolean proxyTargetClass;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            // 如果是基础类型，返回空避免死循环
            return null;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors =
                beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter(); // 获取类过滤器过滤需要代理的类
                if (classFilter.matches(beanClass)) {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName); // 获取创建类的bean定义
                    Object bean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition); // 实例化一个类对象

                    AdvisedSupport support = new AdvisedSupport(); // 创建对应类支持
                    support.setTargetSource(new TargetSource(bean));
                    support.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    support.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    support.setProxyTargetClass(isProxyTargetClass());
                    return new ProxyFactory(support).getProxy();  // 返回对应代理类
                }
            }
        } catch (Exception e) {
            throw new BeansException("Error create proxy bean for: " + beanName, e);
        }
        return null;
    }

    public boolean isProxyTargetClass() {
        return this.proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }
}
