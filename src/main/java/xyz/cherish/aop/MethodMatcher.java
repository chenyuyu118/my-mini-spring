package xyz.cherish.aop;

import java.lang.reflect.Method;

/**
 * 验证方法是否匹配
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
