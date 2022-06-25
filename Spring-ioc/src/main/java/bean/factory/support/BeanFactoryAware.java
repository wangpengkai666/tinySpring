package bean.factory.support;

import bean.BeansException;
import bean.factory.BeanFactory;

/**
 * @author wangpengkai
 */
public interface BeanFactoryAware extends Aware{
    /**
     * set the bean factory when register
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
