package xyz.cherish.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import xyz.cherish.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk原生动态代理实现
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised; // 扩展功能的支持源

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 执行时以该类未实际代理类，执行时通过advisedSupport获取代理源的匹配字符，
     *
     * @param proxy  代理对象
     * @param method 执行方法
     * @param args   参数
     * @return 方法执行结果
     * @throws Throwable 错误
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            // 判断是否匹配目标类方法
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor(); // 获取增强方法
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(),
                    method,
                    args));
        }
        return method.invoke(advised.getTargetSource().getTarget(), args); // 执行原方法
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }
}
