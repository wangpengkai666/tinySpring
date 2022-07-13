package context.schedule.support.scheduler;

import context.schedule.ReschedulingRunnable;
import context.schedule.support.trigger.Trigger;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author wangpengkai
 */
public class ConcurrentTaskScheduler implements TaskScheduler {
    private ScheduledExecutorService scheduledExecutor;

    public ConcurrentTaskScheduler() {
        super();
        this.scheduledExecutor = initScheduledExecutor(null);
    }

    public ConcurrentTaskScheduler(ScheduledExecutorService scheduledExecutor) {
        this.scheduledExecutor = initScheduledExecutor(scheduledExecutor);
    }

    private ScheduledExecutorService initScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
        if (scheduledExecutor != null) {
            this.scheduledExecutor = scheduledExecutor;
        }

        return this.scheduledExecutor;
    }

    public void setScheduledExecutor(@Nullable ScheduledExecutorService scheduledExecutor) {
        initScheduledExecutor(scheduledExecutor);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        try {
            return new ReschedulingRunnable(task, trigger, this.scheduledExecutor).schedule();
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return this.scheduledExecutor.schedule(task, initialDelay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + this.scheduledExecutor + "] did not accept task: " + task, ex);
        }
    }
}
