package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;


/**
 * 版本号Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class Version extends DataEntity<Version> {
	
	private static final long serialVersionUID = 1L;
	private String version;		// 版本号
	
	public Version() {
		super();
	}

	public Version(String id){
		super(id);
	}

	@Length(min=0, max=30, message="版本号长度必须介于 0 和 30 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}