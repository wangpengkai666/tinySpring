package aop;

/**
 * @author wangpengkai
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * Get the Pointcut that drives this advisor
     * @return
     */
    Pointcut getPointcut();
}
