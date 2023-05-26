package xyz.cherish.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import xyz.cherish.aop.AdvisedSupport;

import java.lang.reflect.Method;

public class CglibDynamicAopProxy implements AopProxy {

    private final AdvisedSupport advised;

    public CglibDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        /*
        通过CGlib增强获得代理类
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass()); // 超类
        enhancer.setInterfaces(advised.getTargetSource().getTargetClass()); // 超类接口
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised)); // 设置方法回调
        return enhancer.create(); // 创建代理
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport advisedSupport;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }

        /**
         * 回调方法
         *
         * @param obj    被增强的对象
         * @param method 拦截的方法
         * @param args   参数列表
         * @param proxy  used to invoke super (non-intercepted method); may be called 使用来调用超类方法（实际方法）
         * @return 方法正常返回值
         * @throws Throwable 错误
         */
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            // 创建Cglib的方法默认方法调用
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(
                    advisedSupport.getTargetSource().getTarget(), method, args, proxy);
            if (advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())) {
                // 如果符合AOP请求，拦截方法调用，使用拦截器调用实际方法
                return advisedSupport.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }
    }

    // Cglib的方法调用
    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy proxy) {
            super(target, method, arguments); // 初始化方法、对象、参数
            this.methodProxy = proxy; // 默认方法
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments); // 调用实际方法
        }
    }
}
