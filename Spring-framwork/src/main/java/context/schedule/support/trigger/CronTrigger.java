package context.schedule.support.trigger;

import context.schedule.CronSequenceGenerator;
import context.schedule.support.context.TriggerContext;
import lombok.Data;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author wangpengkai
 */
@Data
public class CronTrigger implements Trigger {
    private final CronSequenceGenerator sequenceGenerator;

    public CronTrigger(String expression) {
        this.sequenceGenerator = new CronSequenceGenerator(expression);
    }

    public CronTrigger(String expression, TimeZone timeZone) {
        this.sequenceGenerator = new CronSequenceGenerator(expression, timeZone);
    }

    public String getExpression() {
        return this.sequenceGenerator.getExpression();
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        Date date = triggerContext.lastCompletionTime();
        if (date != null) {
            Date scheduled = triggerContext.lastScheduledExecutionTime();
            if (scheduled != null && date.before(scheduled)) {
                // Previous task apparently executed too early...
                // Let's simply use the last calculated execution time then,
                // in order to prevent accidental re-fires in the same second.
                date = scheduled;
            }
        } else {
            date = new Date();
        }
        return this.sequenceGenerator.next(date);
    }
}
