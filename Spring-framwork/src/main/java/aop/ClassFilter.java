package aop;

/**
 * @author wangpengkai
 */
public interface ClassFilter {

    /**
     * Should the pointcut apply to the given interface or target class
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
