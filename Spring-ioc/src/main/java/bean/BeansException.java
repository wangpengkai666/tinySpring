package bean;

/**
 * just a notation for exception
 * @author wangpengkai
 */
public class BeansException extends RuntimeException {
    String msg;

    public BeansException(String msg) {
        this.msg = msg;
    }

    public BeansException() {
    }
}
