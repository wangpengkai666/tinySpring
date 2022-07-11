package context.schedule.support.context;

import java.util.Date;

/**
 * @author wangpengkai
 */
public class SimpleTriggerContext implements TriggerContext{
    @Override
    public Date lastScheduledExecutionTime() {
        return null;
    }

    @Override
    public Date lastActualExecutionTime() {
        return null;
    }

    @Override
    public Date lastCompletionTime() {
        return null;
    }
}
