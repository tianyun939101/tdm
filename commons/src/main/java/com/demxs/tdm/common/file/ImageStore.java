package com.demxs.tdm.common.file;

import com.demxs.tdm.common.file.bean.ImageSize;
import com.demxs.tdm.common.file.bean.ImgBean;
import com.demxs.tdm.common.file.util.images.ImageTools;
import com.demxs.tdm.common.file.util.images.ImageUtils;
import com.demxs.tdm.common.utils.MrandomUtil;
import com.demxs.tdm.common.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片存储
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class ImageStore {
    private final static Log log = LogFactory.getLog(ImageStore.class);
    private FileStore fileStore;
    //GraphicsMagick 安装目录  windows 平台需要  linux不需要
    private String gmPath;

    public static final class ImageType{
        public static final int SMALL =0;
        public static final int MIDDLE =1;
        public static final int LARGE =2;
    }

    /**
     *  删除图片
     * @param path
     * @return
     */
    public boolean deleteImg(String path){
        return fileStore.deleteFile(path);
    }

    public String copyImg(String srcPath, Long vendorId) throws Exception {
        return fileStore.copyFile(srcPath);
    }
    /**
     *  删除临时图片文件
     * @param path
     * @return
     */
    public boolean deleteTempImg(String path){
        return fileStore.deleteFile(path);
    }
    /***
     * 临时图片存储  会定期删除  暂定清空 一星期前的订单
     * @param vendorId
     * @param files
     * @return
     */
    public List<String> uploadTempImgNoCompress(Long vendorId, List<File> files){
        if(vendorId==null || CollectionUtils.isEmpty(files)){
            throw new IllegalArgumentException("vendorId or files can not be null");
        }
        List<String> list = new ArrayList<String>();
        try {
            for (File file : files){
                String newFileName = MrandomUtil.getUUID()+ "."+ FilenameUtils.getExtension(file.getName());
                File dst = new File(file.getParent()+"/"+newFileName);
                file.renameTo(dst);
                String s = fileStore.saveFile(dst);
                list.add(s);
                if(dst.exists()){
                    FileUtils.forceDelete(dst);
                }
            }
        } catch (Exception e) {
            log.error("upload Temp Images NoCompress Exception",e);
            return null;
        }
        return list;
    }
    /**
     * 上传不需要压缩的图片 文件
     * @param vendorId  商家ID
     * @param files  需要上传的图片文件
     * @return  图片文件路径名
     */
    public List<String> uploadImgNoCompress(Long vendorId, List<File> files){
        if(vendorId==null || CollectionUtils.isEmpty(files)){
            throw new IllegalArgumentException("vendorId or files can not be null");
        }
        List<String> list = new ArrayList<String>();
        try {
            for (File file : files){
                String newFileName = MrandomUtil.getUUID()+ "."+ FilenameUtils.getExtension(file.getName());
                File dst = new File(file.getParent()+"/"+newFileName);
                file.renameTo(dst);
                String s = fileStore.saveFile(dst);
                list.add(s);
                if(dst.exists()){
                    FileUtils.forceDelete(dst);
                }
            }
        } catch (Exception e) {
            log.error("upload Images NoCompress Exception",e);
            return null;
        }
        return list;
    }

    /***
     * 上传需要压缩的图片  需要制定尺寸
     * @param vendorId
     * @param files
     * @return
     */
    public List<String> uploadImgByCompress(Long vendorId, List<File> files, int width, int height){
        if(width==0 && height==0){
            throw  new IllegalArgumentException("width and height can not equal 0");
        }
        if(vendorId==null || CollectionUtils.isEmpty(files)){
            throw new IllegalArgumentException("vendorId or files can not be null");
        }
        List<String> list = new ArrayList<String>();
        try {
            for (File file : files){
                String newFileName = MrandomUtil.getUUID()+ "."+ FilenameUtils.getExtension(file.getName());
                File base = files.get(0).getParentFile();
                String nFile = base.getPath()+"/"+ newFileName;
                File ossFile = null;
                ImageSize size = getSizeByImage(file);
                if(size.getSmallHeight()<= height || size.getSmallWith()<= width){
                    ossFile = new File(nFile);
                    FileUtils.copyFile(file,ossFile);
                    log.error("no need compress.original image is smaller than target");
                }else{
                    //判断使用哪种压缩工具进行压缩
                    //gmPath 不等空 或者 Linux 平台
                    if(StringUtils.isNotEmpty(gmPath)|| SystemUtils.IS_OS_LINUX){
                        if(SystemUtils.IS_OS_WINDOWS && new File(gmPath).exists()){
                            //windows 下 需要设置  GraphicsMagick 路劲
                            ImageTools.gmPath = gmPath;
                        }
                        log.info("deal image with GraphicsMagick");
                        ImageTools.resize(file.getAbsolutePath(),nFile,width,height);
                    }else{
                        log.info("deal image with java api");
                        //java  压缩 宽高 都不能为0  需要计算出来
                        if (width != 0 && height==0) {
                            // 根据设定的宽度求出长度
                            height = (width * size.getSmallHeight()) / size.getSmallWith();
                        }
                        if (height != 0 && width ==0) {
                            // 根据设定的长度求出宽度
                            width = (height * size.getSmallWith()) / size.getSmallHeight();
                        }
                        ImageUtils.resize(file.getAbsolutePath(), nFile, width, height);
                    }
                    ossFile = new File(nFile);
                }

                String s = fileStore.saveFile(ossFile);
                list.add(s);
                if(file.exists()){
                    FileUtils.forceDelete(file);
                }
                if(ossFile.exists()){
                    FileUtils.forceDelete(ossFile);
                }
            }
        } catch (Exception e) {
            log.error("upload Images NoCompress Exception",e);
            return null;
        }
        return list;
    }

    private ImageSize getSizeByImage(File file) throws Exception {
        ImageSize size = new ImageSize();
        BufferedImage bufferedImage = javax.imageio.ImageIO.read(new FileInputStream(file));
        size.setSmallWith(bufferedImage.getWidth());
        size.setSmallHeight(bufferedImage.getHeight());
        return size;
    }

    /***
     * 上传商品图片
     * 自动压缩成 小 中 大
     * @param files
     * @return
     */
    public List<ImgBean> uploadImages(List<File> files, ImageSize imageSize){
        List<ImgBean> beans = new ArrayList<ImgBean>();

        if(imageSize==null){
            imageSize = new ImageSize();
        }
        //压缩文件存储目录
        try {
            File base = files.get(0).getParentFile();
            for (File file : files){
                String newFileName = MrandomUtil.getUUID()+ "."+ FilenameUtils.getExtension(file.getName());
                String smallPath = base.getPath()+"/s_"+ newFileName;
                String middlePath = base.getPath()+"/m_"+ newFileName;
                String largePath = base.getPath()+"/l_"+ newFileName;
                //判断使用哪种压缩工具进行压缩
                //gmPath 不等空 或者 Linux 平台
                if(StringUtils.isNotEmpty(gmPath)|| SystemUtils.IS_OS_LINUX){
                    if(SystemUtils.IS_OS_WINDOWS && new File(gmPath).exists()){
                        //windows 下 需要设置  GraphicsMagick 路劲
                        ImageTools.gmPath = gmPath;
                    }
                    log.info("deal image with GraphicsMagick");
                    ImageTools.resize(file.getAbsolutePath(),smallPath,imageSize.getSmallWith(),imageSize.getSmallHeight());
                    ImageTools.resize(file.getAbsolutePath(),middlePath,imageSize.getMidWith(),imageSize.getMidHeight());
                    ImageTools.resize(file.getAbsolutePath(),largePath,imageSize.getLargeWith(),imageSize.getLargeHeight());
                }else{
                    log.info("deal image with java api");
                    ImageUtils.resize(file.getAbsolutePath(),smallPath,imageSize.getSmallWith(),imageSize.getSmallHeight());
                    ImageUtils.resize(file.getAbsolutePath(),middlePath,imageSize.getMidWith(),imageSize.getMidHeight());
                    ImageUtils.resize(file.getAbsolutePath(),largePath,imageSize.getLargeWith(),imageSize.getLargeHeight());
                }
                File sFile = new File(smallPath);
                File mFile = new File(middlePath);
                File lFile = new File(largePath);
                String s = fileStore.saveFile(sFile);
                String m = fileStore.saveFile(mFile);
                String l = fileStore.saveFile(lFile);
                ImgBean imgBean = new ImgBean();
                imgBean.setSmall(s);
                imgBean.setMiddle(m);
                imgBean.setLarge(l);
                beans.add(imgBean);
                if(file.exists()){
                    FileUtils.forceDelete(file);
                }
                if(sFile.exists()){
                    FileUtils.forceDelete(sFile);
                }
                if(mFile.exists()){
                    FileUtils.forceDelete(mFile);
                }
                if(lFile.exists()){
                    FileUtils.forceDelete(lFile);
                }
            }
        } catch (Exception e) {
            log.error("upload Images Exception",e);
            return null;
        }
        return beans;
    }

    public boolean deleteImages(String... paths){
        boolean delFlag = false;
        for (String path:paths){
            delFlag = fileStore.deleteFile(path);
            if(!delFlag){
                return false;
            }
        }
        return true;
    }


    public void setFileStore(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    public void setGmPath(String gmPath) {
        this.gmPath = gmPath;
    }
}
