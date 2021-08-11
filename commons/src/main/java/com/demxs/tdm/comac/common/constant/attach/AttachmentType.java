package com.demxs.tdm.comac.common.constant.attach;

/**
 * 附件类型.
 * User: wuliepeng
 * Date: 2017-08-04
 * Time: 下午2:27
 */
public enum AttachmentType {
    Knowledge(2,"知识"),    //知识类型
    Other(1,"附件");       //其它类型

    private Integer value;
    private String type;

    AttachmentType(Integer value,String type){
        this.value = value;
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
