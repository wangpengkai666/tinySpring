package aoptest;

import bean.factory.annotation.Autowired;
import bean.factory.annotation.Value;
import lombok.Data;
import stereotype.Component;

import java.lang.annotation.Documented;
import java.util.Random;

@Component("UserService")
@Data
public class UserService implements IUserService {
    @Value("${token}")
    private String token;

    @Autowired
    private UserDAO userDAO;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "token=" + token + userDAO;
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }
}
