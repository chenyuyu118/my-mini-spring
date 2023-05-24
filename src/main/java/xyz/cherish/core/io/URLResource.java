package xyz.cherish.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResource implements Resource{

    private final URL url;

    public URLResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return url.openConnection().getInputStream();
    }
}
