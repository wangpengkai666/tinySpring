package context.event;

import context.ApplicationEvent;
import context.ApplicationListener;

/**
 * @author wangpengkai
 */
public interface ApplicationEventMulticaster {
    /**
     * Add a listener to be notified of all events.
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners.
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
