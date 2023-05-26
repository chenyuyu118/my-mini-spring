package xyz.cherish.aop;

import org.aopalliance.intercept.MethodInterceptor;


/**
 * 动态代理的支持，包含代理的源，代理的实际执行器
 * 并且通过MethodMatcher可以来匹配我们需要方法执行部分
 */
public class AdvisedSupport {

    private boolean proxyTargetClass = false; // 是否为cglib动态代理
    private TargetSource targetSource; // 目标类的源
    private MethodInterceptor methodInterceptor; // 目标类的拦截器
    private MethodMatcher methodMatcher; // 代理的方法匹配器

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
