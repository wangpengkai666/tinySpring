package bean.factory.support.interfaces;

import bean.BeansException;
import io.Resource;
import io.ResourceLoader;

/**
 * @author wangpengkai
 */
public interface BeanDefinitionReader {
    /**
     * register the beanDefinition
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * the loader to get resource
     * @return
     */
    ResourceLoader getResourceLoader();

    /**
     * load one
     * @param resource
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * load list
     * @param resources
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource... resources) throws BeansException;

    /**
     * load from String
     * @param location
     * @throws BeansException
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * load from Strings
     * @param locations
     * @throws BeansException
     */
    void loadBeanDefinitions(String... locations) throws BeansException;
}
