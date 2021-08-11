package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 设备启停记录Entity
 * @author sunjunhui
 * @version 2017-11-03
 */
public class ShebeiQitingjl extends DataEntity<ShebeiQitingjl> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiId;		// 设备id
	private String status;		// 启停状态 1启动 0停止
	private String shebeibm;//设备编码

	private Date qtTime;//启停时间

	private String txJobId;//提醒定时任务id

	private String jobId;//改变状态任务id

	private String valid;//是否生效 就是到了那个时间真正改了那个状态 才生效
	
	public ShebeiQitingjl() {
		super();
	}

	public ShebeiQitingjl(String id){
		super(id);
	}

	public ShebeiQitingjl(String id, String shebeiId, String status, String shebeibm,Date qtTime,String remarks) {
		super(id);
		this.shebeiId = shebeiId;
		this.status = status;
		this.shebeibm = shebeibm;
		this.qtTime = qtTime;
		this.remarks = remarks;
	}

	@Length(min=0, max=64, message="设备id长度必须介于 0 和 64 之间")
	public String getShebeiId() {
		return shebeiId;
	}

	public void setShebeiId(String shebeiId) {
		this.shebeiId = shebeiId;
	}
	
	@Length(min=0, max=10, message="启停状态 0启动 1停止长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShebeibm() {
		return shebeibm;
	}

	public void setShebeibm(String shebeibm) {
		this.shebeibm = shebeibm;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQtTime() {
		return qtTime;
	}

	public void setQtTime(Date qtTime) {
		this.qtTime = qtTime;
	}


	public String getTxJobId() {
		return txJobId;
	}

	public void setTxJobId(String txJobId) {
		this.txJobId = txJobId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
}