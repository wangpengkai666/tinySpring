package bean.factory.annotation;

import java.lang.annotation.*;

/**
 * @author wangpengkai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Documented
public @interface Value {
    String value();
}
