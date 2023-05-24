package xyz.cherish.core.beans;

public class PropertyValue {
    private String filed;
    private Object value;

    public PropertyValue(String key, Object value) {
        this.filed = key;
        this.value = value;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
