package com.demxs.tdm.domain.configure;

/**
 * @Author： wbq
 * @Description：
 * @Date：Create in 2017-09-15 10:30.
 * @Modefied By：
 */
public class TongjiSeries {
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private String name;
    private String type;
    private Double[] data;
    private String strdata;

    public String getStrdata() {
        return strdata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double[] getData() {
        return data;
    }

    public void setData(Double[] data) {
        this.data = data;
        String temp = "[";
        String strd = "";
        for(int i=0;i<data.length;i++){
            strd+=data[i]+",";
        }
        if (strd.length()>0){
            strd=strd.substring(0,strd.length()-1);
        }
        this.strdata = temp+strd+"]";
    }
}
