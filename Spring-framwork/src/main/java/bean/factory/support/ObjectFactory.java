package bean.factory.support;

import bean.BeansException;

/**
 *  Defines a factory which can return an Object instance
 * (possibly shared or independent) when invoked.
 * @author wangpengkai
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
