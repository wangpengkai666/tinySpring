package context.schedule.config;

import bean.factory.DisposableBean;
import bean.factory.InitializingBean;
import context.schedule.support.scheduler.TaskScheduler;
import context.schedule.support.task.CronTask;
import context.schedule.support.task.ScheduledTask;
import context.schedule.support.task.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author wangpengkai
 */
public class ScheduledTaskRegistrar implements InitializingBean, DisposableBean {

    private ScheduledExecutorService localExecutor;

    private List<CronTask> cronTasks;

    private TaskScheduler taskScheduler;

    private Map<Task, ScheduledTask> unresolvedTasks;

    private Set<ScheduledTask> scheduledTasks;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
