package context;

import bean.factory.HierarchicalBeanFactory;
import bean.factory.ListableBeanFactory;
import core.io.ResourceLoader;

/**
 * @author wangpengkai
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
