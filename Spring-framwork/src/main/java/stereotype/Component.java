package stereotype;

import java.lang.annotation.*;

/**
 * @author wangpengkai
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
