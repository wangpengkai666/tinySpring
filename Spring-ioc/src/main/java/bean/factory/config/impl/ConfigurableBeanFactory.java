package bean.factory.config.impl;

import bean.factory.HierarchicalBeanFactory;
import bean.factory.config.interfaces.SingletonBeanRegistry;

/**
 * @author wangpengkai
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * add bean post processor to the container
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * destroy the singleton initiate object
     */
    void destroySingletons();
}
