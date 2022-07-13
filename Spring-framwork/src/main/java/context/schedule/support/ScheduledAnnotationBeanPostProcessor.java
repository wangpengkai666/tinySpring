package context.schedule.support;

import bean.BeansException;
import bean.factory.config.impl.BeanPostProcessor;
import context.ApplicationContext;
import context.ApplicationContextAware;
import context.ApplicationListener;
import context.event.ContextRefreshedEvent;
import context.schedule.ScheduledMethodRunnable;
import context.schedule.annotation.Scheduled;
import context.schedule.config.ScheduledTaskRegistrar;
import context.schedule.support.task.CronTask;
import context.schedule.support.trigger.CronTrigger;
import utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author wangpengkai
 */
public class ScheduledAnnotationBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>, BeanPostProcessor,
        ApplicationContextAware {

    private ScheduledTaskRegistrar scheduledTaskRegistrar;
    
    private ApplicationContext applicationContext;

    ScheduledAnnotationBeanPostProcessor() {
        scheduledTaskRegistrar = new ScheduledTaskRegistrar();
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 1.对于类的代理情况进行分析
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        // 2.寻找到类中具有scheduled注解的方法，并且获取其中的cron属性
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Scheduled scheduled = method.getAnnotation(Scheduled.class);
            if (scheduled != null) {
                String cronExpression = scheduled.cron();
                if (cronExpression != null && !cronExpression.isEmpty()) {
                    // 3.进行cronTask的注册
                    processScheduled(scheduled, method, bean);
                }
            }
        }

        return bean;
    }

    /**
     * 对于scheduled注解的处理，核心就是封装task任务，完成注册，我们这里只是处理cron任务的逻辑
     * @param scheduled
     * @param method
     * @param bean
     */
    protected void processScheduled(Scheduled scheduled, Method method, Object bean) {
        Runnable runnable = createRunnable(method, bean);

        String cron = scheduled.cron();
        if (cron != null && !cron.isEmpty()) {
            TimeZone zone = TimeZone.getDefault();
            this.scheduledTaskRegistrar.scheduleCronTask(new CronTask(runnable, new CronTrigger(cron, zone)));
        }
    }

    protected Runnable createRunnable(Method method, Object bean) {
        return new ScheduledMethodRunnable(bean, method);
    }

    /**
     * spring容器refresh事件发生之后的处理，开始进行对应的定时任务逻辑
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext() == this.applicationContext) {
            finishRegistration();
        }
    }

    private void finishRegistration() {
        this.scheduledTaskRegistrar.afterPropertiesSet();
    }
}
