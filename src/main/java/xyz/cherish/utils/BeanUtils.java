package xyz.cherish.utils;

import xyz.cherish.exception.BeansException;

import java.lang.reflect.Field;

/**
 * Bean的一些操作
 */
public class BeanUtils {
    
    public static void setFileValue(Object bean, String filedName, Object value) throws NoSuchFieldException, IllegalAccessException {
        if (!isArray(bean)) {
            setSimpleFiledValue(bean, filedName, value);
        }
    }

    private static void setSimpleFiledValue(Object bean, String filedName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = bean.getClass().getField(filedName);
        field.set(bean, value);
    }


    public static boolean isArray(Object bean) {
        return bean.getClass().isArray();
    }
}
