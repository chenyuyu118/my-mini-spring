package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.PropertyValues;

public class BeanDefinition {
    private Class<?> beanClass;
    private PropertyValues propertyValues;

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
