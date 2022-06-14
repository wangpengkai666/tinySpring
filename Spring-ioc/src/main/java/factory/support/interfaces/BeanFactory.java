package factory.support.interfaces;

/**
 * @author wangpengkai
 */
public interface BeanFactory {
    /**
     * 抽象方法，根据类的名称获取一个实例对象
     * @param name
     * @return
     */
    public Object getBean(String name);
}
