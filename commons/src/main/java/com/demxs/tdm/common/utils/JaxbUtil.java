package com.demxs.tdm.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * @author wuhui
 * @date 2020/9/18 14:02
 **/
public class JaxbUtil {
    private static final Logger logger = LoggerFactory.getLogger(JaxbUtil.class);

    /**
     * @param t 要生成xml的实体类
     * @param file 要生成的文件对象
     * @param encoding 数据格式编码
     * @return 是否成生成对应的xml
     */
    public static <T> boolean createXml(T t,File file,String encoding){
        if(file==null) return false;
        if(!file.getParentFile().exists()){
            boolean mkdirs = file.getParentFile().mkdirs();
            logger.debug("mkdirs {} is {}",file.getParent(),mkdirs?"success":"failed");
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            JAXBContext instance = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = instance.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,encoding);
            marshaller.marshal(t,fileWriter);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            logger.error(getException(e));
            return false;
        }
    }

    /**
     * @param t 要生成xml的实体类
     * @param filePath 要生成的文件路径
     * @param encoding 数据格式编码
     * @return 是否成生成对应的xml
     */
    public static <T> boolean createXml(T t,String filePath,String encoding){
        return createXml(t,new File(filePath),encoding);
    }

    /**
     * @param t 要生成xml的实体类
     * @param filePath 要生成的文件路径
     * @return 是否成生成对应的xml
     */
    public static <T> boolean createXml(T t,String filePath){
        return createXml(t,new File(filePath),"utf-8");
    }

    /**
     * @param t 要生成xml的实体类
     * @param file 要生成的文件对象
     * @return 是否成生成对应的xml
     */
    public static <T> boolean createXml(T t,File file){
        return createXml(t,file,"utf-8");
    }

    /**
     * @param file 读取的文件对象
     * @param type 要映射生成的Java对象实体
     * @param encoding 编码格式
     */
    public static <T> T readXml(File file,Class<T> type,String encoding) {
        if(file==null||!file.isFile()||type==null) return null;
        T value = null;
        InputStream inStream = null;
        InputStreamReader reader = null;
        try{
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            inStream = new FileInputStream(file);
            reader = new InputStreamReader(inStream,encoding);
            value = (T) unmarshaller.unmarshal(reader);
        }catch (Exception e){
            logger.error(getException(e));
        }finally {
            try{
                if(inStream!=null) inStream.close();
                if(reader!=null) reader.close();
            }catch (Exception e){
                logger.error(getException(e));
            }
        }
        return value;
    }

    /**
     * @param filePath 读取的文件路径
     * @param type 要映射生成的Java对象实体
     * @param encoding 编码格式
     */
    public static <T> T readXml(String filePath,Class<T> type,String encoding) {
        return readXml(new File(filePath),type,encoding);
    }

    /**
     * @param filePath 读取的文件路径
     * @param type 要映射生成的Java对象实体
     */
    public static <T> T readXml(String filePath,Class<T> type) {
        return readXml(new File(filePath),type,"utf-8");
    }


    /**
     * @param file 读取的文件对象
     * @param type 要映射生成的Java对象实体
     */
    public static <T> T readXml(File file,Class<T> type) {
        return readXml(file,type,"utf-8");
    }

    /**
     * 获取异常信息的日志描述
     */
    private static String getException(Throwable throwable) {
        if (throwable == null) return "";

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException ex) {
            sb.append(ex.toString());
        }
        return sb.toString();
    }

}