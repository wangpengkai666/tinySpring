package io;

import lombok.Data;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangpengkai
 */
@Data
public class FileSystemResource implements Resource {
    /**
     * the file about the class
     */
    private final File file;

    /**
     * the path to get the file
     */
    private String path;

    public FileSystemResource(File file) {
        Assert.notNull(file, "file must not be null");
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        Assert.notNull(path, "path must not be null");
        this.file = new File(path);
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
