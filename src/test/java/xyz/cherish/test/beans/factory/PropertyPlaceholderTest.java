package xyz.cherish.test.beans.factory;

import org.junit.Test;
import xyz.cherish.context.support.ClassPathXmlApplicationContext;
import xyz.cherish.test.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyPlaceholderTest {
    public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{(.*)}");

    @Test
    public void testPattern() {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher("${ good }");
        if (matcher.matches()) {
            for (int i = 0; i <= matcher.groupCount(); ++i) {
                System.out.println(i + " " + matcher.group(i).trim());
            }
        }
    }

    @Test
    public void testPropertyPlaceHolder() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property_placeholder_configurator.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
    }
}
