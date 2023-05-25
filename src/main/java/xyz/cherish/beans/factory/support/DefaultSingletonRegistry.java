package xyz.cherish.beans.factory.support;

import xyz.cherish.beans.factory.DisposableBean;
import xyz.cherish.beans.factory.config.SingletonBeanRegistry;
import xyz.cherish.exception.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认的单例注册
 */
public class DefaultSingletonRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonMap = new HashMap<>();
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonMap.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object singletonObj) {
        singletonMap.put(beanName, singletonObj);
    }

    /**
     * 注册可以执行destroy方法的bean
     *
     * @param beanName bean名称
     * @param bean     bean对象
     */
    public void registerDisposeBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName, bean);
    }

    public void destroySingleton() {
        ArrayList<String> disposeBeans = new ArrayList<>(disposableBeanMap.keySet());
        for (String disposeBean : disposeBeans) {
            DisposableBean removed = disposableBeanMap.remove(disposeBean);
            try {
                removed.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean " + disposeBean + " invoke failed");
            }
        }
    }
}
