package bean.factory.support.impl;

import bean.BeansException;
import bean.factory.DisposableBean;
import bean.factory.config.impl.BeanPostProcessor;
import bean.factory.config.interfaces.SingletonBeanRegistry;
import bean.factory.support.ObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wangpengkai
 */
public abstract class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * Internal marker for a null singleton object:
     * used as marker value for concurrent Maps (which don't support null values).
     */
    protected static final Object NULL_OBJECT = new Object();

    /**
     * 一级缓存-普通对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 二级缓存-提前暴露对象
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>();

    /**
     * 三级缓存-经由工厂对象来存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        // 先是从一级成熟缓存中拿
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            // 拿不到则考虑二级缓存区域
            singletonObject = earlySingletonObjects.get(beanName);
            // 判断二级缓存中是否有对象，否则去三级缓存中获取
            if (singletonObject == null) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }

        return singletonObject;
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public void destroySingletons() throws BeansException {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception");
            }
        }
    }

    public abstract void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
