package com.demxs.tdm.comac.common.constant;

/**
 * @author: wuhui
 * @Date: 2020/12/17 09:55
 * @Description:
 */
public enum RemovalTypeEnum {

    REMOVAL("01","拆分"),
    COPY("02","复制"),
    ;

    private String type;
    private String name;

    RemovalTypeEnum(String type,String name){
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean isType(RemovalTypeEnum type,String value){
        return type.getType().equals(value);
    }
}
