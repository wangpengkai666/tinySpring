package context.annotation;

import bean.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.support.interfaces.BeanDefinitionRegistry;
import cn.hutool.core.util.ClassUtil;
import stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wangpengkai
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    private BeanDefinitionRegistry registry;

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidateComponents) {
                // resolve the type scope of the definition
                String scopeType = resolveBeanScope(beanDefinition);
                if (scopeType != null && !scopeType.isEmpty()) {
                    beanDefinition.setScope(scopeType);
                }
                // resolve the real beanDefinitionName
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

        // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
        registry.registerBeanDefinition("bean.factory.support.impl.AbstractAutowireCapableBeanFactory", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Scope scopeAnnotation = (Scope) beanDefinition.getBeanClass().getAnnotation(Scope.class);
        if (scopeAnnotation == null) {
            return null;
        }
        return scopeAnnotation.value();
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (value == null || value.isEmpty()) return "";
        else return value.toLowerCase();
    }

}
