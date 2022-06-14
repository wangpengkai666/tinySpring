import bean.UserService;
import factory.config.impl.BeanDefinition;
import factory.support.impl.DefaultListableBeanFactory;
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
}
