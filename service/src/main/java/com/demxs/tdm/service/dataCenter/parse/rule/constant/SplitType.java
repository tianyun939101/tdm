package com.demxs.tdm.service.dataCenter.parse.rule.constant;

public enum SplitType {
    //默认逗号
    DEFAULT("0",","),
    //空格
    SPACE("1"," "),
    //制表符
    TAB("2","\t"),
    //冒号
    COLON("3",":");
    private String value;
    private String txt;

    SplitType(String value, String txt) {
        this.value = value;
        this.txt = txt;
    }

    public static SplitType get(String value){
        for (SplitType type: SplitType.values()){
            if(type.getValue().equals(value)){
                return type;
            }
        }
        return DEFAULT;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
