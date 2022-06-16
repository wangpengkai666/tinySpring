package bean.factory;

import javax.management.ObjectName;

/**
 * @author wangpengkai
 */
public interface BeanFactory {
    /**
     * get beanObject from name
     * @param name
     * @return
     */
    public Object getBean(String name);

    /**
     * get beanObject from name,args(use the parametric constructor of reflections)
     * @param name
     * @param args
     * @return
     */
    public Object getBean(String name, Object... args);
}
