package factory.config.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * base the enclosure to the class, we can construct the instance by class
 * @author wangpengkai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanDefinition {
    /**
     *  enclosure bean-class
     */
    private Class beanClass;
}
