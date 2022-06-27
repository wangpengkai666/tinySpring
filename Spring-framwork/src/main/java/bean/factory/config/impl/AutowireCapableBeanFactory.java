package bean.factory.config.impl;

import bean.BeansException;
import bean.factory.BeanFactory;

/**
 * @author wangpengkai
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * do BeanPostProcessors.postProcessBeforeInitialization
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * do BeanPostProcessors.postProcessorsAfterInitialization
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
