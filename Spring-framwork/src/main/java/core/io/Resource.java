package core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangpengkai
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
