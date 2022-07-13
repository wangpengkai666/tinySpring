package context.support;


import bean.BeansException;
import bean.factory.ConfigurableListableBeanFactory;
import bean.factory.config.impl.ApplicationContextAwareProcessor;
import bean.factory.config.impl.BeanFactoryPostProcessor;
import bean.factory.config.impl.BeanPostProcessor;
import bean.factory.support.impl.DefaultListableBeanFactory;
import context.ApplicationEvent;
import context.ApplicationListener;
import context.ConfigurableApplicationContext;
import context.event.ApplicationEventMulticaster;
import context.event.ContextClosedEvent;
import context.event.ContextRefreshedEvent;
import context.event.SimpleApplicationEventMulticaster;
import context.schedule.support.ScheduledAnnotationBeanPostProcessor;
import core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @author wangpengkai
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicati onEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * provide a default method to refresh ioc container
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3.预创建spring系统中预先配置的bean对象
        preInitInnnerConfigBean(beanFactory);

        // 4.构建一个处理器，方便后续application容器对象的感知
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 5. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 6. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 7. 初始化事件发布者
        initApplicationEventMulticaster();

        // 8. 注册事件监听器
        registerListeners();

        // 9. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 10. 发布容器刷新完成事件
        finishRefresh();
    }

    private void preInitInnnerConfigBean(ConfigurableListableBeanFactory beanFactory) {
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }


    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }


    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener applicationListener:applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 预先加载系统内部创建的处理类
        registerInnerBeanPostProcessors(beanFactory);

        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void registerInnerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        publishEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }
    
}
