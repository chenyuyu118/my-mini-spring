package xyz.cherish.test.aop;

import org.junit.Test;
import xyz.cherish.aop.AdvisedSupport;
import xyz.cherish.aop.TargetSource;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcut;
import xyz.cherish.aop.framework.JdkDynamicAopProxy;
import xyz.cherish.test.service.AnotherService;
import xyz.cherish.test.service.AnotherServiceImpl;
import xyz.cherish.test.service.SimpleService;
import xyz.cherish.test.service.SimpleServiceImpl;

public class JdkDynamicProxyTest {

    @Test
    public void testJdkProxy() {
        AdvisedSupport support = new AdvisedSupport();
        SimpleServiceImpl service = new SimpleServiceImpl();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* xyz.cherish.test.service.*.*(..))");

        // 方法匹配器
        support.setMethodMatcher(pointcut.getMethodMatcher());

        // 设置源对象
        support.setTargetSource(new TargetSource(service));

        // 设置增强方法
        support.setMethodInterceptor(methodInvocation -> {
            System.out.println("before " + methodInvocation.getMethod().getName());
            Object proceeded = methodInvocation.proceed();
            System.out.println("after result " + proceeded);
            return proceeded;
        });

        JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(support);

        // 获取增强对象
        SimpleService proxyService = (SimpleService) proxy.getProxy();

        // 测试增强对象
        proxyService.hello();

        support.setTargetSource(new TargetSource(new AnotherServiceImpl()));
        JdkDynamicAopProxy proxy1 = new JdkDynamicAopProxy(support);
        AnotherService anotherService = (AnotherService) proxy1.getProxy();

        // 新的增强
        anotherService.byeBye();
    }
}
