package bean.factory.config.impl;

import bean.BeansException;
import bean.factory.ConfigurableListableBeanFactory;

import java.io.IOException;

/**
 * @author wangpengkai
 */
public interface BeanFactoryPostProcessor {
    /**
     * Allows for custom modification of an application context's bean definitions,
     * adapting the bean property values of the context's underlying bean factory
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws
            BeansException;
}
