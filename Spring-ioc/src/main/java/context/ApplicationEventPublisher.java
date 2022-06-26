package context;

/**
 * @author wangpengkai
 */
public interface ApplicationEventPublisher {

    /**
     * event. Events may be framework events (such as RequestHandledEven
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
