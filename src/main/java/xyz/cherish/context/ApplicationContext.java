package xyz.cherish.context;

import xyz.cherish.beans.factory.HierarchicalBeanFactory;
import xyz.cherish.beans.factory.ListableBeanFactory;
import xyz.cherish.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {

}
