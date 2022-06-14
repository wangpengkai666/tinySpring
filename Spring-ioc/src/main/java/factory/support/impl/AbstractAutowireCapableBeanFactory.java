package factory.support.impl;

import factory.config.impl.BeanDefinition;

/**
 * @author wangpengkai
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // add to singleton cache
        addSingleton(beanName, bean);
        return bean;
    }
}
