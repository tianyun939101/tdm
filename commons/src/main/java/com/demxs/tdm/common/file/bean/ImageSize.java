package com.demxs.tdm.common.file.bean;

import java.io.Serializable;

/**
 * 图片大小转换
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class ImageSize implements Serializable {
    private int smallWith = 100;//小图宽
    private int smallHeight = 100; //小图高
    private int midWith = 450;//中图宽
    private int midHeight = 450;//中图高
    private int largeWith = 800;//大图宽
    private int largeHeight = 800;//大图高

    public int getSmallWith() {
        return smallWith;
    }

    public void setSmallWith(int smallWith) {
        this.smallWith = smallWith;
    }

    public int getSmallHeight() {
        return smallHeight;
    }

    public void setSmallHeight(int smallHeight) {
        this.smallHeight = smallHeight;
    }

    public int getMidWith() {
        return midWith;
    }

    public void setMidWith(int midWith) {
        this.midWith = midWith;
    }

    public int getMidHeight() {
        return midHeight;
    }

    public void setMidHeight(int midHeight) {
        this.midHeight = midHeight;
    }

    public int getLargeWith() {
        return largeWith;
    }

    public void setLargeWith(int largeWith) {
        this.largeWith = largeWith;
    }

    public int getLargeHeight() {
        return largeHeight;
    }

    public void setLargeHeight(int largeHeight) {
        this.largeHeight = largeHeight;
    }
}
