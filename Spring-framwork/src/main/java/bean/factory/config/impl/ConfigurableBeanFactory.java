package bean.factory.config.impl;

import bean.factory.HierarchicalBeanFactory;
import bean.factory.config.interfaces.SingletonBeanRegistry;
import core.convert.ConversionService;
import utils.StringValueResolver;

/**
 * @author wangpengkai
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * add bean post processor to the container
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * destroy the singleton initiate object
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param valueResolver the String resolver to apply to embedded values
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     * @param value the value to resolve
     * @return the resolved value (may be the original value as-is)
     * @since 3.0
     */
    String resolveEmbeddedValue(String value);

    /**
     * Specify a Spring 3.0 ConversionService to use for converting
     * property values, as an alternative to JavaBeans PropertyEditors.
     */
    void setConversionService(ConversionService conversionService);

    /**
     * Return the associated ConversionService, if any.
     * @since 3.0
     */
    ConversionService getConversionService();
}
