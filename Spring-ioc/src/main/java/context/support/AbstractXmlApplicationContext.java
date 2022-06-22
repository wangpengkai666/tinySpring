package context.support;

import bean.BeansException;
import bean.factory.support.impl.DefaultListableBeanFactory;
import bean.factory.support.impl.XmlBeanDefinitionReader;

/**
 * @author wangpengkai
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
   protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException{
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        if(configLocations!=null) {
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
