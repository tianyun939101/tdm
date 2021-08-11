package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.dataCenter.ZyTestMethod;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 试验组试验能力Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustTestGroupAbility extends DataEntity<EntrustTestGroupAbility> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String tGroupId;		// 试验组ID
	private String seqId;		// 试验序列ID
	private String seqName;		// 试验序列名称
	private String itemId;		// 试验项目ID
	private TestItem item;
	private Integer sort;		// 序号
	private String itemName;		// 试验项目名称
	private String unitId;		// 试验项ID
	private String unitName;		// 试验项名称
	private String version;		//版本号
	private List<EntrustTestItemCodition> conditions;	//试验条件

	private List<ZyTestMethod> zyTestMethodList;

	private ZyTestMethod  zyTestMethod;

	private  String method;

	private  String  methodName;


	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ZyTestMethod getZyTestMethod() {
		return zyTestMethod;
	}

	public void setZyTestMethod(ZyTestMethod zyTestMethod) {
		this.zyTestMethod = zyTestMethod;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<ZyTestMethod> getZyTestMethodList() {
		return zyTestMethodList;
	}

	public void setZyTestMethodList(List<ZyTestMethod> zyTestMethodList) {
		this.zyTestMethodList = zyTestMethodList;
	}
	
	public EntrustTestGroupAbility() {
		super();
	}

	public EntrustTestGroupAbility(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=64, message="试验序列ID长度必须介于 0 和 64 之间")
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	@Length(min=0, max=100, message="试验序列名称长度必须介于 0 和 100 之间")
	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	
	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		if (getItem() != null) {
			return getItem().getId();
		}
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public TestItem getItem() {
		return item;
	}

	public void setItem(TestItem item) {
		this.item = item;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=100, message="试验项目名称长度必须介于 0 和 100 之间")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Length(min=0, max=64, message="试验项ID长度必须介于 0 和 64 之间")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@Length(min=0, max=100, message="试验项名称长度必须介于 0 和 100 之间")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public List<EntrustTestItemCodition> getConditions() {
		return conditions;
	}

	public void setConditions(List<EntrustTestItemCodition> conditions) {
		this.conditions = conditions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}