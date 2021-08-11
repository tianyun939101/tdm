package com.demxs.tdm.comac.common.constant.attach;

/**
 * 附件状态.
 * User: wuliepeng
 * Date: 2017-08-04
 * Time: 下午2:45
 */
public enum AttachmentStatus {
    //1:转换中,2:索引中,3:完成
    Convert(1,"转换中"),
    Index(2,"索引中"),
    Done(3,"完成");

    private Integer value;
    private String status;

    AttachmentStatus(Integer value,String status) {
        this.value = value;
        this.status = status;
    }

    public Integer getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }
}
