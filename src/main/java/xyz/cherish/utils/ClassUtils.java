package xyz.cherish.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class ClassUtils {

    public static Method getPublicMethod(Object obj, String methodName, Class<?>... params) throws NoSuchMethodException {
        return obj.getClass().getMethod(methodName, params);
    }

    public static Set<Class<?>> scanPackageByAnnotation(String basePackage, Class<? extends Annotation> annotationClass) {
        return new ClassScanner(basePackage, aClass -> aClass.isAnnotationPresent(annotationClass)).scan();
    }
}
