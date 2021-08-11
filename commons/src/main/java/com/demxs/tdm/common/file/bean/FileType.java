package com.demxs.tdm.common.file.bean;

/**
 * 文件类型
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class FileType {
    public static final int IMAGE = 0;//图片 使用CDN
    public static final int UPGRADE_PACK = 1 ;//升级包文件  不使用CDN
    public static final int ATTACHED_FILE = 2;//其他附件文件 不使用CDN
    public static final int TEMP_FILE = 3;//临时存储文件 需要定时删除

    public static final class Location {
        //左上
        public static final String LEFT_TOP = "NorthWest";
        //上
        public static final String TOP = "North";
        //右上
        public static final String RIGHT_TOP = "NorthEast";
        //左
        public static final String LEFT = "West";
        //中
        public static final String Center = "Center";
        //
        public static final String RIGHT = "East";
        public static final String LEFT_BOTTOM  = "SouthWest";
        public static final String BOTTOM = "South";
        public static final String RIGHT_BOTTOM = "SouthEast";
    }
}
