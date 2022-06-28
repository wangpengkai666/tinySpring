package aop;

import org.aopalliance.aop.Advice;

/**
 * @author wangpengkai
 */
public interface Advisor {
    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     * @return
     */
    Advice getAdvice();
}
