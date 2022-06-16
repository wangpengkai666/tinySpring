package bean;

import cn.hutool.core.io.IoUtil;
import io.DefaultResourceLoader;
import io.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceLoadTest {
    private DefaultResourceLoader defaultResourceLoader;

    @Before
    public void init() {
        defaultResourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void testFile() throws IOException {
        Resource resource = defaultResourceLoader.getResource("src/main/resources/bean.xml");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testURL() {
        //todo set a url resource
    }

    @Test
    public void testPath() throws IOException {
        Resource resource = defaultResourceLoader.getResource("classpath:bean.UserDAO");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
 }
