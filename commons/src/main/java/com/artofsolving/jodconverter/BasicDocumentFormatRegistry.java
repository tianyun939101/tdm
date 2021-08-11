package com.artofsolving.jodconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasicDocumentFormatRegistry implements DocumentFormatRegistry {
    private static final Logger log = LoggerFactory.getLogger(BasicDocumentFormatRegistry.class);


    private List/* <DocumentFormat> */ documentFormats = new ArrayList();

    public void addDocumentFormat(DocumentFormat documentFormat) {
        documentFormats.add(documentFormat);
    }

    protected List/* <DocumentFormat> */ getDocumentFormats() {
        return documentFormats;
    }

    /**
     * @param extension
     *            the file extension
     * @return the DocumentFormat for this extension, or null if the extension
     *         is not mapped
     */
    @Override
    public DocumentFormat getFormatByFileExtension(String extension) {
        log.error("获取文件扩展名:"+extension);
        if (extension == null) {
            return null;
        }
        if (extension.indexOf("doc") >= 0) {
            extension = "doc";
        }
        if (extension.indexOf("ppt") >= 0) {
            extension = "ppt";
        }
        if (extension.indexOf("xls") >= 0) {
            extension = "xls";
        }
        String lowerExtension = extension.toLowerCase();
        for (Iterator it = documentFormats.iterator(); it.hasNext();) {
            DocumentFormat format = (DocumentFormat) it.next();
            if (format.getFileExtension().equals(lowerExtension)) {
                return format;
            }
        }
        return null;
    }

    @Override
    public DocumentFormat getFormatByMimeType(String mimeType) {
        for (Iterator it = documentFormats.iterator(); it.hasNext();) {
            DocumentFormat format = (DocumentFormat) it.next();
            if (format.getMimeType().equals(mimeType)) {
                return format;
            }
        }
        return null;
    }
}