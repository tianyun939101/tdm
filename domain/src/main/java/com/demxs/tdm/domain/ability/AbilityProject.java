package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.SubCenter;

/**
 * @author: Jason
 * @Date: 2020/9/29 13:25
 * @Description: 能力建设项目
 */
public class AbilityProject extends DataEntity<AbilityProject> {

    private static final long serialVersionUID = 1774L;
    //项目编码
    private String code;
    //临时编码
    private String tempCode;

    /**
     * 项目名称
     */
    private String name;
    /**
     * 试验任务
     */
    private String testTask;
    /**
     * 建设内容
     */
    private String content;
    /**
     * 适用型号
     */
    private String model;
    /**
     * 经费预估（万元）
     */
    private String funding;
    /**
     * 经费渠道
     */
    private String fundingChannels;
    /**
     * 所属试验室
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 三年建设计划年份
     */
    private String time1;
    private String time2;
    private String time3;
    /**
     * 三年建设计划内容
     */
    private String plan1;
    private String plan2;
    private String plan3;
    /**
     * 实施单位
     */
    private String implCompany;
    private SubCenter subCenter;

    private  String productType;

    private String   version;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestTask() {
        return testTask;
    }

    public void setTestTask(String testTask) {
        this.testTask = testTask;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getFundingChannels() {
        return fundingChannels;
    }

    public void setFundingChannels(String fundingChannels) {
        this.fundingChannels = fundingChannels;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getPlan1() {
        return plan1;
    }

    public void setPlan1(String plan1) {
        this.plan1 = plan1;
    }

    public String getPlan2() {
        return plan2;
    }

    public void setPlan2(String plan2) {
        this.plan2 = plan2;
    }

    public String getPlan3() {
        return plan3;
    }

    public void setPlan3(String plan3) {
        this.plan3 = plan3;
    }

    public String getImplCompany() {
        return implCompany;
    }

    public void setImplCompany(String implCompany) {
        this.implCompany = implCompany;
    }

    public SubCenter getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(SubCenter subCenter) {
        this.subCenter = subCenter;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
