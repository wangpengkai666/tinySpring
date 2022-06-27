package aop.framework;

/**
 * @author wangpengkai
 */
public interface AopProxy {
    /**
     * 获取代理对象的接口，因为代理的实现可以有多种,cglib/jdk
     * @return
     */
    Object getProxy();
}
