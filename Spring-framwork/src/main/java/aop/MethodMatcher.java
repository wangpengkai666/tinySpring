package aop;

import java.lang.reflect.Method;

/**
 * @author wangpengkai
 */
public interface MethodMatcher {
    /**
     * Perform static checking whether the given method matches. If this
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
