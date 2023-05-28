package xyz.cherish.test.model;

import xyz.cherish.context.annotation.Value;
import xyz.cherish.stereotype.Component;

@Component
public class User implements UserInterface {
    @Value("${name}")
    public String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
