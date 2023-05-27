package xyz.cherish.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import xyz.cherish.aop.framework.MethodBeforeAdvice;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice; // 前置方法对象

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        advice.before(invocation.getMethod(),
                invocation.getArguments(),
                invocation.getThis()); // 执行前置方法
        return invocation.proceed();
    }
}
