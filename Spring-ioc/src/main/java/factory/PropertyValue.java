package factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangpengkai
 */
@Data
@AllArgsConstructor
public class PropertyValue {
    private final String name;
    private final Object value;
}
