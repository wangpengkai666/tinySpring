package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangpengkai
 */
public class PropertyValues {
    /**
     * container to hold the property of a bean
     */
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * add property of bean to a list
     * @param prv
     */
    public void addPropertyValue(PropertyValue prv) {
        propertyValueList.add(prv);
    }

    /**
     * get property list from the bean
     * @return
     */
    public List<PropertyValue> getPropertyValues() {
        return propertyValueList;
    }

    /**
     * get property by name from the bean
     * @param name
     * @return
     */
    public PropertyValue getPropertyValueByName(String name) {
        if(name==null) return null;
        for(PropertyValue propertyValue:propertyValueList) {
            if(name.equals(propertyValue.getName())) {
                return propertyValue;
            }
        }

        return null;
    }
}
