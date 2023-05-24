package xyz.cherish.utils;

import xyz.cherish.exception.BeansException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Bean的一些操作
 */
public class BeanUtils {
    
    public static void setFileValue(Object bean, String filedName, Object value) throws NoSuchFieldException, IllegalAccessException {
        if (!isArray(bean)) {
            setSimpleFiledValue(bean, filedName, value);
        }
    }

    private static void setSimpleFiledValue(Object bean, String filedName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = bean.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        switch (field.getType().getName()) {
            case "int", "java.lang.Integer" -> field.set(bean, Integer.valueOf(value.toString()));
            case "long", "java.lang.Long" -> field.set(bean, Long.valueOf(value.toString()));
            case "double", "java.lang.Double" -> field.set(bean, Double.valueOf(value.toString()));
            case "float", "java.lang.Float" -> field.set(bean, Float.valueOf(value.toString()));
            case "boolean", "java.lang.Boolean" -> field.set(bean, Boolean.valueOf(value.toString()));
            case "java.lang.String" -> field.set(bean, value.toString());
            case "byte", "java.lang.Byte" -> field.set(bean, Byte.valueOf(value.toString()));
            case "char", "java.lang.Character" ->field.set(bean, value.toString().charAt(0));
            default -> field.set(bean, value);
        }
//        field.set(bean, value);
    }


    public static boolean isArray(Object bean) {
        return bean.getClass().isArray();
    }
}
