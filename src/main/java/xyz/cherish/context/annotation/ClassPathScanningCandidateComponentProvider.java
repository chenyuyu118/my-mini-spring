package xyz.cherish.context.annotation;

import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.stereotype.Component;
import xyz.cherish.utils.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 类路径下扫描注册的BeanDefinition
 */
public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackages) {
        LinkedHashSet<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtils.scanPackageByAnnotation(basePackages, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
