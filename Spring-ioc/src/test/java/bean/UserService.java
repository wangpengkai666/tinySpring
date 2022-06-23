package bean;

import bean.factory.DisposableBean;
import bean.factory.InitializingBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserService implements InitializingBean, DisposableBean {
    private int uId;
    private UserDAO userDAO;

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
}
