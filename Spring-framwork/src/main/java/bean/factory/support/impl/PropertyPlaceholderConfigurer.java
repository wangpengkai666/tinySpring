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
import utils.StringValueResolver;

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

        // 向容器中添加字符串解析器，供解析@Value注解使用
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    private String resolvePlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }
        return buffer.toString();
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }

    }
}
