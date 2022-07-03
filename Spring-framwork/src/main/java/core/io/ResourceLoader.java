package core.io;

/**
 * @author wangpengkai
 */
public interface ResourceLoader {
    /**
     * the prefix the class:path
     */
    String CLASSPATH_PREFIX = "classpath:";

    /**
     * get the resource from file/class/url
     * @param location
     * @return
     */
    Resource getResource(String location);
}
