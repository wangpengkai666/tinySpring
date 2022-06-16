package bean.factory.config.impl;

import bean.PropertyValues;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * base the enclosure to the class, we can construct the instance by class
 * @author wangpengkai
 */
@Data
@NoArgsConstructor
public class BeanDefinition {
    /**
     * enclosure bean-class
     */
    private Class beanClass;

    /**
     * property-values of bean
     */
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }
}
