package bean.factory.support.interfaces;

import bean.factory.config.impl.BeanDefinition;

/**
 * @author wangpengkai
 */
public interface BeanDefinitionRegistry {
    /**
     * register a bean-definition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * valid if has registered the beanDefinition
     * @param beanName
     * @return boolean
     */
    boolean containsBeanDefinition(String beanName);
}
