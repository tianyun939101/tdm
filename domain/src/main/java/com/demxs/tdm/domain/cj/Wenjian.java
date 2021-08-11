package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集文件表Entity
 * @author wbq
 * @version 2017-08-21
 */
public class Wenjian extends DataEntity<Wenjian> {

	private static final long serialVersionUID = 1L;
	private String wenjianid;		// 文件ID
	private String isxuyaocj;		// 是否需要采集
	private String iscaiji;		// 是否采集
	private String logid;		// 日志ID
	private String guizeid;		// 规则ID
	private String filename;
	private String guizemc;

	public Wenjian() {
		super();
	}

	public Wenjian(String id){
		super(id);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getGuizemc() {
		return guizemc;
	}

	public void setGuizemc(String guizemc) {
		this.guizemc = guizemc;
	}

	@Length(min=0, max=64, message="文件ID长度必须介于 0 和 64 之间")
	public String getWenjianid() {
		return wenjianid;
	}

	public void setWenjianid(String wenjianid) {
		this.wenjianid = wenjianid;
	}

	@Length(min=0, max=64, message="是否需要采集长度必须介于 0 和 64 之间")
	public String getIsxuyaocj() {
		return isxuyaocj;
	}

	public void setIsxuyaocj(String isxuyaocj) {
		this.isxuyaocj = isxuyaocj;
	}

	@Length(min=0, max=64, message="是否采集长度必须介于 0 和 64 之间")
	public String getIscaiji() {
		return iscaiji;
	}

	public void setIscaiji(String iscaiji) {
		this.iscaiji = iscaiji;
	}

	@Length(min=0, max=64, message="日志ID长度必须介于 0 和 64 之间")
	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	@Length(min=0, max=64, message="规则ID长度必须介于 0 和 64 之间")
	public String getGuizeid() {
		return guizeid;
	}

	public void setGuizeid(String guizeid) {
		this.guizeid = guizeid;
	}

}