package factory.support.interfaces;

import factory.config.impl.BeanDefinition;

/**
 * @author wangpengkai
 */
public interface BeanDefinitionRegistry {
    /**
     * register a bean-definition
     * @param beanName
     * @param beanDefinition
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
