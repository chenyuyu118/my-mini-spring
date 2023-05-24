package xyz.cherish.core.strategy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.cherish.core.beans.BeanDefinition;
import xyz.cherish.model.User;

import java.lang.reflect.Method;

public class CglibSubclassStrategyTest {

    public static Logger logger = LoggerFactory.getLogger(CglibSubclassStrategyTest.class);
    @Test
    public void testCglib() {
        User u = new User("lily");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(User.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                logger.info("object class: " + object.getClass().getName());
                logger.info("method name : " + method.getName());
                logger.info("method proxy: " + methodProxy.getClass().getName());
                return methodProxy.invokeSuper(object, args);
            }
        });
        User enhancerUser = (User)enhancer.create();
        enhancerUser.setName("hello");
        System.out.println(enhancerUser.getName());
    }

    @Test
    public void testCglibSubclassInstitionStrategy() throws Exception {
        CglibSubclassingInstantiationStrategy cglibSubclassingInstantiationStrategy = new CglibSubclassingInstantiationStrategy();
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(User.class);
        User instantiate = (User) cglibSubclassingInstantiationStrategy.instantiate(beanDefinition);
        instantiate.setName("hello");
        System.out.println(instantiate.getName());
    }
}
