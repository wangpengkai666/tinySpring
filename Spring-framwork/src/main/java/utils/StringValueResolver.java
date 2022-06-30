package utils;

/**
 * @author wangpengkai
 */
public interface StringValueResolver {
    /**
     * a resolver to parse the value such as annotation of @Value
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);
}
