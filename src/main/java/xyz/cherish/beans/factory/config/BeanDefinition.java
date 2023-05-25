package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.PropertyValues;

public class BeanDefinition {
    private Class<?> beanClass;
    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public BeanDefinition() {
        beanClass = Object.class;
        propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public void addPropertyValue(String filedName, Object value) {
        propertyValues.addProperty(filedName, value);
    }
}
