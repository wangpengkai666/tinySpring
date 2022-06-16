package bean.factory.support.impl;

import bean.factory.config.impl.BeanDefinition;
import bean.factory.support.interfaces.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @author wangpengkai
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args)
            throws Exception {
        if (ctor != null) {
            return ctor.newInstance(args);
        } else {
            Class beanClass = beanDefinition.getBeanClass();
            if (args != null) {
                Class<?> parameterTypes = Arrays.stream(args).getClass();
                return beanClass.getDeclaredConstructor(parameterTypes).newInstance();
            }

            return beanClass.getDeclaredConstructor().newInstance();
        }
    }
}
