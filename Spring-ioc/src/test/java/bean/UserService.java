package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    private int uId;
    private UserDAO userDAO;

    public void queryUserInfo() {
        System.out.println(userDAO.queryUserName(uId));
    }
}
