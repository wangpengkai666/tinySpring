package bean.factory.support.impl;

import bean.factory.support.interfaces.BeanDefinitionReader;
import bean.factory.support.interfaces.BeanDefinitionRegistry;
import core.io.DefaultResourceLoader;
import core.io.ResourceLoader;
import lombok.Data;

/**
 * @author wangpengkai
 */
@Data
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    private ResourceLoader resourceLoader;

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.beanDefinitionRegistry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }


    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this(beanDefinitionRegistry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry,ResourceLoader resourceLoader) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.resourceLoader = resourceLoader;
    }
}
