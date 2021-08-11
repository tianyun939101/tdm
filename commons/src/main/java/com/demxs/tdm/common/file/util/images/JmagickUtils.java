package com.demxs.tdm.common.file.util.images;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;

/***
 *  Jmagick 工具类
 */
public class JmagickUtils {
    static {
        System.setProperty("jmagick.systemclassloader", "no");
    }
    private final static Log log = LogFactory.getLog(JmagickUtils.class);
    /**
     * 图片缩放。非等比缩放
     *
     * @param in     需要被缩放的图片的完整路径
     * @param out    缩放后存放到
     * @param width  指定宽度
     * @param height 指定高度
     * @return 是否成功
     */
    public static boolean forceResize(String in, String out, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Cann't height or width is less than 0");
        }
        return resize(in, out, width, height, false);
    }

    /**
     * 图片缩放。等比缩放。
     * 宽度或者高度可以指定为0。这样就会以等比缩放对应的高度或者宽度
     * 如果两个都有，则表示为缩放的宽度或者高度不超过指定的宽度或者高度
     *
     * @param in     需要被缩放的图片的完整路径
     * @param out    缩放后存放到
     * @param width  指定宽度
     * @param height 指定高度
     * @return 是否成功
     */
    public static boolean resize(String in, String out, int width, int height) {
        return resize(in, out, width, height, true);
    }

    private static boolean resize(String in, String out, int width, int height, boolean proportion) {
        if (width <= 0 && height <= 0) {
            throw new IllegalArgumentException("Cann't both height and width is less than 0");
        }
        MagickImage image=null;
        MagickImage scaled=null;// 小图片文件的大小.
        try {
            ImageInfo info = new ImageInfo(in);
            image = new MagickImage(info);
            Dimension dimension = image.getDimension();
            if (proportion) {
                if (width <= 0) {
                    width = (int) (((double) height / dimension.getHeight()) * dimension.getWidth());
                } else if (height <= 0) {
                    height = (int) (((double) width / dimension.getWidth()) * dimension.getHeight());
                } else {
                    double sw = (double) width / dimension.getWidth();
                    double sh = (double) height / dimension.getHeight();
                    // 则将下面的if else语句注释即可
                    if (sw > sh) /* 当宽长宽则压小*/ {
                        width = (int) (sh * dimension.getWidth());
                    } else if (sh > sw) {
                        height = (int) (sw * dimension.getHeight());
                    }
                }
            }

            scaled = image.scaleImage(width, height);
            scaled.setFileName(out);
            return scaled.writeImage(info);
        } catch (MagickException e) {
            throw new RuntimeException("resize error!", e);
        } finally {
            if (scaled!=null) {
                try {
                    scaled.destroyImages();
                } catch (Exception e) {
                    log.error("destroyImages error!",e);
                }
            }
            if (image!=null) {
                try {
                    image.destroyImages();
                } catch (Exception e) {
                    log.error("destroyImages error!",e);
                }
            }
        }

    }
}
