package jdbcTest;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;
import org.testng.annotations.Test;

public class JdbcTest {

    private DruidDataSource dataSource;

    private Connection connection;

    private Statement statement;

    @Before
    public void init() throws SQLException {
        dataSource = new DruidDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/document_search?useUnicode=true&characterEncoding=utf8");
        dataSource.setPassword("159284zxy");
        dataSource.setUsername("root");
        connection = dataSource.getConnection().getConnection();
        statement = connection.createStatement();
    }

    @Test
    public void saveDataWithoutTranslation() {

    }

    @Test
    public void saveDataWithTranslation() {

    }

    @Test
    public void saveDataWithTranslationProxy() {

    }

    @After
    public void destroy() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
