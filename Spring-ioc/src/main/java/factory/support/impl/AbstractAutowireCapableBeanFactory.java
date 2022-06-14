package factory.support.impl;

import factory.config.impl.BeanDefinition;
import factory.support.interfaces.InstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * @author wangpengkai
 */
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
                Object instantiate = instantiationStrategy.instantiate(beanDefinition, beanName, ctor, args);
                addSingleton(beanName, instantiate);
                return instantiate;
            }
        }

        return null;
    }
}
