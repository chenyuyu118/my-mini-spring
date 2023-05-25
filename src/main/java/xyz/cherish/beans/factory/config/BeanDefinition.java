package xyz.cherish.beans.factory.config;

import xyz.cherish.beans.PropertyValues;

public class BeanDefinition {
    private Class<?> beanClass;
    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    private String scope = SCOPE_SINGLETON;
    private boolean singleton = true;
    private boolean prototype = false;

    public void setScope(String scope) {
        this.scope = scope == null ? SCOPE_SINGLETON : scope;
        prototype = SCOPE_PROTOTYPE.equals(scope);
        singleton = !prototype;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

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
