package xyz.cherish.core.io;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class DefaultResourceLoader implements ResourceLoader {

    public static final String CLASS_PATH_RESOURCE_PREFIX = "classpath:";
    @Override
    public Resource getResource(String path) {
        if (path.startsWith(CLASS_PATH_RESOURCE_PREFIX)) {
            return new ClassPathResource(path.substring(CLASS_PATH_RESOURCE_PREFIX.length()));
        } else {
            try {
                URI uri = new URI(path);
                return new URLResource(uri.toURL());
            } catch (URISyntaxException | MalformedURLException e) {
                return new FileSystemResource(path);
            }
        }
    }
}
