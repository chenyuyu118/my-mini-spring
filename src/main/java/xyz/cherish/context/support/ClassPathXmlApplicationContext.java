package xyz.cherish.context.support;

import xyz.cherish.exception.BeansException;


public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    public String[] getConfigLocation() {
        return this.configLocations;
    }
}
