package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;


/**
 * 申请单样品BOMEntity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustSampleBom extends DataEntity<EntrustSampleBom> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;	//申请单ID
	private String tGroupId;	//试验组ID
	private String sGroupId;		// 样品组ID
	private String name;		// 材料名称
	private String code;	//物料编码
	private String manufactor;		// 厂家
	private String desc;		// 产品描述
	private String sn;	//批次
	private Integer sort;	//序号
	
	public EntrustSampleBom() {
		super();
	}

	public EntrustSampleBom(String id){
		super(id);
	}

	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}

	@Length(min=0, max=64, message="样品组ID长度必须介于 0 和 64 之间")
	public String getSGroupId() {
		return sGroupId;
	}

	public void setSGroupId(String sGroupId) {
		this.sGroupId = sGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufactor() {
		return manufactor;
	}

	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o){
			return true;
		}
		if (o == null || getClass() != o.getClass()){
			return false;
		}

		EntrustSampleBom sampleBom = (EntrustSampleBom) o;

		/*if (!entrustId.equals(sampleBom.entrustId)){
			return false;
		}
		if (!tGroupId.equals(sampleBom.tGroupId)){
			return false;
		}
		if (!sGroupId.equals(sampleBom.sGroupId)){
			return false;
		}*/
		if (name != null && !name.equals(sampleBom.getName())){
			return false;
		}
		if (code != null && !code.equals(sampleBom.getCode())){
			return false;
		}
		if (manufactor !=null && !manufactor.equals(sampleBom.getManufactor())){
			return false;
		}
		if (desc!=null && !desc.equals(sampleBom.getDesc())){
			return false;
		}
		if (sn!=null && !sn.equals(sampleBom.getSn())){
			return false;
		}

		return true;

	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public int hashCode() {
		int result = entrustId.hashCode();
		result = 31 * result + tGroupId.hashCode();
		result = 31 * result + sGroupId.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}
}