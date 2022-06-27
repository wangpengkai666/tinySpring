package context;

import context.event.ApplicationContextEvent;

/**
 * @author wangpengkai
 */
public abstract class ApplicationEvent extends EventObject{
    /**
     * the constructor of the event
     * @param object
     */
    public ApplicationEvent(Object object) {
        super(object);
    }
}
