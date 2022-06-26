package context;

/**
 * @author wangpengkai
 */
public class EventObject {
    private Object event;

    public EventObject(Object object) {
        this.event = object;
    }

    public Object getSource() {
        return event;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + event + "]";
    }
}
