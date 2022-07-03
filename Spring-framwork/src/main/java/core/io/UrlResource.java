package core.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author wangpengkai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlResource implements Resource{
    private URL url;

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = this.url.openConnection();
        try {
            return urlConnection.getInputStream();
        } catch (IOException ex){
            // to disconnect the resource when face the exception
            if (urlConnection instanceof HttpURLConnection){
                ((HttpURLConnection) urlConnection).disconnect();
            }
            throw ex;
        }

    }
}
