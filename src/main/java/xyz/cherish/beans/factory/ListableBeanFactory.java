package xyz.cherish.beans.factory;

import xyz.cherish.exception.BeansException;

import java.util.Map;

/**
 * BeanFactory列表板
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 返回指定类型的所有Bean
     * @param clazz 类型
     * @return 类型对应Bean的Map，键为Bean名称，值为对应Class对象
     * @param <T> 类型
     * @throws BeansException 无法找到对应Bean类型报错
     */
    <T> Map<String, T> getBeansOfType(Class<T> clazz) throws BeansException;
    String[] getBeanDefinitionNames();
}
