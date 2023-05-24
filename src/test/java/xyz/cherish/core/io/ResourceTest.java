package xyz.cherish.core.io;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class ResourceTest {

    @Test
    public void testResource() throws IOException, URISyntaxException {
        /*
        类路径加载资源
         */
        Resource resource = new ClassPathResource("hello.txt");
        printResourceAsString(resource, "classpath");
        byte[] bytes;
        String s;

        /*
        文件系统加载
         */
        resource = new FileSystemResource("C:\\Users\\yuchenyu\\IdeaProjects\\my-mini-spring\\src\\test\\resources\\hello.txt");
        printResourceAsString(resource, "file system");

        /*
        URL资源加载
         */
        resource = new URLResource(new URI("http://baidu.com").toURL());
        printResourceAsString(resource, "url");
    }

    @Test
    public void testDefaultResourceLoader() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        printResourceAsString(resource, "classpath");

        resource = resourceLoader.getResource("C:\\Users\\yuchenyu\\IdeaProjects\\my-mini-spring\\src\\test\\resources\\hello.txt");
        printResourceAsString(resource, "file system");

        resource = resourceLoader.getResource("http://baidu.com");
        printResourceAsString(resource, "url");
    }

    private static void printResourceAsString(Resource resource, String notice) throws IOException {
        InputStream is = resource.getInputStream();
        byte[] bytes = is.readAllBytes();
        String s = new String(bytes);
        System.out.println("read from " + notice + " source: " + s);
        is.close();
    }
}
