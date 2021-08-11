package com.demxs.tdm.common.utils;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Configuration
public class UreportDataSource implements BuildinDatasource {
    private static final String NAME = "MyDataSource";
    private Logger log = LoggerFactory.getLogger(UreportDataSource.class);

    @Autowired
    private DataSource dataSource;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String userName;

    @Value("${jdbc.password}")
    private String password;
    /**
     * 数据源名称
     **/
    @Override
    public String name() {
        return NAME;
    }

    /**
     * 获取连接
     **/
    @Override
    public Connection getConnection() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (SQLException e) {
            log.error("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
