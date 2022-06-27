package bean;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static Map<Integer, String> hashMap = new HashMap<>();

    private void initMethod() {
        for (int i = 0; i < 4; i++) {
            hashMap.put(i, "test1" + i);
        }
    }

    public void destroyMethod() {
        System.out.println("do destroy method");
        hashMap.clear();
    }

    public String queryUserName(Integer uId) {
        return hashMap.get(uId);
    }
}
