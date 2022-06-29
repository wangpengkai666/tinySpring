package context.annotation;

import java.lang.annotation.*;

/**
 * @author wangpengkai
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
