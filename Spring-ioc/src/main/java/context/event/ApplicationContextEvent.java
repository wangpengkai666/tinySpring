package context.event;

import context.ApplicationContext;
import context.ApplicationEvent;

/**
 * @author wangpengkai
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * the constructor of the event
     *
     * @param object
     */
    public ApplicationContextEvent(Object object) {
        super(object);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
