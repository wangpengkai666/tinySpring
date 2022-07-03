package core.convert.converter;

/**
 * @author wangpengkai
 */
public interface ConverterFactory<S, R> {
    /**
     * 从目标类当中获取一个数据转换器
     * @param targetType
     * @param <T>
     * @return
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
