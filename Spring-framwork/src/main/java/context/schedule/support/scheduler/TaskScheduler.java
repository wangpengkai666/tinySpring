package context.schedule.support.scheduler;

import context.schedule.support.trigger.Trigger;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * @author wangpengkai
 */
public interface TaskScheduler {

    ScheduledFuture<?> schedule(Runnable task, Trigger trigger);

    default ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
        return schedule(task, Date.from(startTime));
    }

    ScheduledFuture<?> schedule(Runnable task, Date startTime);
}
