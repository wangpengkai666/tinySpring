package context.schedule;

import context.schedule.support.context.SimpleTriggerContext;
import context.schedule.support.trigger.Trigger;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author wangpengkai
 */
public class ReschedulingRunnable implements Runnable, ScheduledFuture<Object> {

    private final Runnable delegate;

    private final Trigger trigger;

    private final SimpleTriggerContext triggerContext = new SimpleTriggerContext();

    private final ScheduledExecutorService executor;

    private ScheduledFuture<?> currentFuture;

    private Date scheduledExecutionTime;

    /**
     * 触发定时任务的锁机制监视器
     */
    private final Object triggerContextMonitor = new Object();

    public ReschedulingRunnable(
            Runnable delegate, Trigger trigger, ScheduledExecutorService executor) {
        this.delegate = delegate;
        this.trigger = trigger;
        this.executor = executor;
    }

    public ScheduledFuture<?> schedule() {
        synchronized (this.triggerContextMonitor) {
            this.scheduledExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
            if (this.scheduledExecutionTime == null) {
                return null;
            }
            long initialDelay = this.scheduledExecutionTime.getTime() - System.currentTimeMillis();
            this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
            return this;
        }
    }

    private ScheduledFuture<?> obtainCurrentFuture() {
        Assert.state(this.currentFuture != null, "No scheduled future");
        return this.currentFuture;
    }

    @Override
    public void run() {
        Date actualExecutionTime = new Date();
        this.delegate.run();
        Date completionTime = new Date();
        synchronized (this.triggerContextMonitor) {
            Assert.state(this.scheduledExecutionTime != null, "No scheduled execution");
            this.triggerContext.update(this.scheduledExecutionTime, actualExecutionTime, completionTime);
            if (!obtainCurrentFuture().isCancelled()) {
                schedule();
            }
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (this.triggerContextMonitor) {
            return obtainCurrentFuture().cancel(mayInterruptIfRunning);
        }
    }

    @Override
    public boolean isCancelled() {
        synchronized (this.triggerContextMonitor) {
            return obtainCurrentFuture().isCancelled();
        }
    }

    @Override
    public boolean isDone() {
        synchronized (this.triggerContextMonitor) {
            return obtainCurrentFuture().isDone();
        }
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = obtainCurrentFuture();
        }
        return curr.get();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = obtainCurrentFuture();
        }
        return curr.get(timeout, unit);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = obtainCurrentFuture();
        }
        return curr.getDelay(unit);
    }

    @Override
    public int compareTo(Delayed other) {
        if (this == other) {
            return 0;
        }
        long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
        return (diff == 0 ? 0 : ((diff < 0)? -1 : 1));
    }
}
