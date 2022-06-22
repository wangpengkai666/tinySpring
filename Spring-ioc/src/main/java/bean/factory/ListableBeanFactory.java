package bean.factory;

import bean.BeansException;

import java.util.Map;

/**
 * @author wangpengkai
 */
public interface ListableBeanFactory extends BeanFactory{
    /**
     * get bean by class
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * Return the names of all beans defined in this registry.
     * @return
     *
     */
    String[] getBeanDefinitionNames();
}
