package context.annotation;

import bean.factory.config.impl.BeanDefinition;
import bean.factory.support.interfaces.BeanDefinitionRegistry;
import cn.hutool.core.util.ClassUtil;
import stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wangpengkai
 */
public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }

        return candidates;
    }

}
