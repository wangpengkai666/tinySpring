package bean;

public class UserService {
    private String name;
    private Integer age;

    UserService(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void queryUserInfo() {
        System.out.println("----query some info:tiny spring run----");
    }
}
