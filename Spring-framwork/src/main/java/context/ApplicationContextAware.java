package context;

import bean.BeansException;
import bean.factory.support.Aware;

/**
 * Interface to be implemented by any object that wishes to be notifiedof the {@link ApplicationContext} that it runs in.
 * @author wangpengkai
 */
public interface ApplicationContextAware extends Aware {
    /**
     * set the own application
     * @param applicationContext
     * @throws BeansException
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
