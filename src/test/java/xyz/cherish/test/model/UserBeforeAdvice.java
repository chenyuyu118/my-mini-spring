package xyz.cherish.test.model;

import xyz.cherish.aop.framework.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class UserBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before");
    }
}
