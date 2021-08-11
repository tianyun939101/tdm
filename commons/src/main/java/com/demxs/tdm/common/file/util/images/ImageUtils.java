package com.demxs.tdm.common.file.util.images;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片加水印，自动缩放等类<br>
 * 使用该类注意事项  <br>
 * 特别注意：  <br>
 * 配置环境变量  <br>
 * 换成 JAVA_OPTS="-Djava.awt.headless=true -server";export JAVA_OPTS  <br>
 *     java 原生api 图片处理封装
 */
public class ImageUtils {

    public static final int POS_TYPE_LEFT_TOP = 1;
    public static final int POS_TYPE_RIGHT_TOP = 2;
    public static final int POS_TYPE_LEFT_BOTTOM = 3;
    public static final int POS_TYPE_RIGHT_BOTTOM = 4;
    public static final int POS_TYPE_CENTER = 5;


    /**
     * 加水印。建议水印文件使用透明png格式
     *
     * @param sourceFile 被加水印的源文件
     * @param watermark  水印图片文件
     * @param targetFile 保存为
     * @param x          边距
     * @param y          边距
     * @param align      位置
     * @return 给图片加水印
     * @throws Exception 文件读写异常
     */
    public static boolean generateMark(String sourceFile, String watermark, String targetFile, int x, int y, int align) throws Exception {
        ImageIcon imageIcon = new ImageIcon(sourceFile);
        Image theImg = imageIcon.getImage();
        BufferedImage bimage = bufferedBothMark(theImg, watermark, null, x, y, align);
        write(bimage, targetFile);
        return true;
    }


    /**
     * 加水印(包含图片水印和文字水印)
     *
     * @param theImg    原来图像
     * @param watermark 水印文件
     * @param text      文印文字
     * @param x         x边距
     * @param y         y边距
     * @param align     位置
     * @return 描好后image
     */
    private static BufferedImage bufferedBothMark(Image theImg, String watermark, String text, int x, int y, int align) {
        int sourceWidth = theImg.getWidth(null);
        int sourceHeight = theImg.getHeight(null);


        BufferedImage bimage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = bimage.createGraphics();

        graphics2D.setColor(Color.white);

        graphics2D.drawImage(theImg, 0, 0, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1F));  //设置透明标记
        if (watermark != null) {
            ImageIcon waterIcon = new ImageIcon(watermark);
            Image waterImg = waterIcon.getImage();

            int waterImgWidth = waterImg.getWidth(null);
            int waterImgHeight = waterImg.getHeight(null);


            int waterX = calcWatermarkPosX(sourceWidth, waterImgWidth, x, align);
            int waterY = calcWatermarkPosY(sourceHeight, waterImgHeight, y, align);

            graphics2D.drawImage(waterImg, waterX, waterY, null);

            if (text != null && text.trim().length() > 0) {
                graphics2D.setFont(new Font("宋体", Font.PLAIN, waterImgHeight - 10));
                int textX, textY;
                if (align == POS_TYPE_LEFT_TOP || align == POS_TYPE_LEFT_BOTTOM) {
                    textX = waterX + waterImgWidth + x;
                    textY = waterY + waterImgHeight - 10;
                } else {
                    textX = waterX - waterImgHeight * text.length() - x;
                    textY = waterY + (waterImgHeight - 10);
                }

                graphics2D.drawString(text, textX, textY);

            }

        } else if (text != null && text.trim().length() > 0) {
            int waterX = calcWatermarkPosX(sourceWidth, 15 * text.length(), x, align);
            int waterY = calcWatermarkPosY(sourceHeight, 15, y, align);
            graphics2D.drawString(text, waterX, waterY);
        }


