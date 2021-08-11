package com.demxs.tdm.domain.dataCenter;

public class OaEntity {
    private static final long serialVersionUID = 1L;


    private String url;

    private String publishOrg;

    private String link;

    private String color;

    private String publishDate;

    private String title;

    private String siteAreaName;

    private String imgUrl;

    private String systemName;

    private String summary;

    private String local;

    private String dealUrl;

    private String taskId;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishOrg() {
        return publishOrg;
    }

    public void setPublishOrg(String publishOrg) {
        this.publishOrg = publishOrg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSiteAreaName() {
        return siteAreaName;
    }

    public void setSiteAreaName(String siteAreaName) {
        this.siteAreaName = siteAreaName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDealUrl() {
        return dealUrl;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
