package com.demxs.tdm.common.utils;

import com.demxs.tdm.common.config.Global;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;
import java.util.List;

/**
 * 解压缩文件
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 下午2:49
 */
public class CompressFile {

    /**
     * 压缩文件
     *
     * @param srcfile
     *            File[] 需要压缩的文件列表
     * @param zipfile
     *            File 压缩后的文件
     */
    public static void ZipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件
     *
     * @param files
     *            List<FileBean> 需要压缩的文件列表
     * @param zipfile
     *            File 压缩后的文件
     */
    public static void ZipFiles(List<FileBean> files, File zipfile) {
        try {
            FileOutputStream outputStream = new FileOutputStream(zipfile);
            ZipOutputStream out = new ZipOutputStream(outputStream);
            for(FileBean file : files){
                out.putNextEntry(new ZipEntry(file.getFileName()));
                out.write(file.getContent());
                out.closeEntry();
            }
            out.close();
            outputStream.close();
            System.out.println("压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * zip解压缩
     *
     * @param zipfile
     *            File 需要解压缩的文件
     * @param descDir
     *            String 解压后的目标目录
     */
    public static void unZipFiles(File zipfile, String descDir) {
        try {
            ZipFile zf = new ZipFile(zipfile,
                    Global.getConfig("zip.encode"));
            for (@SuppressWarnings("rawtypes")
                 Enumeration entries = zf.getEntries(); entries.hasMoreElements();) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String zipEntryName = entry.getName();
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = zf.getInputStream(entry);
                    out = new FileOutputStream(descDir
                            + zipEntryName.replaceAll("\\\\", File.separator)
                            .replaceAll("/", File.separator));
                    byte[] buf1 = new byte[1024];
                    int len;
                    while ((len = in.read(buf1)) > 0) {
                        out.write(buf1, 0, len);
                    }

                } catch (Exception e) {

                } finally {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }
                // System.out.println("解压缩完成.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据原始rar路径，解压到指定文件夹下.
     *
     * @param srcRarPath
     *            原始rar路径
     * @param dstDirectoryPath
     *            解压到的文件夹
     */
    public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
        File dstDiretory = new File(dstDirectoryPath);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        Archive a = null;
        try {
            a = new Archive(new File(srcRarPath));
            if (a != null) {
                // a.getMainHeader().print(); // 打印文件信息.
                FileHeader fh = a.nextFileHeader();
                while (fh != null) {
                    if (fh.isDirectory()) { // 文件夹
                        File fol = new File(dstDirectoryPath + File.separator
                                + fh.getFileNameString());
                        fol.mkdirs();
                    } else { // 文件
                        File out = new File(dstDirectoryPath + File.separator
                                + fh.getFileNameW().trim());
                        // System.out.println(out.getAbsolutePath());
                        try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
                            if (!out.exists()) {
                                if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
                                    out.getParentFile().mkdirs();
                                }
                                out.createNewFile();
                            }
                            FileOutputStream os = new FileOutputStream(out);
                            a.extractFile(fh, os);
                            os.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    fh = a.nextFileHeader();
                }
                a.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
