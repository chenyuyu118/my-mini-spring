package xyz.cherish.context.support;

import xyz.cherish.beans.factory.support.DefaultListableBeanFactory;
import xyz.cherish.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 从XML文件中创建ApplicationContext
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    public void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] locations = getConfigLocation();
        if (locations != null) {
            beanDefinitionReader.loadBeanDefinition(locations);
        }
    }

    public abstract String[] getConfigLocation();
}
