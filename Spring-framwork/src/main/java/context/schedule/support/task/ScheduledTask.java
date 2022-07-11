package context.schedule.support.task;

import lombok.Data;

import java.util.concurrent.ScheduledFuture;
@Data
public class ScheduledTask {
    private final Task task;

    volatile ScheduledFuture<?> future;

    ScheduledTask(Task task) {
        this.task = task;
    }

    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
