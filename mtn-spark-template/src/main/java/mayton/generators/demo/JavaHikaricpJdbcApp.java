package mayton.generators.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class JavaHikaricpJdbcApp {

    static Logger logger = LoggerFactory.getLogger("demo");

    private HikariConfig hikariConfig = new HikariConfig();

    private HikariDataSource ds;

    private String jdbcUrl  = "jdbc:postgresql://127.0.0.1:5432/orcl";
    private String username = "scott";
    private String password = "************";
    private String driverClassName = "";

    public void init() {
        logger.info("init");

        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        hikariConfig.addDataSourceProperty("minimumIdle", 5);
        hikariConfig.addDataSourceProperty("maximumPoolSize", 20);
        hikariConfig.addDataSourceProperty("idleTimeout", 30000);
        hikariConfig.addDataSourceProperty("maxLifetime", 2000000);
        hikariConfig.addDataSourceProperty("connectionTimeout", 30000);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 4);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 4);

        ds = new HikariDataSource(hikariConfig);
    }

    public JavaHikaricpJdbcApp() throws SQLException {
        init();
        Connection conn = ds.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        new JavaHikaricpJdbcApp();
    }

}
