package bean.factory.support;

/**
 * Callback that allows a bean to be aware of the bean{@link ClassLoader class loader};
 * that is, the class loader used by the present bean factory to load bean classes.
 * @author wangpengkai
 */
public interface BeanClassLoaderAware extends Aware{
    /**
     * set class Loader
     * @param classLoader
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
