package com.demxs.tdm.common.file.bean;

/**
 * 不同格式图片存储路径实体
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class ImgBean {
    private String small; //小图的路径
    private String middle;//中图的路径
    private String large; //大图的路径

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}
