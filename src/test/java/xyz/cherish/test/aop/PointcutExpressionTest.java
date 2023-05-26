package xyz.cherish.test.aop;

import org.junit.Test;
import xyz.cherish.aop.aspectj.AspectJExpressionPointcut;
import xyz.cherish.test.model.SimpleService;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class PointcutExpressionTest {

    @Test
    public void testPointcutExpress() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* xyz.cherish.test.model.SimpleService.sayHello(..))");
        Class<SimpleService> simpleServiceClass = SimpleService.class;
        Method sayHelloMethod = simpleServiceClass.getMethod("sayHello");
        Method anotherMethod = simpleServiceClass.getMethod("getApplicationContext");

        assertThat(pointcut.matches(simpleServiceClass)).isTrue();
        assertThat(pointcut.matches(sayHelloMethod, simpleServiceClass)).isTrue();
        assertThat(pointcut.matches(anotherMethod, simpleServiceClass)).isFalse();
    }
}
