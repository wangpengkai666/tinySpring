package bean.factory.support.impl;

import bean.factory.config.impl.BeanDefinition;
import bean.factory.BeanFactory;

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

    @Override
    public Object getBean(String beanName, Object... args) {
        // get object from singleton cache
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }
        // build the definition bean to get an instance
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        try {
            return createBean(beanName, beanDefinition, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
     * create Bean and add to cache from name and its definition with args
     *
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws Exception;

    /**
     * abstract:get beanDefinition from beanName
     *
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);


}
