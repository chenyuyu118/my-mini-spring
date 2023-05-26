package xyz.cherish.test.context.event;

import xyz.cherish.context.ApplicationListener;
import xyz.cherish.context.event.ApplicationContextEvent;
import xyz.cherish.test.model.User;

public class CustomEventListener implements ApplicationListener<ApplicationContextEvent> {
    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        System.out.println(event.getClass().getName() + "happened");
        User user = event.getApplicationContext().getBean("user", User.class);
        System.out.println(user);
        System.out.println();
    }
}
