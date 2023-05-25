package xyz.cherish.test.beans.factory.context.event;

import xyz.cherish.beans.factory.context.ApplicationListener;
import xyz.cherish.beans.factory.context.event.ApplicationContextEvent;
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
