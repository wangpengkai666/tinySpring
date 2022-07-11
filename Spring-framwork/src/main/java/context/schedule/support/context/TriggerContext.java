package context.schedule.support.context;

import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * @author wangpengkai
 */
public interface TriggerContext {
    /**
     * Return the last <i>scheduled</i> execution time of the task,
     * or {@code null} if not scheduled before.
     */
    @Nullable
    Date lastScheduledExecutionTime();

    /**
     * Return the last <i>actual</i> execution time of the task,
     * or {@code null} if not scheduled before.
     */
    @Nullable
    Date lastActualExecutionTime();

    /**
     * Return the last completion time of the task,
     * or {@code null} if not scheduled before.
     */
    @Nullable
    Date lastCompletionTime();
}
