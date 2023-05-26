package xyz.cherish.aop.framework;

import java.lang.reflect.Method;

/**
 * 前置切面
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
