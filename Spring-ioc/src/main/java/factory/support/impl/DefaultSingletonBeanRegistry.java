package factory.support.impl;

import factory.config.interfaces.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangpengkai
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonMap.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonMap.put(beanName, singletonObject);
    }
}
