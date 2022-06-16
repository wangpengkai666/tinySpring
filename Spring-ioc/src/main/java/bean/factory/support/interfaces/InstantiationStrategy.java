package bean.factory.support.interfaces;

import bean.factory.config.impl.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author wangpengkai
 */
public interface InstantiationStrategy {
    /**
     * the strategy to instantiate a class
     * @param beanDefinition
     * @param beanName
     * @param ctor
     * @param args
     * @return
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws Exception;
}
