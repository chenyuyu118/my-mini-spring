package xyz.cherish.utils;

import xyz.cherish.exception.BeansException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Bean的一些操作
 */
public class BeanUtils {

    public static void setFileValue(Object bean, String filedName, Object value) {
        if (isArray(bean)) {
            setArrayIndexValue(bean, filedName, value);
        } else {
            try {
                setSimpleFiledValue(bean, filedName, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 设置数组指定索引值
     *
     * @param bean  数组对象
     * @param index 索引
     * @param value 值
     */
    private static void setArrayIndexValue(Object bean, String index, Object value) {
        int length = Array.getLength(bean);
        try {
            int i = Integer.parseInt(index);
            if (length > i) {
                Array.set(bean, i, value);
            }
        } catch (NumberFormatException ex) {
            throw new BeansException("Can't set array value", ex);
        }
    }

    /**
     * 通过反射设置bean的属性值辅助类，简单设置字段
     *
     * @param bean      bean对选哪个
     * @param filedName 字段名称
     * @param value     值
     * @throws NoSuchFieldException   没有字段
     * @throws IllegalAccessException 没有访问权限
     */
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
    }


    public static boolean isArray(Object bean) {
        return bean.getClass().isArray();
    }
}
