package com.demxs.tdm.comac.common.jdbc.utils;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 获得临时数据源DataSource
 * Created by guojinlong on 2017/12/28.
 */
public class DataSourceTemp extends BasicDataSource {

    public DataSource getDataSource(String driverClassName, String url, String userName, String passWord) {
        DataSource ds = null;
        super.setDriverClassName(driverClassName);
        super.setUrl(url);
        super.setUsername(userName);
        super.setPassword(passWord);
        try {
            ds = super.createDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public DataSource getDriverManagerDataSource(String driverClassName, String url, String userName, String passWord){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClass(driverClassName);
        source.setJdbcUrl(url);
        source.setUser(userName);
        source.setPassword(passWord);
        return source;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
