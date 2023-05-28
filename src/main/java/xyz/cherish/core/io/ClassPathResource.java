package xyz.cherish.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 * 从类路径上获取输入流
 */
public class ClassPathResource implements Resource {

    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(this.path + " can't be opened because it doesn't exist");
        }
        return is;
    }

    public Enumeration<URL> getResourceURLs() throws IOException {
        return this.getClass().getClassLoader().getResources(path);
    }
}
