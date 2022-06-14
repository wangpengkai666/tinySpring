import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangpengkai
 */
public class BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name) {
        try {
            return beanDefinitionMap.get(name).getBean();
        } catch (Exception e) {
            return null;
        }
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }
}
