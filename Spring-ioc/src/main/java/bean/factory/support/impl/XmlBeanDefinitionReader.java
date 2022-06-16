package bean.factory.support.impl;

import bean.BeansException;
import bean.PropertyValue;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.config.impl.BeanReference;
import bean.factory.support.interfaces.BeanDefinitionRegistry;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import io.Resource;
import io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangpegnkai
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        super(beanDefinitionRegistry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream inputStream = resource.getInputStream();
            doLoadBeanDefinitions(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        // construct the resource from location
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    /**
     * the extended class can custom this method
     *
     * @param inputStream
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws IOException, ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        // the root of parse-tree
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (!(item instanceof Element)) continue;
            if (!"bean".equals(item.getNodeName())) continue;

            // start to parse the xml tags
            Element bean = (Element) item;
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");

            // set the priority of id or name(compared to the origin spring alias)
            Class<?> clazz = Class.forName(className);
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }


            // define the bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            // add properties to the bean
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                Node subNode = bean.getChildNodes().item(j);
                if (!(subNode instanceof Element)) continue;
                if (!"property".equals(subNode.getNodeName())) continue;

                // parse the property tags
                Element subElement = (Element) subNode;
                String subName = subElement.getAttribute("name");
                String subValue = subElement.getAttribute("value");
                String subRef = subElement.getAttribute("ref");
                Object value = StrUtil.isEmpty(subRef) ? subValue : new BeanReference(subRef);

                // build the property info
                PropertyValue propertyValue = new PropertyValue(subName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            // do some validation
            if (getRegistry().containsBeanDefinition(beanName)) {
                continue;
            }

            // register the beanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
