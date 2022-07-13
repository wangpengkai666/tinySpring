import aop.AdvisedSupport;
import aop.MethodMatcher;
import aop.TargetSource;
import aoptest.*;
import aop.aspectj.AspectJExpressionPointcut;
import aop.framework.Cglib2AopProxy;
import aop.framework.JdkDynamicAopProxy;
import aop.framework.ReflectiveMethodInvocation;
import bean.*;
import bean.UserDAO;
import bean.UserService;
import bean.factory.config.impl.BeanDefinition;
import bean.factory.config.impl.BeanReference;
import bean.factory.support.impl.DefaultListableBeanFactory;
import bean.factory.support.impl.XmlBeanDefinitionReader;
import circular_dependency.Husband;
import circular_dependency.Wife;
import context.support.ClassPathXmlApplicationContext;
import convert.StringToIntegerConverter;
import core.convert.converter.Converter;
import core.convert.support.StringToNumberConverterFactory;
import event.CustomEvent;
import org.aopalliance.intercept.MethodInterceptor;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ApiTest {
    @Test
    public void testRepo() throws BeansException {
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
    public void testProperty() throws BeansException {
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

    @Test
    public void testXMLContext() throws BeansException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("src/main/resources/bean.xml");
        UserService userService = (UserService) classPathXmlApplicationContext.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void testInitAndDestroy() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("src/main/resources/bean.xml");
        classPathXmlApplicationContext.registerShutdownHook();
        UserService userService = (UserService) classPathXmlApplicationContext.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void testFactoryBean() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("src/main/resources/bean.xml");
        classPathXmlApplicationContext.registerShutdownHook();
        IUserDao iUserDao = (IUserDao)classPathXmlApplicationContext.getBean("proxyUserDao");
        String s = iUserDao.queryUserName("1");
        System.out.println("代理的结果是"+s);
    }

    @Test
    public void listerEventTest() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("src/main/resources/bean.xml");
        classPathXmlApplicationContext.publishEvent(new CustomEvent(classPathXmlApplicationContext,12345L,"success"));
        classPathXmlApplicationContext.registerShutdownHook();
    }

    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* aop.IUserService.*(..))");

        Class<aoptest.UserService> clazz = aoptest.UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new aoptest.UserService();
        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* aop.IUserService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("花花"));
    }

    @Test
    public void test_proxy_class() {
        IUserService userService = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserService.class}, (proxy, method, args) -> "你被代理了！");
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

    }

    @Test
    public void test_proxy_method() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new aoptest.UserService();

        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* aop.IUserService.*(..))");

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            return invocation.proceed();
                        } finally {
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod().getName());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });

        String result = proxy.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_aop2() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("src/main/resources/bean.xml");

        IUserService userService = applicationContext.getBean("userservice", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void placeHolderTest() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:bean3.xml");
        System.out.println("test");
    }

    @Test
    public void packageScanTest() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:bean2.xml");
        IUserService userService = classPathXmlApplicationContext.getBean("userService", IUserService.class);
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean4.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公：" + wife.queryHusband());
    }

    @Test
    public void test_convert() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean5.xml");
        convert.Husband husband = applicationContext.getBean("husband", convert.Husband.class);
        System.out.println("测试结果：" + husband);
    }

    @Test
    public void test_StringToIntegerConverter() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.convert("1234");
        System.out.println("测试结果：" + num);
    }

    @Test
    public void test_StringToNumberConverterFactory() {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        System.out.println("测试结果：" + stringToIntegerConverter.convert("1234"));

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        System.out.println("测试结果：" + stringToLongConverter.convert("1234"));
    }

    @Test
    public void ScheduledTest() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:bean6.xml");
        System.out.println("test");
        while (true);
    }
}
