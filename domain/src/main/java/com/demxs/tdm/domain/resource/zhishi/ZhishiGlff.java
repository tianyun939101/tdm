package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 知识关联方法Entity
 * @author 詹小梅
 * @version 2017-06-17
 */
public class ZhishiGlff extends DataEntity<ZhishiGlff> {
	
	private static final long serialVersionUID = 1L;
	private String zhishiid;		// 知识ID
	private String fangfaid;		// 方法ID
	private int  status ; //	状态（删除1 或 新增2）
	private String  fangfamc; // 方法名称
	
	public ZhishiGlff() {
		super();
	}

	public ZhishiGlff(String id){
		super(id);
	}

	@Length(min=0, max=200, message="知识ID长度必须介于 0 和 200 之间")
	public String getZhishiid() {
		return zhishiid;
	}

	public void setZhishiid(String zhishiid) {
		this.zhishiid = zhishiid;
	}
	
	@Length(min=0, max=200, message="方法ID长度必须介于 0 和 200 之间")
	public String getFangfaid() {
		return fangfaid;
	}

	public void setFangfaid(String fangfaid) {
		this.fangfaid = fangfaid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFangfamc() {
		return fangfamc;
	}

	public void setFangfamc(String fangfamc) {
		this.fangfamc = fangfamc;
	}
}