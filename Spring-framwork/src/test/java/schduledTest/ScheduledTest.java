package schduledTest;

import context.schedule.annotation.Scheduled;
import stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTest {

    @Scheduled(cron = "*/2 * * * * ?")
    public void test() throws InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("test:" + simpleDateFormat.format(new Date()));
        System.out.println("线程池的介绍:"+Thread.currentThread().getName()+":"+ Thread.currentThread());
        Thread.sleep(2000);
    }
}
