package bean;

import bean.factory.BeanFactory;
import bean.factory.DisposableBean;
import bean.factory.InitializingBean;
import bean.factory.support.BeanClassLoaderAware;
import bean.factory.support.BeanFactoryAware;
import bean.factory.support.BeanNameAware;
import context.ApplicationContext;
import context.ApplicationContextAware;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserService implements InitializingBean, DisposableBean,
        BeanFactoryAware, BeanClassLoaderAware, BeanNameAware, ApplicationContextAware {
    private int uId;
    private UserDAO userDAO;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    public void queryUserInfo() {
        System.out.println(userDAO.queryUserName(uId));
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("class=UserService||method=destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("class=UserService||method=init-afterPropertiesSet");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println(classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println(beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println(beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }
}
