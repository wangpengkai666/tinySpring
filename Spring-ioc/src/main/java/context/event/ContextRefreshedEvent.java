package context.event;

/**
 * @author wangpengkai
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    /**
     * the constructor of the event
     *
     * @param object
     */
    public ContextRefreshedEvent(Object object) {
        super(object);
    }
}
