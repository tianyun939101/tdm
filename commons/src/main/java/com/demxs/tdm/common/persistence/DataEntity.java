package com.demxs.tdm.common.persistence;

import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 数据Entity类
 * @author ThinkGem
 * @version 2014-05-16
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	protected String remarks;	// 备注
	protected User createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected User updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag = DEL_FLAG_NORMAL; 	// 删除标记（0：正常；1：删除；2：审核）
    protected String createById;//创建者id
    protected String createByName;//创建者名称
	protected String updateById;//操作者id
	protected String updateByName;//操作者名称



	private String labInfoId;//试验室id
	private String labInfoName;//试验室名称


	public String getCreateById() {
		if(createBy!=null)
		{
			return createBy.getId();
		}
		return createById;
	}


	public String getCreateByName() {
		if(createBy!=null)
		{
			this.createByName = createBy.getName();
			return createBy.getName();
		}
		return createByName;
	}




	public String getUpdateById() {
		if(updateBy!=null)
		{
			return updateBy.getId();
		}
		return updateById;
	}



	public String getUpdateByName() {
		if(updateBy!=null)
		{
			return updateBy.getName();
		}
		return updateByName;
	}

	public String[] getArrIDS() {
		if(arrIDS!=null)
		{
			return arrIDS;
		}else{
			return new String[0];
		}

	}

	public void setArrIDS(String[] arrIDS) {
		this.arrIDS = arrIDS;
	}

	protected String[] arrIDS; // 批量删除使用




	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		Date d = new Date();
		this.updateDate = d;
		//this.updateDateForStr=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(d);
		this.createDate = this.updateDate;
		//this.createDateForStr=this.updateDateForStr;
		//设置试验室
		if(StringUtils.isBlank(this.labInfoId )){
			this.labInfoId = user.getLabInfoId();
		}
		if(StringUtils.isBlank(this.labInfoName)){
			this.labInfoName = user.getLabInfoName();
		}
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		Date d = new Date();
		this.updateDate = d;
		if(StringUtils.isBlank(this.labInfoId )){
			this.labInfoId = user.getLabInfoId();
		}
		if(StringUtils.isBlank(this.labInfoName)){
			this.labInfoName = user.getLabInfoName();
		}

		//this.updateDateForStr=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(d);
	}
	
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	//@JsonIgnore
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getLabInfoId() {
		return labInfoId;
	}

	public void setLabInfoId(String labInfoId) {
		this.labInfoId = labInfoId;
	}

	public String getLabInfoName() {
		return labInfoName;
	}

	public void setLabInfoName(String labInfoName) {
		this.labInfoName = labInfoName;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}


}
