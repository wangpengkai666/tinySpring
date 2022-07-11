package context.schedule.support;

import bean.BeansException;
import bean.factory.config.impl.BeanPostProcessor;
import context.ApplicationListener;
import context.event.ContextRefreshedEvent;

/**
 * @author wangpengkai
 */
public class ScheduledAnnotationBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>, BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
