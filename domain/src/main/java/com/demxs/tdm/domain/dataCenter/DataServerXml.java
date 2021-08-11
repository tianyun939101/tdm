package com.demxs.tdm.domain.dataCenter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

/**
 * @author wuhui
 * @date 2020/9/18 14:02
 **/
@XmlRootElement(name = "dataInfo")
@XmlType(propOrder = {"testName","taskNum","testReport","outline","config",
        "productModel","testNature","providerId","providerContact","company","lab","ata","testPurpose","task"})
public class DataServerXml {
    //试验名称
    private String testName;
    //任务编号
   // private String taskNo;
    //计划编号
   // private String planNo;
    //试验大纲
    private Data outline;
    //构型文件
    private Data config;
    //试验报告编号
   // private String reportNo;

    //产品型号
    private String productModel;
    //试验性质
    private String testNature;

    private String providerId;

    private String  providerContact;
    //公司
    private String company;
    //所属试验室
    private String lab;
    //ATA章节
    private String ata;
    //试验目的
    private String testPurpose;
    //试验列表
    private List<Task> task;

    //任务书信息
    private Data taskNum;

    //试验报告信息
    private Data testReport;


    public DataServerXml() {
    }

    public DataServerXml(DataBaseInfo dataBaseInfo) {
        this.testName = dataBaseInfo.getTestName();
        this.providerId=dataBaseInfo.getProviderId();
        this.providerContact=dataBaseInfo.getProviderContact();
      //  this.taskNo = dataBaseInfo.getTaskNo();
       // this.taskNo = dataBaseInfo.getTaskNo();
      //  this.planNo = dataBaseInfo.getPlanNo();
      //  this.reportNo = dataBaseInfo.getReportCode();
       // this.productModel = dataBaseInfo.getProductModel();
        this.testNature = dataBaseInfo.getTestNature();
        this.company = dataBaseInfo.getCompany();
        this.lab = dataBaseInfo.getLabInfo() != null ? dataBaseInfo.getLabInfo().getId() : dataBaseInfo.getLabId();
       // this.ata = dataBaseInfo.getAtaChapter();
        this.testPurpose = dataBaseInfo.getTestPurpose();
    }

    //试验任务
    @XmlType(propOrder = {"id","testItem","testItemName","tester","testAddress","timeRange","remarks","data"})
    public static class Task{
        //实验编号
        private String id;
        //试验项目
        private String testItem;

        private String testItemName;
        //试验人员
        private String tester;
        //试验地点
        private String testAddress;
        //试验时间
     //   private Date testDate;
        //试验内容
        private String remarks;
        //试验数据
        private List<Data> data;

        //试验时间范围
        private String  timeRange;

        public Task() {
        }

        public Task(DataTestInfo testInfo, List<Data> data) {
            this.id = testInfo.getId();
            this.testItem = testInfo.getTestItem() == null ? testInfo.getTestItemId() : testInfo.getTestItem().getId();
            this.testItemName=testInfo.getTestItemName();
            this.tester = testInfo.getTester();
            this.testAddress = testInfo.getTestAddress();
           // this.testDate = testInfo.getTestDate();
            this.timeRange = testInfo.getTimeRange();
            this.remarks = testInfo.getRemarks();
            this.data = data;
        }


        public String getTestItemName() {
            return testItemName;
        }

        public void setTestItemName(String testItemName) {
            this.testItemName = testItemName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTestItem() {
            return testItem;
        }

        public void setTestItem(String testItem) {
            this.testItem = testItem;
        }

        public String getTester() {
            return tester;
        }

        public void setTester(String tester) {
            this.tester = tester;
        }

        public String getTestAddress() {
            return testAddress;
        }

        public void setTestAddress(String testAddress) {
            this.testAddress = testAddress;
        }

        public String getTimeRange() {
            return timeRange;
        }

        public void setTimeRange(String timeRange) {
            this.timeRange = timeRange;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }
    //数据项
    @XmlType(propOrder = {"type","path","name"})
    public static class Data{
        //数据类型 原始记录单 : "1" 试验数据:"2" 试验日志:"3" 视频数据:"4" 音频数据: "5" 图片数据: "6"
        private String type;
        //文件路径
        private String path;
        //文件名称
        private String name;

        public Data() {
        }

        public Data(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }


    public Data getOutline() {
        return outline;
    }

    public void setOutline(Data outline) {
        this.outline = outline;
    }

    public Data getConfig() {
        return config;
    }

    public void setConfig(Data config) {
        this.config = config;
    }



    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getTestNature() {
        return testNature;
    }

    public void setTestNature(String testNature) {
        this.testNature = testNature;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getAta() {
        return ata;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }

    public String getTestPurpose() {
        return testPurpose;
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public Data getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Data taskNum) {
        this.taskNum = taskNum;
    }

    public Data getTestReport() {
        return testReport;
    }

    public void setTestReport(Data testReport) {
        this.testReport = testReport;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderContact() {
        return providerContact;
    }

    public void setProviderContact(String providerContact) {
        this.providerContact = providerContact;
    }
}
