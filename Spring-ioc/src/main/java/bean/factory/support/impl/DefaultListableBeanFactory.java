package bean.factory.support.impl;

import bean.factory.support.interfaces.BeanDefinitionRegistry;
import bean.factory.config.impl.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangpengkai
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
    Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        try {
            return definitionMap.get(beanName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        definitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return definitionMap.containsKey(beanName);
    }
}
