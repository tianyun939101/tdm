package com.demxs.tdm.service.dataCenter.parse.rule.constant;

import com.demxs.tdm.comac.common.constant.attach.DocType;
import com.demxs.tdm.comac.common.constant.attach.TextDocType;
import com.demxs.tdm.service.dataCenter.parse.rule.DataFileResolver;
import com.demxs.tdm.service.dataCenter.parse.rule.docType.CsvDocType;
import com.demxs.tdm.service.dataCenter.parse.rule.docType.ExcelDocType;
import com.demxs.tdm.service.dataCenter.parse.rule.impl.CsvDataFileResolver;
import com.demxs.tdm.service.dataCenter.parse.rule.impl.ExcelDataFileResolver;
import com.demxs.tdm.service.dataCenter.parse.rule.impl.TxtDataFileResolver;

public enum  FileResolverType {
    TXT(1,"文本", new TextDocType(),new TxtDataFileResolver()),
    CSV(2,"CSV", new CsvDocType(),new CsvDataFileResolver()),
    EXCEL(3,"EXCEL", new ExcelDocType(),new ExcelDataFileResolver());

    private Integer value;
    private String text;
    private DocType docType;
    private DataFileResolver fileResolver;

    FileResolverType(Integer value, String text, DocType docType, DataFileResolver fileResolver) {
        this.value = value;
        this.text = text;
        this.docType = docType;
        this.fileResolver = fileResolver;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public DocType getDocType() {
        return docType;
    }

    public DataFileResolver getFileResolver() {
        return fileResolver;
    }
}
