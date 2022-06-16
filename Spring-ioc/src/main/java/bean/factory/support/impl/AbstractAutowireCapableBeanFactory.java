package bean.factory.support.impl;

import bean.PropertyValue;
import bean.PropertyValues;
import bean.factory.config.impl.BeanReference;
import cn.hutool.core.bean.BeanUtil;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.support.interfaces.InstantiationStrategy;
import lombok.Data;

import java.lang.reflect.Constructor;

/**
 * @author wangpengkai
 */
@Data
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    /**
     * strategy to create an instance
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
            // apply properties for the bean
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // add to singleton cache
        addSingleton(beanName, bean);
        return bean;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws Exception {
        if (args == null) return createBean(beanName, beanDefinition);
        Class<?> beanClass = beanDefinition.getBeanClass();

        // find the suit constructor
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> ctor : constructors) {
            Class<?>[] parameterTypes = ctor.getParameterTypes();
            if (parameterTypes.length == args.length) {
                // start the strategy
                Object bean = instantiationStrategy.instantiate(beanDefinition, beanName, ctor, args);
                // apply properties for the bean
                applyPropertyValues(beanName, bean, beanDefinition);
                addSingleton(beanName, bean);
                return bean;
            }
        }

        return null;
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(propertyValues==null) return;
        for(PropertyValue propertyValue:propertyValues.getPropertyValue()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference) {
                // instance a new object
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }
}
