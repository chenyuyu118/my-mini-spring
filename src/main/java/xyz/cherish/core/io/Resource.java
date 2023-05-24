package xyz.cherish.core.io;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;

public interface Resource {
    InputStream getInputStream() throws IOException;
}
