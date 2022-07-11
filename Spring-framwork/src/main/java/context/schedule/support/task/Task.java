package context.schedule.support.task;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wangpengkai
 */
@Data
@AllArgsConstructor
public class Task {
    private final Runnable runnable;
}
