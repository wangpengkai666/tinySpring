package factory.support.impl;

import factory.config.impl.BeanDefinition;
import factory.support.interfaces.BeanFactory;

/**
 * @author wangpengkai
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        // get object from singleton cache
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }

        // build the definition bean to get an instance
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    /**
     * create Bean and add to cache from name and its definition
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    /**
     * abstract:get beanDefinition from beanName
     *
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);


}
