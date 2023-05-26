package xyz.cherish.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import xyz.cherish.aop.AdvisedSupport;
import xyz.cherish.aop.ClassFilter;
import xyz.cherish.aop.TargetSource;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcutAdvisor;
import xyz.cherish.aop.framework.MethodBeforeAdvice;
import xyz.cherish.aop.framework.ProxyFactory;
import xyz.cherish.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import xyz.cherish.test.service.SimpleService;
import xyz.cherish.test.service.SimpleServiceImpl;

import java.lang.reflect.Method;

public class AdvisorTest {
    private AspectJExpressionPointcutAdvisor advisor;
    private SimpleServiceImpl service;

    @Before
    public void setup() {
        SimpleServiceImpl service = new SimpleServiceImpl();
        this.service = service;
        String expression = "execution (* xyz.cherish.test.service.*.*(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor(expression);
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {

            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before");
            }
        }));
        this.advisor = advisor;
    }

    @Test
    public void testAdvisor() {
        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(SimpleService.class)) {
            AdvisedSupport support = new AdvisedSupport();
            support.setTargetSource(new TargetSource(service));
            support.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            support.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());

            SimpleService proxy = (SimpleService) new ProxyFactory(support).getProxy();
            proxy.hello();
        }
    }
}
