package xyz.cherish.beans.factory;

import xyz.cherish.beans.PropertyValue;
import xyz.cherish.beans.PropertyValues;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.BeanFactoryPostProcessor;
import xyz.cherish.core.io.DefaultResourceLoader;
import xyz.cherish.core.io.Resource;
import xyz.cherish.exception.BeansException;
import xyz.cherish.utils.StringValueResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理属性Bean的后置处理器
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{(.*)}");
    private String location;

    private static String resolvePropertyValues(Properties properties, String strVal) {
        String value = strVal;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(strVal);
        if (matcher.matches()) {
            /*
            寻找到在Properties中注册的键值对
             */
            String key = matcher.group(1).trim();
            value = properties.getProperty(key);
        }
        return value;
    }

    /**
     * 将property值应用于beanDefinition中
     *
     * @param beanFactory bean定义工厂
     * @param properties  配置的属性值
     */
    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties); // 将属性值应用于beanDefinition中
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties properties = loadProperties(); // 加载所有属性

        // 使用属性替换占位符
        processProperties(beanFactory, properties);

        // 容器中添加字符解析器，将@Value注解解析为属性值
        PlaceholderResolvingStringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        Iterator<PropertyValue> propertyIterator = propertyValues.getProperties();
        while (propertyIterator.hasNext()) {
            // 遍历所有bean定义的属性，寻找需要替换的占位符
            PropertyValue propertyValue = propertyIterator.next();
            if (propertyValue.getValue() instanceof String strVal) {
                String value = resolvePropertyValues(properties, strVal);
                propertyValues.addProperty(propertyValue.getFiled(), value);
            }
        }
    }

    /**
     * 从文件中加载properties
     *
     * @return 属性
     */
    private Properties loadProperties() {
        InputStream is = null;
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            is = resource.getInputStream();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 解析特殊的属性值
     */
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return resolvePropertyValues(properties, strVal);
        }
    }
}
