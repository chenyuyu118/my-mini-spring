package xyz.cherish.test.aop;

import org.junit.Before;
import org.junit.Test;
import xyz.cherish.aop.AdvisedSupport;
import xyz.cherish.aop.TargetSource;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcut;
import xyz.cherish.aop.framework.ProxyFactory;
import xyz.cherish.test.service.SimpleService;
import xyz.cherish.test.service.SimpleServiceImpl;

public class ProxyFactoryTest {
    private AdvisedSupport support;

    @Before
    public void setup() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution (* xyz.cherish.test.service.*.*(..))");

        AdvisedSupport support = new AdvisedSupport();
        support.setTargetSource(new TargetSource(new SimpleServiceImpl()));
        support.setMethodMatcher(pointcut.getMethodMatcher());
        support.setMethodInterceptor(invocation -> {
            System.out.println("before: " + invocation.getMethod().getName());
            Object proceeded = invocation.proceed();
            System.out.println("after: " + proceeded);
            return proceeded;
        });
        this.support = support;
    }

    @Test
    public void testProxyFactory() {
        /*
        使用Cglib生成代理类
         */
        support.setProxyTargetClass(true);
        ProxyFactory proxyFactory = new ProxyFactory(support);
        SimpleService proxy = (SimpleService) proxyFactory.getProxy();
        System.out.println(proxy.getClass().getName());
        proxy.hello();

        /*
        使用jdk动态代理生成代理类
         */
        System.out.println("-".repeat(20));
        support.setProxyTargetClass(false);
        proxyFactory = new ProxyFactory(support);
        proxy = (SimpleService) proxyFactory.getProxy();
        System.out.println(proxy.getClass().getName());
        proxy.hello();
    }
}
