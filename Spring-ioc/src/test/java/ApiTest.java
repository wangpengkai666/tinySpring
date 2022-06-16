import bean.*;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.config.impl.BeanReference;
import bean.factory.support.impl.DefaultListableBeanFactory;
import bean.factory.support.impl.XmlBeanDefinitionReader;
import org.testng.annotations.Test;

public class ApiTest {
    @Test
    public void testRepo() {
        // create a new Factory
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // create the definition of class
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        // register the definition to factory
        defaultListableBeanFactory.registerBeanDefinition(UserService.class.getName(),beanDefinition);
        // get singleton instance
        //UserService userService = (UserService)defaultListableBeanFactory.getBean(UserService.class.getName());
        UserService userService = (UserService)defaultListableBeanFactory.getBean(UserService.class.getName(),"wpk",12);
        userService.queryUserInfo();
        // get singleton instance from single cache
        userService = (UserService)defaultListableBeanFactory.getBean(UserService.class.getName());
        userService.queryUserInfo();
    }

    @Test
    public void testProperty() {
        // 1.init BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. UserDao register
        beanFactory.registerBeanDefinition("userDAO", new BeanDefinition(UserDAO.class)
        );
        //3. set properties
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId",1));
        propertyValues.addPropertyValue(new PropertyValue("userDAO", new BeanReference("userDAO")));

        // 4. UserService register bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService get bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void testXML() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("src/main/resources/bean.xml");
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
