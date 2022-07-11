package context.schedule.support.trigger;

import context.schedule.support.context.TriggerContext;

import java.util.Date;

/**
 * @author wangpengkai
 */
public interface Trigger {
    /**
     * Determine the next execution time according to the given trigger context.
     * @param triggerContext context object encapsulating last execution times
     * and last completion time
     * @return the next execution time as defined by the trigger,
     * or {@code null} if the trigger won't fire anymore
     */
    Date nextExecutionTime(TriggerContext triggerContext);
}
