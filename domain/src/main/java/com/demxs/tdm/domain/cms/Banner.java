package com.demxs.tdm.domain.cms;


import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 轮播图视图实体类型
 */
public class Banner extends DataEntity<Banner> {

    private String id;//表单编号
    private String title;//文章标题
    private String name;//栏目名称
    private String module;// 栏目模型（article：文章；picture：图片；download：下载；link：链接；special：专题）
    private String siteId;//站点编号
    private String categoryId;//栏目编号
    private String description;//描述
    private String image;//图片
    private String table;//表单
    private String inBanner;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getInBanner() {
        return inBanner;
    }

    public void setInBanner(String inBanner) {
        this.inBanner = inBanner;
    }
}
