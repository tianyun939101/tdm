package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 附件管理Entity
 * @author wbq
 * @version 2017-07-01
 */
public class SysAttachment extends DataEntity<SysAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 原文件名
	private String fileType;		// 文件类型
	private String fileVersion;		// 文件版本
	private String filePath;		// 文件路径
	private String fileDesc;		// 文件描述
	private Date createTime;		// 创建时间
	private Long createUserid;		// 创建人ID
	private String createUsername;		// 创建人
	private String codeId;		// 上传模块的主键ID
	private String filenameindisk;
	public String getFilenameindisk() {
		return filenameindisk;
	}

	public void setFilenameindisk(String filenameindisk) {
		this.filenameindisk = filenameindisk;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	private String fileLength;		// 文件大小
	private Long state;		// 状态
	private String columnName;		// 对应字段名称
	private String saveDataId;
	
	public String getSaveDataId() {
		return saveDataId;
	}

	public void setSaveDataId(String saveDataId) {
		this.saveDataId = saveDataId;
	}

	public SysAttachment() {
		super();
	}

	public SysAttachment(String id){
		super(id);
	}

	@Length(min=0, max=4000, message="原文件名长度必须介于 0 和 4000 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=100, message="文件类型长度必须介于 0 和 100 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=20, message="文件版本长度必须介于 0 和 20 之间")
	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}
	
	@Length(min=0, max=4000, message="文件路径长度必须介于 0 和 4000 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=2000, message="文件描述长度必须介于 0 和 2000 之间")
	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Long getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(Long createUserid) {
		this.createUserid = createUserid;
	}
	
	@Length(min=0, max=100, message="创建人长度必须介于 0 和 100 之间")
	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	@Length(min=0, max=20, message="文件大小长度必须介于 0 和 20 之间")
	public String getFileLength() {
		return fileLength;
	}

	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}
	
	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}
	
	@Length(min=0, max=50, message="对应字段名称长度必须介于 0 和 50 之间")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
}