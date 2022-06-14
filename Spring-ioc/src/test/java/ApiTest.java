import bean.UserService;
import org.testng.annotations.Test;

public class ApiTest {
    @Test
    public void testRepo() {
        BeanFactory beanFactory = new BeanFactory();
        String name = "userService";
        beanFactory.registerBeanDefinition(name, new BeanDefinition(new UserService()));
        Object bean = beanFactory.getBean(name);
        if (bean instanceof UserService) {
            UserService userService = (UserService) bean;
            userService.queryUserInfo();
        }
    }
}
