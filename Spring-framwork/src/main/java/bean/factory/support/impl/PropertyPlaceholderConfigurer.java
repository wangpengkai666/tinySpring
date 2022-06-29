package bean.factory.support.impl;

import bean.BeansException;
import bean.PropertyValue;
import bean.PropertyValues;
import bean.factory.ConfigurableListableBeanFactory;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.config.impl.BeanFactoryPostProcessor;
import bean.factory.config.impl.BeanPostProcessor;
import io.DefaultResourceLoader;
import io.Resource;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author wangpengkai
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * the default placeholder prefix
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * the default placeholder suffix
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    String location;

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 1.load property from location
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(location);
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            throw new BeansException();
        }

        // 2.list all the beanDefinition before init
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            LinkedList<PropertyValue> replaceHolderValues = new LinkedList<>();

            // 3.find all the replaceHolder str and add new value
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                Object value = propertyValue.getValue();
                if (!(value instanceof String)) continue;

                String strVal = (String) value;
                int startInx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                int endInx = strVal.lastIndexOf(DEFAULT_PLACEHOLDER_SUFFIX);

                if (startInx != -1 && endInx != -1 && startInx < endInx) {
                    String replaceStr = strVal.substring(startInx + 2, endInx);
                    Object findVal = properties.get(replaceStr);
                    replaceHolderValues.add(new PropertyValue(propertyValue.getName(), findVal));
                }
            }

            propertyValues.addPropertyValue(replaceHolderValues);
        }
    }
}