        graphics2D.dispose();
        return bimage;
    }


    /**
     * 计算水印左上角x轴位置..
     *
     * @param sourceImageWidth 原始图片宽度
     * @param waterImgWidth    水印宽度
     * @param x                边距
     * @param type             水印位置
     * @return int                 计算后的水印左上角x轴位置
     */
    private static  int calcWatermarkPosX(int sourceImageWidth, int waterImgWidth, int x, int type) {

        int result = 0;
        switch (type) {
            case POS_TYPE_LEFT_TOP:
            case POS_TYPE_LEFT_BOTTOM:
                result = x;
                break;
            case POS_TYPE_RIGHT_TOP:
            case POS_TYPE_RIGHT_BOTTOM:
                result = sourceImageWidth - (waterImgWidth + x);
                break;
            case POS_TYPE_CENTER:
                result = (sourceImageWidth - waterImgWidth) / 2;
                break;
        }

        result = (result >= 0 ? result : 0);
        return result;
    }


    /**
     * 计算水印左上角y轴位置
     *
     * @param sourceImageHeight 图片高度
     * @param waterImgHeight    水印高度
     * @param y                 边距
     * @param type              水印位置
     * @return int                  计算后的水印左上角y轴位置
     */
    private static  int calcWatermarkPosY(int sourceImageHeight, int waterImgHeight, int y, int type) {

        int result = 0;
        switch (type) {
            case POS_TYPE_LEFT_TOP:
            case POS_TYPE_RIGHT_TOP:
                result = y;
                break;
            case POS_TYPE_LEFT_BOTTOM:
            case POS_TYPE_RIGHT_BOTTOM:
                result = sourceImageHeight - (waterImgHeight + y);
                break;
            case POS_TYPE_CENTER:
                result = (sourceImageHeight - waterImgHeight) / 2;
                break;
        }

        result = (result >= 0 ? result : 0);
        return result;
    }

    /**
     * 缩放本地图片，缩放时会进行适当的裁剪
     *
     * @param picFrom 图片来自
     * @param picTo   图片缩放后放到
     * @param width   缩放后的宽度
     * @param height  缩放后的高度
     * @return boolean      是否缩放成功
     * @throws Exception 文件读写异常，缩放异常
     */
    public static  boolean resize(String picFrom, String picTo, int width, int height) throws Exception {

        FileInputStream input = null;
        try {
            input = new FileInputStream(picFrom);
            return resize(input, picTo, width, height);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {

                }
            }
        }


    }


    /**
     * 缩放图片，缩放时会进行适当的裁剪
     * Insert the method's description here.
     * Creation date: (8/12/01 6:28:37 PM)
     *
     * @param input
     * @param to      输出
     * @param scaledW 缩放后宽度
     * @param scaledH 缩放后高度
     * @return 是否成功
     * @throws Exception 文件读写异常
     */
    public static  boolean resize(InputStream input, String to, int scaledW, int scaledH) throws Exception {

        BufferedImage bufferedImage = ImageIO.read(input);
        if (bufferedImage == null) {         //可能是非法图片格式
            return false;
        }
        BufferedImage writeBuffered = resizeBuffered(bufferedImage, scaledW, scaledH, false);
        write(writeBuffered, to);

        return true;

    }

    public static BufferedImage resizeBuffered(BufferedImage bufferedImage, int scaledW, int scaledH, boolean cut) {
        float fromWidth = (float) bufferedImage.getWidth();
        float fromHeight = (float) bufferedImage.getHeight();
        float percent = (float) scaledW / (float) scaledH;
        float fromPercent = fromWidth / fromHeight;

        if ((int) (fromPercent * 100) != (int) (percent * 100)) { //表示不能等比缩放
            if (fromPercent > percent) {//表示目标图要窄一些。既然窄一些，就要cut宽
                if (cut) {
                    float dstWidth = fromHeight * percent;
                    bufferedImage = crop(bufferedImage, new Rectangle((int) (fromWidth - dstWidth) / 2, 0, (int) dstWidth, (int) fromHeight));
                } else {
                    scaledH = (int) (fromHeight / (fromWidth / scaledW));
                }
            } else { //这就意味着目标图要宽一些，既然要宽一些就要cut高
                if (cut) {
                    float dstHeight = fromWidth / percent;
                    bufferedImage = crop(bufferedImage, new Rectangle(0, (int) (fromHeight - dstHeight) / 2, (int) fromWidth, (int) dstHeight));
                } else {
                    scaledW = (int) (fromWidth / (fromHeight / scaledH));
                }
            }
        }

        Image img = bufferedImage.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);

        return toBufferedImage(img, scaledW, scaledH);
    }

    /**
     * 将缓冲区写入文件
     *
     * @param bi   图片
     * @param file 指定的文件名
     * @throws Exception 例外
     */
    public static  void write(BufferedImage bi, String file) throws Exception {
        ImageOutputStream ios = null;
        JPEGImageWriter iw = null;
        try {
            ios = ImageIO.createImageOutputStream(new File(file));
            iw = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();
            iw.setOutput(ios);


            JPEGImageWriteParam p = (JPEGImageWriteParam) iw.getDefaultWriteParam();
            p.setOptimizeHuffmanTables(true);
            p.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            p.setCompressionQuality(0.9f);
            IIOImage i = new IIOImage(bi, null, null);
            iw.write(null, i, p);
        } finally {
            if (ios != null) {
                try {
                    ios.close();
                } catch (IOException e) {

                }
            }
            if (iw != null) {
                iw.dispose();
            }
        }
    }


    /**
     * 将image轮换成bufferedimage
     *
     * @param image   图片缓冲区
     * @param scaledW
     * @param scaledH @return BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image, int scaledW, int scaledH) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;

        }

        BufferedImage bufferedImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);

        return bufferedImage;


    }

    /**
     * 图像裁剪。
     *
     * @param srcImage 源图像。
     * @param rect     裁剪区域。
     * @return 裁剪后的图像。
     */
    public static BufferedImage crop(BufferedImage srcImage, Rectangle rect) {
        return srcImage.getSubimage(rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * 缩放图片并且打图片水印
     *
     * @return 是否成功
     */
    public static boolean resizeAndMark(String picFrom, String picTo, String mark, int width, int height) throws Exception {
        BufferedImage bufferedImage = getBufferedImage(picFrom);
        if (bufferedImage == null) {         //可能是非法图片格式
            return false;
        }
        bufferedImage = resizeBuffered(bufferedImage, width, height, false);
        bufferedImage = bufferedBothMark(bufferedImage, mark, null, 5, 5, POS_TYPE_RIGHT_BOTTOM);
        write(bufferedImage, picTo);
        return true;
    }

    /**
     * 缩放图片并且打图片水印
     * @param picFrom
     * @param picTo
     * @param mark
     * @param width 图片的max width 超过则会引发缩放动作
     * @param height 图片的max height 超过则会引发缩放动作
     * @return
     * @throws Exception
     */
    public static boolean resizeMaxAndMarkCenter(String picFrom, String picTo, String mark, int width, int height) throws Exception {
        BufferedImage bufferedImage = getBufferedImage(picFrom);
        if (bufferedImage == null) {         //可能是非法图片格式
            return false;
        }
        float fromWidth = (float) bufferedImage.getWidth();
        float fromHeight = (float) bufferedImage.getHeight();
        if(fromWidth>width || fromHeight>height){
            bufferedImage = resizeBuffered(bufferedImage, width, height, false);
        }
        bufferedImage = bufferedBothMark(bufferedImage, mark, null, 5, 5, POS_TYPE_CENTER);
        write(bufferedImage, picTo);
        return true;
    }

    /**
     * 缩放图片并且打图片水印
     * @param bufferedImage
     * @param picTo
     * @param mark
     * @param width 图片的max width 超过则会引发缩放动作
     * @param height 图片的max height 超过则会引发缩放动作
     * @return
     * @throws Exception
     */
    public static boolean resizeMaxAndMarkCenter(BufferedImage bufferedImage, String picTo, String mark, int width, int height) throws Exception {
        if (bufferedImage == null) {         //可能是非法图片格式
            return false;
        }
        float fromWidth = (float) bufferedImage.getWidth();
        float fromHeight = (float) bufferedImage.getHeight();
        if(fromWidth>width || fromHeight>height){
            bufferedImage = resizeBuffered(bufferedImage, width, height, false);
        }
        bufferedImage = bufferedBothMark(bufferedImage, mark, null, 5, 5, POS_TYPE_CENTER);
        write(bufferedImage, picTo);
        return true;
    }



    /**
     * 缩放图片并且打文字水印
     *
     * @return 是否成功
     */
    public static boolean resizeAndTextMark(String picFrom, String picTo, String text, int width, int height) throws Exception {

        BufferedImage bufferedImage = getBufferedImage(picFrom);
        if (bufferedImage == null) {         //可能是非法图片格式
            return false;
        }
        bufferedImage = resizeBuffered(bufferedImage, width, height, false);
        bufferedImage = bufferedBothMark(bufferedImage, null, text, 5, 5, POS_TYPE_RIGHT_BOTTOM);
        write(bufferedImage, picTo);
        return true;
    }

    /**
     * 缩放图片并且打文字+图片水印
     *
     * @return 是否成功
     */
    public static boolean resizeAndBothMark(String picFrom, String picTo, String mark, String text, int width, int height) throws Exception {

        BufferedImage bufferedImage = getBufferedImage(picFrom);
        if (bufferedImage == null) {         //可能是非法图片格式
            return false;
        }
        bufferedImage = resizeBuffered(bufferedImage, width, height, false);
        bufferedImage = bufferedBothMark(bufferedImage, mark, text, 5, 5, POS_TYPE_RIGHT_BOTTOM);
        write(bufferedImage, picTo);
        return true;
    }

    public static BufferedImage getBufferedImage(String picFrom) throws IOException {
        InputStream input = null;
        BufferedImage bufferedImage = null;
        try {
            input = new FileInputStream(picFrom);
            bufferedImage = ImageIO.read(input);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {

                }
            }
        }
        return bufferedImage;
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ImageUtils.resizeMaxAndMarkCenter("F:\\test-pic\\ccc.png", "F:\\test-pic\\reccc.jpg","F:\\test-pic\\ware.png", 743, 800); //正常图片
        long end = System.currentTimeMillis();
        long used = end - start;
        System.out.println("used = " + used);
//        utils.resize("struts2demo.xml", "to1.jpg", 320, 240);    //非图片

    }
}
