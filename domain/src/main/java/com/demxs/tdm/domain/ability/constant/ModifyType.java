package com.demxs.tdm.domain.ability.constant;

/**
 * @author: Jason
 * @Date: 2020/8/4 15:50
 * @Description:
 */
public enum ModifyType {
    INSERT(0,"新增"),
    UPDATE(1,"修改"),
    DELETE(2,"删除"),
    ;
    private Integer code;
    private String title;

    ModifyType(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public static ModifyType get(String code){
        if(code == null || code.length() == 0){
            return null;
        }
        ModifyType[] values = ModifyType.values();
        for (ModifyType value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public ModifyType setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ModifyType setTitle(String title) {
        this.title = title;
        return this;
    }
}
