package aop;

/**
 * @author wangpengkai
 */
public interface Pointcut {
    /**
     * Return the ClassFilter for this pointcut
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * Return the MethodMatcher for this pointcut.
     * @return
     */
    MethodMatcher getMethodMatcher();
}
