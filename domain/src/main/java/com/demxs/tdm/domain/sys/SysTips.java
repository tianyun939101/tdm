package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 系统提示信息配置Entity
 * @author 谭冬梅
 * @version 2017-09-06
 */
public class SysTips extends DataEntity<SysTips> {
	
	private static final long serialVersionUID = 1L;
	private String tipContent;		// 提示内容
	private String tipSql;		// 需要执行的sql
	private String url;		// 处理的url地址
	private String type;		// 类型
	
	public SysTips() {
		super();
	}

	public SysTips(String id){
		super(id);
	}

	@Length(min=0, max=200, message="提示内容长度必须介于 0 和 200 之间")
	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}
	
	@Length(min=0, max=4000, message="需要执行的sql长度必须介于 0 和 4000 之间")
	public String getTipSql() {
		return tipSql;
	}

	public void setTipSql(String tipSql) {
		this.tipSql = tipSql;
	}
	
	@Length(min=0, max=2000, message="处理的url地址长度必须介于 0 和 2000 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=5, message="类型长度必须介于 0 和 5 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}