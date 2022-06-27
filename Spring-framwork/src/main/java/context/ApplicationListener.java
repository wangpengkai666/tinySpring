package context;

import java.util.EventListener;

/**
 * @author wangpengkai
 */
public interface ApplicationListener<T extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(T event);
}
