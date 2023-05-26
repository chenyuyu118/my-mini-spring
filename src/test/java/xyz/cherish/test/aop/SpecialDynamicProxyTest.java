package xyz.cherish.test.aop;

import org.junit.Before;
import org.junit.Test;
import xyz.cherish.aop.AdvisedSupport;
import xyz.cherish.aop.TargetSource;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcut;
import xyz.cherish.aop.framework.AfterReturningAdvice;
import xyz.cherish.aop.framework.BaseThrowsAdvice;
import xyz.cherish.aop.framework.MethodBeforeAdvice;
import xyz.cherish.aop.framework.ProxyFactory;
import xyz.cherish.aop.framework.adapter.AfterReturningAdviceInterceptor;
import xyz.cherish.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import xyz.cherish.aop.framework.adapter.ThrowsAdviceInterceptor;
import xyz.cherish.test.service.SimpleService;
import xyz.cherish.test.service.SimpleServiceImpl;

import java.lang.reflect.Method;

public class SpecialDynamicProxyTest {

    private AdvisedSupport support;
    private MethodBeforeAdviceInterceptor before;
    private AfterReturningAdviceInterceptor after;
    private ThrowsAdviceInterceptor exception;

    @Before
    public void setup() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution (* xyz.cherish.test.service.*.*(..))");

        AdvisedSupport support = new AdvisedSupport();
        support.setTargetSource(new TargetSource(new SimpleServiceImpl()));
        support.setMethodMatcher(pointcut.getMethodMatcher());
        before = new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before");
            }
        });
        after = new AfterReturningAdviceInterceptor(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("after");
            }
        });
        exception = new ThrowsAdviceInterceptor(new BaseThrowsAdvice() {
            public void afterThrowing(NoSuchMethodException throwable) {
                System.out.println("exception");
                System.out.println(throwable.getMessage());
            }
        });
        this.support = support;
    }

    @Test
    public void testBeforeAdviceInterceptor() {
        support.setMethodInterceptor(before);
        ProxyFactory proxyFactory = new ProxyFactory(support);
        SimpleService proxy = (SimpleService) proxyFactory.getProxy();
        proxy.hello();
    }

    @Test
    public void testAfterAdviceInterceptor() {
        support.setMethodInterceptor(after);
        ProxyFactory proxyFactory = new ProxyFactory(support);
        SimpleService proxy = (SimpleService) proxyFactory.getProxy();
        proxy.hello();
    }

    @Test
    public void testExceptionInterceptor() throws NoSuchMethodException {
        support.setMethodInterceptor(exception);
        ProxyFactory proxyFactory = new ProxyFactory(support);
        SimpleService proxy = (SimpleService) proxyFactory.getProxy();
        proxy.error();
    }
}
