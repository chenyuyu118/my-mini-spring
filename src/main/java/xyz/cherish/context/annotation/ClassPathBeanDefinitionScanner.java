package xyz.cherish.context.annotation;

import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.support.BeanDefinitionRegistry;
import xyz.cherish.stereotype.Component;
import xyz.cherish.utils.StrUtils;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "xyz.cherish." +
            "context.annotation.internalAutowiredAnnotationProcessor"; // 自动装配后置处理器的名称
    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                String scope = resolveBeanScope(candidateComponent);
                if (!scope.isEmpty()) {
                    candidateComponent.setScope(scope);
                }
                String beanName = determineBeanName(candidateComponent);
                registry.registerBeanDefinition(beanName, candidateComponent);
            }
        }

        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (value.isEmpty()) {
            value = StrUtils.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return "";
    }
}
