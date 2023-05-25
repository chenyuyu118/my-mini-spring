package xyz.cherish.test.model;

import xyz.cherish.beans.FactoryBean;

public class UserFactory implements FactoryBean<User> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User getObject() throws Exception {
        return new User(name);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
