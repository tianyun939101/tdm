package com.demxs.tdm.common.utils;

import java.io.*;

/**
 * @author: Jason
 * @Date: 2020/7/9 15:41
 * @Description:
 */
public class IOUtil {

    private final static int ARRAY_LENGTH = 1024 * 1024;

    public static void writeByteToFile(InputStream is, String path) throws IOException {
        writeByteToFile(is,new File(path));
    }

    public static void writeByteToFile(InputStream is, File file) throws IOException {
        writeByteToFile(is,new FileOutputStream(file));
    }

    public static void writeByteToFile(InputStream is, OutputStream os) throws IOException {
        int len;
        byte[] data = new byte[ARRAY_LENGTH];
        while ((len = is.read(data)) != -1){
            os.write(data,0,len);
        }
        os.flush();
        disConnect(is);
        disConnect(os);
    }

    private static void disConnect(Closeable closeable){
        try {
            closeable.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
