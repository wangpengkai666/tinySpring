package bean.factory.support;

/**
 * Interface to be implemented by beans that want to be aware of their bean name in a bean factory.
 * @author wangpengkai
 */
public interface BeanNameAware extends Aware{
    /**
     * set bean name
     * @param name
     */
    void setBeanName(String name);
}
