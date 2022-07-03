package core.io;

import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangpengkai
 */
@Data
public class ClassPathResource implements Resource {
    /**
     * the path of a class
     */
    private final String path;

    /**
     * the classLoader to get the resource from path as inputStream
     */
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        String classPath = path.substring(path.indexOf(':') + 1);
        InputStream inputStream = classLoader.getResourceAsStream(classPath);
        if (inputStream == null) {
            throw new FileNotFoundException("can not find class:"+classPath);
        }
        return inputStream;
    }
}
