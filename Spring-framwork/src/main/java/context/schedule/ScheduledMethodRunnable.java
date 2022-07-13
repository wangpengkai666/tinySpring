package context.schedule;

import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 构造新的无参定时任务runnable，方便后续触发调用指定类的指定方法
 * @author wangpengkai
 */
@Data
public class ScheduledMethodRunnable implements Runnable {

    private Object source;

    private Method method;

    public ScheduledMethodRunnable(Object source, Method method) {
        this.source = source;
        this.method = method;
    }

    @Override
    public void run() {
        try {
            if ((!Modifier.isPublic(method.getModifiers()) ||
                    !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(this.source);
        } catch (InvocationTargetException ex) {
            ReflectionUtils.rethrowRuntimeException(ex.getTargetException());
        } catch (IllegalAccessException ex) {
            throw new UndeclaredThrowableException(ex);
        }
    }
}
