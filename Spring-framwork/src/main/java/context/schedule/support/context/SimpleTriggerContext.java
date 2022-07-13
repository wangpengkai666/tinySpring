package context.schedule.support.context;

import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * @author wangpengkai
 */
public class SimpleTriggerContext implements TriggerContext{

    private volatile Date lastScheduledExecutionTime;

    private volatile Date lastActualExecutionTime;

    private volatile Date lastCompletionTime;


    /**
     * Create a SimpleTriggerContext with all time values set to {@code null}.
     */
    public SimpleTriggerContext() {
    }

    public SimpleTriggerContext(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
        this.lastActualExecutionTime = lastActualExecutionTime;
        this.lastCompletionTime = lastCompletionTime;
    }

    public void update(Date lastScheduledExecutionTime, Date lastActualExecutionTime, Date lastCompletionTime) {
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
        this.lastActualExecutionTime = lastActualExecutionTime;
        this.lastCompletionTime = lastCompletionTime;
    }


    @Override
    public Date lastScheduledExecutionTime() {
        return this.lastScheduledExecutionTime;
    }

    @Override
    public Date lastActualExecutionTime() {
        return this.lastActualExecutionTime;
    }

    @Override
    public Date lastCompletionTime() {
        return this.lastCompletionTime;
    }
}
