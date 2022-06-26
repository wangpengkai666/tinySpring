package context.event;

/**
 * @author wangpengkai
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    /**
     * the constructor of the event
     *
     * @param object
     */
    public ContextClosedEvent(Object object) {
        super(object);
    }
}
