package xyz.cherish.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常拦截器
 */
public class ThrowsAdviceInterceptor implements MethodInterceptor {
    private static final String AFTER_THROWING = "afterThrowing";
    private static final Logger logger = LoggerFactory.getLogger(ThrowsAdviceInterceptor.class);
    private final Object throwAdvice;
    private final Map<Class<?>, Method> exceptionHandlerMap = new HashMap<>();

    public ThrowsAdviceInterceptor(Object throwAdvice) {
        this.throwAdvice = throwAdvice;

        Method[] methods = throwAdvice.getClass().getMethods();
        for (Method method : methods) {
            if (AFTER_THROWING.equals(method.getName()) &&
                    (method.getParameterCount() == 1 || method.getParameterCount() == 4)
            ) {
                Class<?> throwableParams = method.getParameterTypes()[method.getParameterCount() - 1];
                if (Throwable.class.isAssignableFrom(throwableParams)) {
                    exceptionHandlerMap.put(throwableParams, method);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Found Exception handler method on throws advice: " + method);
                    }
                }
            }
        }
    }

    public int getHandlerMethodCount() {
        return this.exceptionHandlerMap.size();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable ex) {
            Method handlerMethod;
            if (ex instanceof InvocationTargetException targetException) {
                handlerMethod = getExceptionHandler(targetException.getTargetException());
                if (handlerMethod != null) {
                    invokeHandlerMethod(invocation, targetException.getTargetException(), handlerMethod);
                }
            } else {
                handlerMethod = getExceptionHandler(ex);
                if (handlerMethod != null) {
                    invokeHandlerMethod(invocation, ex, handlerMethod);
                }
            }
            throw ex;
        }
    }

    private void invokeHandlerMethod(MethodInvocation invocation, Throwable ex, Method method) throws Throwable {
        Object[] handlerArgs;
        if (method.getParameterCount() == 1) {
            handlerArgs = new Object[]{ex};
        } else {
            handlerArgs = new Object[]{invocation.getMethod(), invocation.getArguments(), invocation.getThis(), ex};
        }
        try {
            method.setAccessible(true);
            method.invoke(throwAdvice, handlerArgs);
        } catch (InvocationTargetException targetException) {
            throw targetException.getTargetException();
        }
    }

    private Method getExceptionHandler(Throwable ex) {
        Class<?> exceptionClass = ex.getClass();
        if (logger.isTraceEnabled()) {
            logger.trace("Trying to find handler for exception of type [" + exceptionClass.getName() + "]");
        }
        Method handler = this.exceptionHandlerMap.get(exceptionClass);
        while (handler == null && exceptionClass != Throwable.class) {
            exceptionClass = exceptionClass.getSuperclass();
            handler = exceptionHandlerMap.get(exceptionClass);
        }
        if (handler != null && logger.isTraceEnabled()) {
            logger.trace("Found handler for exception of type [" + exceptionClass.getName() + "]: " + handler);
        }
        return handler;
    }
}
