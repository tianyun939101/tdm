package com.demxs.tdm.comac.common.constant.attach;


import com.demxs.tdm.common.file.convertor.DocConvertor;
import com.demxs.tdm.common.file.convertor.impl.FfmpegConvertor;
import com.demxs.tdm.common.file.convertor.impl.FileCopyConvertor;
import com.demxs.tdm.common.file.convertor.impl.OpenOfficeConvertor;

/**
 * 知识类型.
 * User: wuliepeng
 * Date: 2017-07-20
 * Time: 下午3:32
 */
public enum KnowledgeType {
    //1:pdf,2:text,3:vedio,4:img,5:office,6:other
    Pdf(1,"PDF",new PdfDocType(),new FileCopyConvertor()), //pdf文档类型
    Text(2,"文本",new TextDocType(),new FileCopyConvertor()), //文本类型
    Video(3,"视频",new VideoDocType(),new FfmpegConvertor()), //多媒体文档类型
    Image(4,"图片",new ImgDocType(),new FileCopyConvertor()), //图像类型
    Document(5,"OFFICE",new OfficeDocType(),new OpenOfficeConvertor()), //offic文档类型
    Other(6,"其它",new OtherDocType(),new FileCopyConvertor());    //其它类型


    private Integer value;
    private String text;
    private DocType docType;
    private DocConvertor convertor;

    KnowledgeType(Integer value, String text, DocType docType, DocConvertor convertor){
        this.value = value;
        this.text = text;
        this.docType = docType;
        this.convertor = convertor;
    }

    public Integer getValue(){
        return value;
    }

    public String getText() {
        return text;
    }

    public DocType getDocType(){
        return docType;
    }

    public DocConvertor getConvertor() {
        return convertor;
    }

    public static KnowledgeType getDocType(Integer docType){
        for (KnowledgeType type : KnowledgeType.values()){
            if(type.getValue().equals(docType)){
                return type;
            }
        }
        return null;
    }
}
