package com.demxs.tdm.comac.common.constant;

/**
 * @author: Jason
 * @Date: 2020/5/12 15:01
 * @Description:
 */
public enum StatusEnum {

    SUCCESS("200","成功"),
    MISSING("404","未找到"),
    FAILED("400","失败"),
    ERROR("500","错误"),
    ;

    private String status;
    private String message;

    StatusEnum(String status,String msg){
        this.status = status;
        this.message = msg;
    }

    public StatusEnum get(String status){
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum value : values) {
            if(value.status.equals(status)){
                return value;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public StatusEnum setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StatusEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
