package bean.factory;

import bean.BeansException;
import bean.factory.config.impl.AutowireCapableBeanFactory;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.config.impl.BeanPostProcessor;
import bean.factory.config.impl.ConfigurableBeanFactory;

/**
 * @author wangpengkai
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * get bean definition
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * pre init before other beans
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;

    @Override
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
