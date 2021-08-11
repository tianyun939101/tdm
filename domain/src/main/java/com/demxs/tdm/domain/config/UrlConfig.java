package com.demxs.tdm.domain.config;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;


/**
 * url配置Entity
 * @author sunjunhui
 * @version 2017-11-30
 */
public class UrlConfig extends DataEntity<UrlConfig> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 生产基地名称
	private String url;		// 配置url
	
	public UrlConfig() {
		super();
	}

	public UrlConfig(String id){
		super(id);
	}

	@Length(min=0, max=100, message="生产基地名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="配置url长度必须介于 0 和 100 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}