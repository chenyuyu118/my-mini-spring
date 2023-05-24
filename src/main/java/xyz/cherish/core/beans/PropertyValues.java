package xyz.cherish.core.beans;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PropertyValues {
    private Map<String, PropertyValue> propertyValueMap;

    public void addProperty(String key, Object value) {
        PropertyValue oldProperty;
        if ((oldProperty = propertyValueMap.get(key)) == null) {
            propertyValueMap.put(key, new PropertyValue(key, value));
        } else {
            oldProperty.setValue(value);
        }
    }

    public PropertyValues() {
        propertyValueMap = new HashMap<>();
    }

    public PropertyValue getProperty(String key) {
        return propertyValueMap.get(key);
    }

    public Iterator<PropertyValue> getProperties() {
        return propertyValueMap.values().iterator();
    }
}
