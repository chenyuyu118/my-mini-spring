package xyz.cherish.test.model;

import xyz.cherish.context.annotation.Autowired;
import xyz.cherish.context.annotation.Qualifier;
import xyz.cherish.stereotype.Component;

@Component
public class UserService {

    @Autowired
    @Qualifier("user1")
    private User user;

    @Override
    public String toString() {
        return "UserService{" +
                "user=" + user +
                '}';
    }
}
