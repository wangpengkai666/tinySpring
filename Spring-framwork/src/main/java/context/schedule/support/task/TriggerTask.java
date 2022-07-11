package context.schedule.support.task;

import context.schedule.support.trigger.Trigger;

/**
 *  {@link Task} implementation defining a {@code Runnable} to be executed
 *  according to a given {@link Trigger}.
 * @author wangpengkai
 */
public class TriggerTask extends Task {
    private Trigger trigger;

    TriggerTask(Runnable runnable, Trigger trigger) {
        super(runnable);
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }
}
