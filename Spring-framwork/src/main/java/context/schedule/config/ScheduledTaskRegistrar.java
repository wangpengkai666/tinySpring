package context.schedule.config;

import bean.factory.DisposableBean;
import bean.factory.InitializingBean;
import context.schedule.support.scheduler.ConcurrentTaskScheduler;
import context.schedule.support.scheduler.TaskScheduler;
import context.schedule.support.task.CronTask;
import context.schedule.support.task.ScheduledTask;
import context.schedule.support.task.Task;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author wangpengkai
 */
public class ScheduledTaskRegistrar implements InitializingBean, DisposableBean {

    private ScheduledExecutorService localExecutor;

    private List<CronTask> cronTasks;

    private TaskScheduler taskScheduler;

    private final Map<Task, ScheduledTask> unresolvedTasks = new HashMap<>(16);

    private final Set<ScheduledTask> scheduledTasks = new LinkedHashSet<>(16);

    @Override
    public void afterPropertiesSet() {
        scheduleTasks();
    }

    /**
     * 对于任务进行调度处理
     */
    protected void scheduleTasks() {
        if(taskScheduler==null) {
            localExecutor = Executors.newSingleThreadScheduledExecutor();
            taskScheduler = new ConcurrentTaskScheduler();
        }

        if (this.cronTasks != null) {
            for (CronTask task : this.cronTasks) {
                addScheduledTask(scheduleCronTask(task));
            }
        }
    }

    private void addScheduledTask(ScheduledTask task) {
        if (task != null) {
            this.scheduledTasks.add(task);
        }
    }

    /**
     * 单独对于cronTask的逻辑处理
     * @param task
     * @return
     */
    public ScheduledTask scheduleCronTask(CronTask task) {
        ScheduledTask scheduledTask = this.unresolvedTasks.remove(task);
        boolean newTask = false;
        if (scheduledTask == null) {
            scheduledTask = new ScheduledTask(task);
            newTask = true;
        }

        addCronTask(task);
        this.unresolvedTasks.put(task, scheduledTask);

        return (newTask ? scheduledTask : null);
    }

    private void addCronTask(CronTask task) {
        if (this.cronTasks == null) {
            this.cronTasks = new ArrayList<>();
        }

        this.cronTasks.add(task);
    }

    @Override
    public void destroy() throws Exception {
        // 1.关闭所有注册的任务
        for (ScheduledTask scheduledTask : scheduledTasks) {
            scheduledTask.cancel();
        }

        // 2.关闭任务执行的线程池
        if (localExecutor != null) {
            localExecutor.shutdownNow();
        }
    }
}
