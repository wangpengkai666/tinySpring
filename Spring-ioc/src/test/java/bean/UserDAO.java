package bean;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static Map<Integer, String> hashMap = new HashMap<>();

    static {
        for (int i = 0; i < 4; i++) {
            hashMap.put(i, "test1" + i);
        }
    }

    public String queryUserName(Integer uId) {
        return hashMap.get(uId);
    }
}
