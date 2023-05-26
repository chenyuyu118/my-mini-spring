package xyz.cherish.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import xyz.cherish.aop.AdvisedSupport;
import xyz.cherish.aop.TargetSource;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcut;
import xyz.cherish.aop.framework.CglibDynamicAopProxy;
import xyz.cherish.test.service.AnotherServiceImpl;

public class CglibDynamicProxyTest {
    @Test
    public void testCglibDynamicProxy() {
        AdvisedSupport support = new AdvisedSupport();
        support.setTargetSource(new TargetSource(new AnotherServiceImpl()));
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution (* xyz.cherish.test.service.*.*(..))");
        support.setMethodMatcher(pointcut.getMethodMatcher());
        support.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("before: " + invocation.getMethod().getName());
                Object proceeded = invocation.proceed();
                System.out.println("after: " + proceeded);
                return proceeded;
            }
        });
        CglibDynamicAopProxy cglibDynamicAopProxy = new CglibDynamicAopProxy(support);
        AnotherServiceImpl anotherService = (AnotherServiceImpl) cglibDynamicAopProxy.getProxy();
        anotherService.byeBye();
    }
}
