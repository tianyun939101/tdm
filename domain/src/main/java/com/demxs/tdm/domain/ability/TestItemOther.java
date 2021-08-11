package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目其它信息Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestItemOther extends DataEntity<TestItemOther> {
	
	private static final long serialVersionUID = 1L;
	private String sampleRequire;		// 样品要求
	private String images;		// 样例图片
	private String standard;		// 标准
	private String otherFiles;		// 附件
	private String collator;		// 整理人
	private String deplete;		// 是否消耗样品
	
	public TestItemOther() {
		super();
	}

	public TestItemOther(String id){
		super(id);
	}

	@Length(min=0, max=500, message="样品要求长度必须介于 0 和 500 之间")
	public String getSampleRequire() {
		return sampleRequire;
	}

	public void setSampleRequire(String sampleRequire) {
		this.sampleRequire = sampleRequire;
	}
	
	@Length(min=0, max=2000, message="样例图片长度必须介于 0 和 2000 之间")
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	@Length(min=0, max=64, message="标准长度必须介于 0 和 64 之间")
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getOtherFiles() {
		return otherFiles;
	}

	public void setOtherFiles(String otherFiles) {
		this.otherFiles = otherFiles;
	}
	
	@Length(min=0, max=64, message="整理人长度必须介于 0 和 64 之间")
	public String getCollator() {
		return collator;
	}

	public void setCollator(String collator) {
		this.collator = collator;
	}
	
	@Length(min=0, max=64, message="是否消耗样品长度必须介于 0 和 64 之间")
	public String getDeplete() {
		return deplete;
	}

	public void setDeplete(String deplete) {
		this.deplete = deplete;
	}
	
}