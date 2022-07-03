package core.convert.converter;

/**
 * @author wangpengkai
 */
public interface ConverterRegistry {
    /**
     * Add a plain converter to this registry.
     * @param converter
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * Add a generic converter to this registry.
     * @param converter
     */
    void addConverter(GenericConverter converter);

    /**
     * Add a ranged converter factory to this registry.
     * @param converterFactory
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);
}
