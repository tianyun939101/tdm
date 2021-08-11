package com.demxs.tdm.common.file.util.images;


import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.file.bean.FileType;
import org.apache.commons.lang3.SystemUtils;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import java.util.UUID;

/**
 * im4java + GraphicsMagick 处理图片封装类
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class ImageTools {
    public static String gmPath = "";
    //加水印
    /***
     * 同比图片缩放
     * 宽度 高度 必须至少一个值>0
     * 若宽度与高度 不按同比设置，默认按宽度同比缩放
     * @param in  输入图片地址
     * @param out 输出的图片地址
     * @param width 图片宽度  宽度<=0 则按高度同比例缩放
     * @param height 图片高度  高度<=0 则按宽度同比例缩放
     * @throws Exception
     */
    public static void resize(String in, String out, int width, int height) throws Exception {
        if (width <= 0 && height <= 0) {
            throw new IllegalArgumentException("height or width is less than 0");
        }
        IMOperation op = new IMOperation();
        op.addImage(in);
        op.addRawArgs("-strip"); //去除图片信息及注解
        op.addRawArgs("-quality", "90"); // 设置图片质量
        if(width<=0){
            op.resize(null, height);
        }else if(height<=0){
            op.resize(width, null);
        }else{
            op.resize(width,height);
        }
        op.addImage(out);
        //false - 不使用  GraphicsMagick   true 使用
        ConvertCmd convert = new ConvertCmd(true);
        //linux下 不需要设置
        if(SystemUtils.IS_OS_WINDOWS){
            ConvertCmd.setGlobalSearchPath(gmPath);
        }
        convert.run(op);
    }
    /**
     * 根据坐标裁剪图片
     *
     * @param srcPath   要裁剪图片的路径
     * @param newPath   裁剪图片后的路径
     * @param x   起始横坐标
     * @param y   起始挫坐标
     * @param x1  结束横坐标
     * @param y1  结束挫坐标
     */
    public static void cutImage(String srcPath, String newPath, int x, int y, int x1,
                                int y1)  throws Exception {
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.addRawArgs("-strip"); //去除图片信息及注解
        op.addRawArgs("-quality", "90"); // 设置图片质量
        /**
         * width：裁剪的宽度
         * height：裁剪的高度
         * x：裁剪的横坐标
         * y：裁剪的挫坐标
         */
        op.crop(width, height, x, y);
        op.addImage(newPath);
        ConvertCmd convert = new ConvertCmd(true);
        //linux下不要设置此值，不然会报错
        if(SystemUtils.IS_OS_WINDOWS){
            ConvertCmd.setGlobalSearchPath(gmPath);
        }
        convert.run(op);
    }
    /**
     * 先缩放，后居中切割图片
     * @param srcPath 源图路径
     * @param desPath 目标图保存路径
     * @param width 待切割在宽度
     * @param height 待切割在高度
     * @param location 切割位置
     * @throws Exception
     */
    public static void cropImageCenter(String srcPath, String desPath, int width, int height, String location)
            throws Exception {
        if(StringUtils.isEmpty(location)){
            location = FileType.Location.Center;
        }
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        op.resize(width, height, '^').gravity(location).extent(width, height);
        op.addRawArgs("-strip"); //去除图片信息及注解
        op.addRawArgs("-quality", "90"); // 设置图片质量
        op.addImage(desPath);
        ConvertCmd convert = new ConvertCmd(true);
        //linux下不要设置此值，不然会报错
        if(SystemUtils.IS_OS_WINDOWS){
            ConvertCmd.setGlobalSearchPath(gmPath);
        }
        convert.run(op);
    }

    /***
     * 加水印  并同比压缩图片  若只添加水印  宽度 高度 设置0或负数
     * @param waterPic 水印图片路径
     * @param src  源文件
     * @param dec  目标文件
     * @param width   宽度
     * @param height 高度
     * @param location  水印图片位置
     * @throws Exception
     */
    public static void waterMark(String waterPic, String src, String dec, int width, int height, String location) throws Exception {
        IMOperation op = new IMOperation();
        if(StringUtils.isEmpty(location)){
            location = FileType.Location.LEFT_BOTTOM;
        }
        op.gravity(location).dissolve(80);
        op.addImage(waterPic);
        op.addImage(src);
        if(width>0 && height>0){
            op.resize(width, height, '^');
        }
        op.addImage(dec);
        CompositeCmd cmd = new CompositeCmd(true);
        //linux下不要设置此值，不然会报错
        if(SystemUtils.IS_OS_WINDOWS){
            CompositeCmd.setGlobalSearchPath(gmPath);
        }
        cmd.run(op);
    }
    public static void  main(String[] args) throws Exception {
//        resize("D:\\test.jpg","D:\\test600w.jpg",600,0);
//        resize("D:\\test.jpg","D:\\test1000h.jpg",0,1000);
//        resize("D:\\test.jpg","D:\\test600-5000.jpg",600,5000);
//        cutImage("D:\\test.jpg","D:\\test6-000.jpg",600,500,0,100);

        System.out.print(UUID.randomUUID());
          gmPath = "D:\\Program Files (x86)\\GraphicsMagick-1.3.21-Q8";
        cropImageCenter("D:\\test1.jpg","D:\\test200-200.jpg",200,200,FileType.Location.LEFT_BOTTOM);
//        resize("D:\\test.jpg","D:\\test600-500.jpg",600,500);
//        waterMark("D:\\arc.png","D:\\test1.jpg","D:\\testWater22.jpg",200,200, FileType.Location.Center);
//        ImageUtils.resizeAndTextMark("D:\\test.jpg","D:\\testWater1.jpg","金米世家",600,300);
    }
}
