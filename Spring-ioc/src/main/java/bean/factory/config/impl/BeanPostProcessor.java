package bean.factory.config.impl;

import bean.BeansException;

/**
 * Factory hook that allows for custom modification of new bean instances,e.g. checking
 * for marker interfaces or wrapping them with proxies.
 * @author wangpengkai
 */
public interface BeanPostProcessor {
    /**
     * process bean before init method
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * process bean after init method
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
