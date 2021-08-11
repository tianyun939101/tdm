package com.demxs.tdm.common.utils.zrutils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author： 谭冬梅
 * @Description：
 * @Date：Create in 2017-07-26 18:35
 * @Modefied By：
 */

public class BiaoqianJson {
    private String mbid;		// 模板ID
    private String bqid;		// 标签ID
    private String caijilx;     //采集类型 tool 工具, form 表单, list 列表
    private String caijizdid;   //采集字段id
    private String sheetname;		// sheet名称
    private Long rowindex;		// 行索引
    private Long columnindex;		// 列索引
    private String enname;//标签英文名
    private String cnname;
    private String address;
    private String cjenname;//采集字段英文名
    private String cjcnname;//采集字段中文名

    public String getCjenname() {
        return cjenname;
    }

    public void setCjenname(String cjenname) {
        this.cjenname = cjenname;
    }

    public String getCjcnname() {
        return cjcnname;
    }

    public void setCjcnname(String cjcnname) {
        this.cjcnname = cjcnname;
    }

    public String getCaijilx() {
        return caijilx;
    }

    public void setCaijilx(String caijilx) {
        this.caijilx = caijilx;
    }

    public String getCaijizdid() {
        return caijizdid;
    }

    public void setCaijizdid(String caijizdid) {
        this.caijizdid = caijizdid;
    }

    private  String cellvalue;//值


    //关联标签查询
    private String biaoqianlx;//标签类型 from or tool or list
    private String ishuixie;//是否回写
    private String biaoqiansx; //标签属性
    public String getBiaoqiansx() {
        return biaoqiansx;
    }

    public void setBiaoqiansx(String biaoqiansx) {
        this.biaoqiansx = biaoqiansx;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getBqid() {
        return bqid;
    }

    public void setBqid(String bqid) {
        this.bqid = bqid;
    }

    public String getSheetname() {
        return sheetname;
    }

    public void setSheetname(String sheetname) {
        this.sheetname = sheetname;
    }

    public Long getRowindex() {
        return rowindex;
    }

    public void setRowindex(Long rowindex) {
        this.rowindex = rowindex;
    }

    public Long getColumnindex() {
        return columnindex;
    }

    public void setColumnindex(Long columnindex) {
        this.columnindex = columnindex;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBiaoqianlx() {
        return biaoqianlx;
    }

    public void setBiaoqianlx(String biaoqianlx) {
        this.biaoqianlx = biaoqianlx;
    }

    public String getIshuixie() {
        return ishuixie;
    }

    public void setIshuixie(String ishuixie) {
        this.ishuixie = ishuixie;
    }

    public String getCellvalue() {
        return cellvalue;
    }

    public void setCellvalue(String cellvalue) {
        this.cellvalue = cellvalue;
    }

    public static Object returnForNewObject(Object updateObject, Object newOject)  {

        for (Field field : updateObject.getClass().getDeclaredFields()){
            field.setAccessible(true); // 设置些属性是可以访问的
            //  String type = field.getType().toString();// 得到此属性的类型
            String key = field.getName();// key:得到属性名
            Object value = null;// 得到此属性的值
            try {
                value = field.get(updateObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if(value!=null){
                //       System.out.println(key+"--key-------value--"+value);
                for (Field yfield : newOject.getClass().getDeclaredFields()){
                    try {
                        yfield.setAccessible(true); // 设置些属性是可以访问的
                        if (yfield.getName().equals(key) )
                        {
                            yfield.set(newOject,value);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return newOject;
    }
    public static  Map ObjectTOMap(Object o, Map map){
        for (Field field : o.getClass().getDeclaredFields()){
            field.setAccessible(true); // 设置些属性是可以访问的
            //  String type = field.getType().toString();// 得到此属性的类型
            String key = field.getName();// key:得到属性名
            Object value = null;// 得到此属性的值
            try {
                value = field.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(value!=null){
                map.put(key,value);
            }
        }
        return  map;
    }
}
