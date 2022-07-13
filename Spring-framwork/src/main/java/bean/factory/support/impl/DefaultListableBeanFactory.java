package bean.factory.support.impl;

import bean.BeansException;
import bean.factory.ConfigurableListableBeanFactory;
import bean.factory.support.interfaces.BeanDefinitionRegistry;
import bean.factory.config.impl.BeanDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangpengkai
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 这里主要是记录内部人工导入的配置类对象
     */
    private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    public void registerManualSingleton(String beanName, Object bean) {
        if (!manualSingletonNames.contains(beanName)) {
            manualSingletonNames.add(beanName);
            registerSingleton(beanName, bean);
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                try {
                    result.put(beanName, (T) getBean(beanName));
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        });

        // 开始寻找到手工创建的类中的属性
        manualSingletonNames.forEach(manualName->{
            Object bean = getSingleton(manualName);
            Class beanClass = bean.getClass();
            if (type.isAssignableFrom(beanClass)) {
                try {
                    result.put(manualName, (T)bean);
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        });

        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }

        for (String manualName : manualSingletonNames) {
            Object bean = getSingleton(manualName);
            if (requiredType.isAssignableFrom(bean.getClass())) {
                return (T) bean;
            }
        }

        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }

}
