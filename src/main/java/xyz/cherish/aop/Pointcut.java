package xyz.cherish.aop;

/**
 * 切点
 */
public interface Pointcut {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
