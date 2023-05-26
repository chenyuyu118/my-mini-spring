package xyz.cherish.test.service;

public class SimpleServiceImpl implements SimpleService {

    @Override
    public void hello() {
        System.out.println("hello");
    }

    @Override
    public void error() throws NoSuchMethodException {
        throw new NoSuchMethodException("no such method test");
    }
}
