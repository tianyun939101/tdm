package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集规则日志Entity
 * @author wbq
 * @version 2017-08-11
 */
public class Guizelog extends DataEntity<Guizelog> {
	
	private static final long serialVersionUID = 1L;
	private String ciguizeid;		// 规则ID
	private String loglx;		// 日志类型
	private String logcontent;		// 日志内容
	private String filename;		// 文件名称
	
	public Guizelog() {
		super();
	}

	public Guizelog(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规则ID长度必须介于 1 和 64 之间")
	public String getCiguizeid() {
		return ciguizeid;
	}

	public void setCiguizeid(String ciguizeid) {
		this.ciguizeid = ciguizeid;
	}
	
	@Length(min=1, max=64, message="日志类型长度必须介于 1 和 64 之间")
	public String getLoglx() {
		return loglx;
	}

	public void setLoglx(String loglx) {
		this.loglx = loglx;
	}
	
	@Length(min=0, max=255, message="日志内容长度必须介于 0 和 255 之间")
	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}
	
	@Length(min=0, max=100, message="文件名称长度必须介于 0 和 100 之间")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}