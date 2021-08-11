package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.sample.SampleLocation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 申请试验组中的样品组信息Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustSampleGroup extends DataEntity<EntrustSampleGroup> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String tGroupId;		// 试验组ID
	private String no;		//样品组号
	private String sn;		// 批次号/SN码
	private Integer type;		// 样品类型(1:成品,2:半成品,3:材料)
	private String name;		// 样品名称
	private String model;		// 样品型号
	private Date factureDate;	//生产日期
	private Integer amount;		// 样品数量
	private Integer status;		// 样品状态(0:缺陷,1:完好)
	private String voltage;		// 系统电压
	private String size;		// 组件尺寸
	private String desc;		// 缺陷描述
	private String token;		// 工作令号
	private List<EntrustSampleBom> bom; //样品bom信息

	private List<EntrustSampleGroupItem> sampleList = Lists.newArrayList();//样品信息
	private Integer sampleStatus;		// 样品组状态 0没有入库 1入库


	/*********页面传值**************/
	private SampleLocation sampleLocation;//存放位置
	private User operater;//送样人
	private Date sampleInDate;//入库时间
	private String sampleLocationIds;//用于页面后台传值 样品存放位置ids集合
	private String sampleIds;//用于页面后台传值 样品ids集合

    private String tuHao;//图号
	private String isApproval;//有无批准放行证书/适航批准
	private String approvalContent;//批准放行证书/适航批准内容
	private String fileId;//文件id
    private String category;//样品种类

	public String getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(String isApproval) {
		this.isApproval = isApproval;
	}

	public String getApprovalContent() {
		return approvalContent;
	}

	public void setApprovalContent(String approvalContent) {
		this.approvalContent = approvalContent;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public EntrustSampleGroup() {
		super();
	}

	public EntrustSampleGroup(String id){
		super(id);
	}

	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Length(min=0, max=128, message="批次号/SN码长度必须介于 0 和 128 之间")
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="样品名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="样品型号长度必须介于 0 和 100 之间")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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
	
	@Length(min=0, max=200, message="缺陷描述长度必须介于 0 和 200 之间")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Length(min=0, max=100, message="工作令号长度必须介于 0 和 100 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<EntrustSampleBom> getBom() {
		return bom;
	}

	public void setBom(List<EntrustSampleBom> bom) {
		this.bom = bom;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getFactureDate() {
		return factureDate;
	}

	public void setFactureDate(Date factureDate) {
		this.factureDate = factureDate;
	}

	public Integer getSampleStatus() {
		return sampleStatus=sampleStatus;
	}

	public void setSampleStatus(Integer sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	public List<EntrustSampleGroupItem> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<EntrustSampleGroupItem> sampleList) {
		this.sampleList = sampleList;
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

	public Date getSampleInDate() {
		return sampleInDate;
	}

	public void setSampleInDate(Date sampleInDate) {
		this.sampleInDate = sampleInDate;
	}

    public String getTuHao() {
        return tuHao;
    }

    public void setTuHao(String tuHao) {
        this.tuHao = tuHao;
    }

    public String getCategory() {
        return category;
    }

    public EntrustSampleGroup setCategory(String category) {
        this.category = category;
        return this;
    }
}