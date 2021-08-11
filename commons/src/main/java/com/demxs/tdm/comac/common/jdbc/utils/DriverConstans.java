package com.demxs.tdm.comac.common.jdbc.utils;

/**
 * Created by guojinlong on 2017/12/28.
 */
public abstract class DriverConstans {

    /**
     * 数据库类型
     * */
    public static final class DbType{
        public static final String  ORACLE = "Oracle";// ORACLE驱动类
        public static final String MYSQL = "MySql";// MYSQL驱动类
        public static final String POSTGRE = "PostGre";// POSTGRE驱动类
        public static final String ACCESS = "Access";// ACCESS驱动类
        public static final String FILE = "File";// 采集文件
    }

    /**
     * 数据库驱动类常量
     * */
    public static final class DriverClass{
        public static final String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";// ORACLE驱动类
        public static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";// MYSQL驱动类
        public static final String POSTGRE_DRIVER_CLASS = "org.postgresql.Driver";// POSTGRE驱动类
//        public static final String ACCESS_DRIVER_CLASS = "org.objectweb.rmijdbc.Driver";// ACCESS驱动类
        public static final String ACCESS_DRIVER_CLASS = "net.ucanaccess.jdbc.UcanaccessDriver";// ACCESS驱动类
    }
}
