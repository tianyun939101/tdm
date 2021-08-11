package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.sample.SampleLocation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 申请试验组中样品组的样品信息Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustSampleGroupItem extends DataEntity<EntrustSampleGroupItem> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String entrustCode;		// 申请单号
	private String tGroupId;		// 试验组ID
	private String sGroupId;		// 样品组ID
	private String name;		// 样品名称
	private Integer type;		// 样品类型
	private String sn;		// 样品编号
	private Date factureDate;	//生产日期
	private Integer amount;		// 样品数量
	private Integer status;		// 样品状态
	private String voltage;		// 系统电压
	private String size;		// 组件尺寸
	private String desc;		// 缺陷描述
	private String model;		// 样品型号
	private String token;		// 工作令号

	private String no;//样品流水号

	private EntrustInfo entrustInfo;//申请信息
	private Integer sampleStatus;		// 样品状态 0待入库 1待检 2在检 3已检 4已归还 5已报废  数据字典
	private SampleLocation sampleLocation;//存放位置
	private User operater;//送样人
	private Date sampleInDate;//入库时间


	private String taskCode;//任务编号
	private String sampleLocationIds;//用于页面后台传值 样品存放位置ids集合
	private String sampleIds;//用于页面后台传值 样品ids集合

	private String dateRange;//入库时间范围 页面传值
	private Date startDate;//入库开始时间
	private Date endDate;//入库结束时间
	private String codes;//用于页面后台传值 样品codes集合
	private String ids;//用于页面后台传值 样品codes集合
	private String sampleTaskId;//任务样品Id 用于页面传值
    private String tuHao;//样品图号
    private String miaoShu;//样品描述
    private String isApproval;//有无批准放行证书/适航批准
    private String approvalContent;//批准放行证书/适航批准内容
    private String fileId;//文件id
    private String category;//样品种类

	private String subCenter;


	public String getSubCenter() {
		return subCenter;
	}

	public void setSubCenter(String subCenter) {
		this.subCenter = subCenter;
	}

	public EntrustSampleGroupItem() {
		super();
	}

	public EntrustSampleGroupItem(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=100, message="申请单号长度必须介于 0 和 100 之间")
	public String getEntrustCode() {
		return entrustCode;
	}

	public void setEntrustCode(String entrustCode) {
		this.entrustCode = entrustCode;
	}
	
	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}
	
	@Length(min=0, max=64, message="样品类型长度必须介于 0 和 64 之间")
	public String getSGroupId() {
		return sGroupId;
	}

	public void setSGroupId(String sGroupId) {
		this.sGroupId = sGroupId;
	}
	
	@Length(min=0, max=100, message="样品名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="系统电压长度必须介于 0 和 100 之间")
	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	
	@Length(min=0, max=100, message="组件尺寸长度必须介于 0 和 100 之间")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	@Length(min=0, max=100, message="缺陷描述长度必须介于 0 和 100 之间")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Length(min=0, max=100, message="样品型号长度必须介于 0 和 100 之间")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss")
	public Date getFactureDate() {
		return factureDate;
	}

	public void setFactureDate(Date factureDate) {
		this.factureDate = factureDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getSampleStatus() {
		return sampleStatus=sampleStatus;
	}

	public void setSampleStatus(Integer sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public SampleLocation getSampleLocation() {
		return sampleLocation;
	}

	public void setSampleLocation(SampleLocation sampleLocation) {
		this.sampleLocation = sampleLocation;
	}

	public User getOperater() {
		return operater;
	}

	public void setOperater(User operater) {
		this.operater = operater;
	}

	@JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss")
	public Date getSampleInDate() {
		return sampleInDate;
	}

	public void setSampleInDate(Date sampleInDate) {
		this.sampleInDate = sampleInDate;
	}

	public String getSampleLocationIds() {
		return sampleLocationIds;
	}

	public void setSampleLocationIds(String sampleLocationIds) {
		this.sampleLocationIds = sampleLocationIds;
	}

	public String getSampleIds() {
		return sampleIds;
	}

	public void setSampleIds(String sampleIds) {
		this.sampleIds = sampleIds;
	}

	public EntrustInfo getEntrustInfo() {
		return entrustInfo;
	}

	public void setEntrustInfo(EntrustInfo entrustInfo) {
		this.entrustInfo = entrustInfo;
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

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getSampleTaskId() {
		return sampleTaskId;
	}

	public void setSampleTaskId(String sampleTaskId) {
		this.sampleTaskId = sampleTaskId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

    public String getTuHao() {
        return tuHao;
    }

    public void setTuHao(String tuHao) {
        this.tuHao = tuHao;
    }

    public String getMiaoShu() {
        return miaoShu;
    }

    public void setMiaoShu(String miaoShu) {
        this.miaoShu = miaoShu;
    }

    public String getIsApproval() {
        return isApproval;
    }

    public EntrustSampleGroupItem setIsApproval(String isApproval) {
        this.isApproval = isApproval;
        return this;
    }

    public String getApprovalContent() {
        return approvalContent;
    }

    public EntrustSampleGroupItem setApprovalContent(String approvalContent) {
        this.approvalContent = approvalContent;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public EntrustSampleGroupItem setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public EntrustSampleGroupItem setCategory(String category) {
        this.category = category;
        return this;
    }
}