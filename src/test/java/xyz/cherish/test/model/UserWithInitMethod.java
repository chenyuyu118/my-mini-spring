package xyz.cherish.test.model;

public class UserWithInitMethod {
    private String name;

    public void print() {
        System.out.println(toString());
    }

    public void destroy() {
        System.out.println(name + " has been destroyed");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserWithInitMethod{" +
                "name='" + name + '\'' +
                '}';
    }
}
