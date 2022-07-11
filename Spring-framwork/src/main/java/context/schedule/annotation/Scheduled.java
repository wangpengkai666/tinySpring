package context.schedule.annotation;

import java.lang.annotation.*;

/**
 * @author wangpengkai
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scheduled {
    String cron() default "";
}
