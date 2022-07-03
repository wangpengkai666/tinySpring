package core.convert.converter;

/**
 * @author wangpengkai
 */
public interface Converter<S, T> {
    /**
     * 简单定义一个数据转换接口
     * @param source
     * @return
     */
    T convert(S source);
}
