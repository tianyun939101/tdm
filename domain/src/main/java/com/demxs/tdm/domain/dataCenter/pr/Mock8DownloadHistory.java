package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/*
 * @Describe:mock8数据下载履历
 * @Author:WuHui
 * @Date:11:01 2020/12/7
*/
public class Mock8DownloadHistory extends DataEntity<Mock8DownloadHistory> {

    private static final long serialVersionUID = 1L;

    private String filePath;//文件路径
    private String fileName;//文件名称
    private Long fileSize;//文件大小
    private String ip;//客户端IP

    //下载数量
    private String planGross;

    private String dateRange;//时间范围 页面传值
    private Date startDate;//开始时间
    private Date endDate;//结束时间

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPlanGross() {
        return planGross;
    }

    public void setPlanGross(String planGross) {
        this.planGross = planGross;
    }

    @JsonIgnore
    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    @JsonIgnore
    public Date getStartDate() {
        if(StringUtils.isNotBlank(dateRange)){
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[0]);
        }else{
            return null;
        }

    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonIgnore
    public Date getEndDate() {
        if(StringUtils.isNotBlank(dateRange)){
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[1]);
        }else{
            return null;
        }
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
