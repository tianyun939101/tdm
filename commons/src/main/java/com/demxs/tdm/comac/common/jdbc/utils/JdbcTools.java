package com.demxs.tdm.comac.common.jdbc.utils;

import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * 使用JdbcTemplate创建临时数据源，对数据库操作。
 * Created by guojinlong on 2017/12/28.
 */
public class JdbcTools {

    private static final Logger log = LoggerFactory.getLogger(JdbcTools.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcTools(String dbType,String url,String userName,String passWord) {
        jdbcTemplate = getJdbcTemplate(dbType,url,userName,passWord);
    }

    /**
     * 测试连接是否连通
     * @return boolean 是否成功连通 true：连通 false:连接失败
     * @throws SQLException
     */
    public boolean testConnect() throws SQLException {
        boolean isSuccess = false;
        DatabaseMetaData databaseMetaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        String[]types={"TABLE"};
        ResultSet rs=databaseMetaData.getTables(null,null,"%",types);
        if (rs.next()){
            log.debug("testConnect success! table-name:{}",rs.getString(3));
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 获取表全部数据
     * @param tableName 表名
     * @return List<Map<String, Object>> 返回一个装有map的list,每一个map是一条记录,map里面的key是字段名
     */
    public List<Map<String, Object>> getTableResult(String tableName){
        String sql = "select * from "+tableName;
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 获取表全部数据
     * @param tableName 表名
     * @return List<Map<String, Object>> 返回一个装有map的list,每一个map是一条记录,map里面的key是字段名
     */
    public List<Map<String, Object>> getTableResultWithParam(String tableName,Map<String,String> colAndType,Map<String,Object> params){
        String sql = "select * from "+tableName;
        String whereSql = "where ";
        Set<String> keySet = colAndType.keySet();
        Object[] args = new Object[keySet.size()];
        int num = 0;
        for(String key:keySet){
            whereSql += key + " "+ params.get(key) + " ?,";
            args[num] = params.get(key);
            num++;
        }
//        jdbcTemplate.queryForList()
        jdbcTemplate.queryForList(sql+whereSql,args);
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 获取表数据
     * @param tableName 表名
     * @param column 字段名
     * @param type 类型（=、in、like等）
     * @return List<Map<String, Object>> 返回一个装有map的list,每一个map是一条记录,map里面的key是字段名
     */
    public List<Map<String, Object>> getTableResultWithOneParam(String tableName,String column,String type,List<String> param){
        String sql = "select * from "+tableName+" where "+column+" "+type+" ( :param ) ";
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("param", param);
        return namedParameterJdbcTemplate.queryForList(sql,parameters);
       /* String sql = "select * from "+tableName;
        return jdbcTemplate.queryForList(sql);*/
    }


    /**
     * 获取表数据
     * @param tableName 表名
     * @param column 字段名
     * @return List<Map<String, Object>> 返回一个装有map的list,每一个map是一条记录,map里面的key是字段名
     */
    public List<Map<String, Object>> getTableResultBySql(String tableName,String column,List<String> param){
        String sql = "select * from "+tableName;
        String whereSql = " where 1=1 ";
        StringBuilder sqlString = new StringBuilder();
        if(param!=null && param.size()>0){
            sqlString.append(" and ");
            sqlString.append(" ( ");
            for(int i = 0;i<param.size();i++){
                if(i!=param.size()-1){
                    sqlString.append("upper("+column + ") like '%"+ StringUtils.upperCase(param.get(i))+"%' or ");
                }else{
                    sqlString.append("upper("+column + ") like '%"+StringUtils.upperCase(param.get(i))+"%' ");
                }
            }
            sqlString.append(" ) ");
        }
        return  jdbcTemplate.queryForList(sql+whereSql+sqlString);
    }

    /**
     * 获取JdbcTemplate
     * @param dbType 数据库类型（如：Oracle,MySql,Access,PostGre）
     * @param url 连库URL
     * @param userName 用户名
     * @param passWord 密码
     * @return JdbcTemplate
     */
    private JdbcTemplate getJdbcTemplate(String dbType,String url,String userName,String passWord){
        //驱动类名
        String driverClassName = "";
        //获取驱动类
        switch (dbType){
            case DriverConstans.DbType.ORACLE : driverClassName = DriverConstans.DriverClass.ORACLE_DRIVER_CLASS;
                break;
            case DriverConstans.DbType.MYSQL: driverClassName = DriverConstans.DriverClass.MYSQL_DRIVER_CLASS;
                break;
            case  DriverConstans.DbType.ACCESS: driverClassName = DriverConstans.DriverClass.ACCESS_DRIVER_CLASS;
                break;
            case  DriverConstans.DbType.POSTGRE: driverClassName = DriverConstans.DriverClass.POSTGRE_DRIVER_CLASS;
                break;
        }
        return new JdbcTemplate(getDateSource(driverClassName,url,userName,passWord));
    }

    /**
     * 获取数据源
     * @param driverClassName 驱动类
     * @param url 连库URL
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    public DataSource getDateSource(String driverClassName, String url, String userName, String passWord){
        DataSourceTemp dst = new DataSourceTemp();
        return dst.getDriverManagerDataSource(driverClassName,url,userName,passWord);
    }

    public static void main(String[] args) {
       /* String dbType = "Access";
        // String url = "jdbc:ucanaccess://D:\\tdm\\longitdm.mdb";
        //远程
        String url = "jdbc:ucanaccess://\\\\66.0.91.18\\data\\rpt.mdb";
        //本地

        String userName = "";
        String passWord = "";
        String tableName = "report";
*/
        String dbType = "PostGre";
        String url = "jdbc:postgresql://66.0.91.16:5409/Pasan-R2.4";
        //本地
        String userName = "postgres";
        String passWord = "supersecret";
        String tableName = "simplified_result";
        testCon(dbType,url,userName,passWord);
//        testGetData(dbType,url,userName,passWord,tableName);
        //testGetData(dbType,url,userName,passWord,tableName);
        testGetDataJson(dbType,url,userName,passWord,tableName);
        //testGetData(dbType,url,userName,passWord,tableName);
    }


    public static void testGetData(String dbType,String url,String userName,String passWord,String tableName){
        JdbcTools jdbcTools = new JdbcTools(dbType,url,userName,passWord);
//        List<Map<String,Object>> rowList = jdbcTools.getTableResult(tableName);
        List<String> paramList = new ArrayList<>();
        paramList.add("LRE404028161001400007c");
        List<Map<String,Object>> rowList = jdbcTools.getTableResultBySql(tableName,"sn",paramList);
        int i = 0;
        for(Map map : rowList){
            if (i == 0){
                for(Object key : map.keySet()){
                    System.out.print(key+"\t");
                }
                System.out.println();
                System.out.println("----------------------------------------");
            }
            for(Object key : map.keySet()){
                System.out.print(map.get(key)+"\t");
            }
            System.out.println();
            i++;
        }
    }

    public static void testCon(String dbType,String url,String userName,String passWord){
        JdbcTools jdbcTools = new JdbcTools(dbType,url,userName,passWord);
        try {
            if (jdbcTools.testConnect()){
                System.out.println("连接成功！");
            }
            else{
                System.out.println("连接失败！");
            }
        } catch (SQLException e) {
            System.out.println("连接失败！");
            e.printStackTrace();
        }
    }

  /*  public static void testGetData(String dbType,String url,String userName,String passWord,String tableName){
        JdbcTools jdbcTools = new JdbcTools(dbType,url,userName,passWord);
        List<Map<String,Object>> rowList = jdbcTools.getTableResult(tableName);
//        List<String> paramList = new ArrayList<>();
//        paramList.add("1");
//        paramList.add("2");
//        List<Map<String,Object>> rowList = jdbcTools.getTableResultWithOneParam(tableName,"\"Eff\"","in",paramList);
        int i = 0;
        for(Map map : rowList){
            if (i == 0){
                for(Object key : map.keySet()){
                    System.out.print(key+"\t");
                }
                System.out.println();
                System.out.println("----------------------------------------");
            }
            for(Object key : map.keySet()){
                System.out.print(map.get(key)+"\t");
            }
            System.out.println();
            i++;
        }
    }*/

    public static void testGetDataJson(String dbType,String url,String userName,String passWord,String tableName){
       /* JdbcTools jdbcTools = new JdbcTools(dbType,url,userName,passWord);
        List<Map<String,Object>> rowList = jdbcTools.getTableResult(tableName);
        System.out.println(JsonMapper.toJsonString(rowList));*/
        JdbcTools jdbcTools = new JdbcTools(dbType,url,userName,passWord);
        List<String> paramList = new ArrayList<>();

        paramList.add("lRP503032171100627031");
        //List<Map<String,Object>> rowList = jdbcTools.getTableResultWithOneParam(tableName,"\"SerialNb\"","in",paramList);
        //getTableResultBySql
        List<Map<String,Object>> rowList = jdbcTools.getTableResultBySql(tableName,"serialnb",paramList);
        System.out.println(JsonMapper.toJsonString(rowList));
    }
}
