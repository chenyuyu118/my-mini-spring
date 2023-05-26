package xyz.cherish.aop;

public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
