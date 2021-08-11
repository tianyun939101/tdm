package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/7/15 11:37
 * @Description: 数据提报详情页下载历史记录
 */
public class DataReportDownloadHistory extends DataEntity<DataReportDownloadHistory> {

    private static final long serialVersionUID = 1L;
    /**
     * 数据提报对象id
     */
    private String testId;
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 文件名称
     */
    private String fileName;

    public DataReportDownloadHistory() {
    }

    public DataReportDownloadHistory(String id) {
        super(id);
    }

    public String getTestId() {
        return testId;
    }

    public DataReportDownloadHistory setTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public DataReportDownloadHistory setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DataReportDownloadHistory setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
