package com.demxs.tdm.domain.resource.attach;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 附件管理Entity
 * @author wuliepeng
 * @version 2017-07-19
 */
public class AttachmentInfo extends DataEntity<AttachmentInfo> {

	private static final long serialVersionUID = 1L;
	private Integer type;		// 附件类型 1:普通附件,2:知识附件
	private String name;		// 附件名称
	private String path;		// 附件存储路径
	private String extName;		//扩展名称
	private String businessId;		// 业务id
	private Long length;		// 大小
	private String convertPath;			//转换后的附件存储路径
	private Integer docType;	//文档类型	//1:pdf,2:txt,3:vedio,4:img,5:office,6:other
	private Integer status;		//文档状态	//1:转换中,2:索引中,3:完成

	public AttachmentInfo() {
		super();
	}

	public AttachmentInfo(String id){
		super(id);
	}

	public AttachmentInfo(String id,String businessId){
		super(id);
		this.businessId = businessId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=50, message="附件名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="附件路径长度必须介于 0 和 100 之间")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

	
	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getConvertPath() {
		return convertPath;
	}

	public void setConvertPath(String convertPath) {
		this.convertPath = convertPath;
	}

	public Integer getDocType() {
		return docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
}