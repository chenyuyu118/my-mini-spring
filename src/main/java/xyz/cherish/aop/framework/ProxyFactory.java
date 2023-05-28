package xyz.cherish.aop.framework;

import xyz.cherish.aop.AdvisedSupport;

/**
 * 代理工厂，生产代理实例
 */
public class ProxyFactory {

    private final AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        AopProxy proxy = null;
        if (advisedSupport.isProxyTargetClass()) {
            proxy = new CglibDynamicAopProxy(advisedSupport);
        } else {
            proxy = new JdkDynamicAopProxy(advisedSupport);
        }
        return proxy;
    }
}
