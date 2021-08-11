package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.cj.Guize;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import org.hibernate.validator.constraints.Length;


/**
 * 设备采集配置Entity
 * @author sunjunhui
 * @version 2018-01-08
 */
public class ShebeiRule extends DataEntity<ShebeiRule> {

	private static final long serialVersionUID = 1L;
	private String dataUrl;		// data_url
	private String dataUsername;		// data_username
	private String dataPassword;		// data_password
	private Shebei shebei;		// shebei_id
	private Guize guize;		// caiji_id
	
	public ShebeiRule() {
		super();
	}

	public ShebeiRule(String id){
		super(id);
	}

	public ShebeiRule(Shebei shebei){
		this.shebei = shebei;
	}

	@Length(min=0, max=200, message="data_url长度必须介于 0 和 200 之间")
	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	@Length(min=0, max=100, message="data_username长度必须介于 0 和 100 之间")
	public String getDataUsername() {
		return dataUsername;
	}

	public void setDataUsername(String dataUsername) {
		this.dataUsername = dataUsername;
	}
	
	@Length(min=0, max=100, message="data_password长度必须介于 0 和 100 之间")
	public String getDataPassword() {
		return dataPassword;
	}

	public void setDataPassword(String dataPassword) {
		this.dataPassword = dataPassword;
	}

	public Shebei getShebei() {
		return shebei;
	}

	public void setShebei(Shebei shebei) {
		this.shebei = shebei;
	}

	public Guize getGuize() {
		return guize;
	}

	public void setGuize(Guize guize) {
		this.guize = guize;
	}
}