package xyz.cherish.utils;

import java.lang.reflect.Method;

public class ClassUtils {

    public static Method getPublicMethod(Object obj, String methodName, Class<?>... params) throws NoSuchMethodException {
        return obj.getClass().getMethod(methodName, params);
    }
}
