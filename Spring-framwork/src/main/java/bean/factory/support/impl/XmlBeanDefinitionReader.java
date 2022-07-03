package bean.factory.support.impl;

import bean.BeansException;
import bean.PropertyValue;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.config.impl.BeanReference;
import bean.factory.support.interfaces.BeanDefinitionRegistry;
import cn.hutool.core.util.StrUtil;
import context.annotation.ClassPathBeanDefinitionScanner;
import core.io.Resource;
import core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for(String location:locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * the extended class can custom this method
     *
     * @param inputStream
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws IOException, ClassNotFoundException, BeansException, DocumentException {
        SAXReader reader = new SAXReader();
        Document read = reader.read(inputStream);
        Element root = read.getRootElement();

        Element scanComponent = root.element("component-scan");
        if(scanComponent!=null) {
            String basePackages = scanComponent.attributeValue("base-package");
            if(basePackages!=null&&!basePackages.isEmpty()) {
                scanPackage(basePackages);
            } else {
                throw new BeansException();
            }
        }

        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {

            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements("property");
            // 读取属性并填充
            for (Element property : propertyList) {
                // 解析标签：property
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void scanPackage(String scanPath) {
        String[] basePackages = scanPath.split(",");
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(getRegistry());
        classPathBeanDefinitionScanner.doScan(basePackages);
    }
}
