package com.demxs.tdm.domain.resource.huanjing;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 环境设备维护Entity
 * @author zhangdengcai
 * @version 2017-07-16
 */
public class HuanjingSbwh extends DataEntity<HuanjingSbwh> {
	
	private static final long serialVersionUID = 1L;
	private String shebeibh;		// 设备编号
	private String shebeimc;		// 设备名称
	private String shebeifj;		// 设备房间
	private String changshang;		// 厂商
	private String shebeizt;		// 设备状态
	private String ipAddress;		//设备连接的ip地址
	private String port;		//设备对应的端口。如温湿度仪：502
	private String shebeifjmc;		//设备房间名称

	public HuanjingSbwh() {
		super();
	}

	public HuanjingSbwh(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备编号长度必须介于 0 和 200 之间")
	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
	}
	
	@Length(min=0, max=200, message="设备名称长度必须介于 0 和 200 之间")
	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}
	
	@Length(min=0, max=200, message="设备房间长度必须介于 0 和 200 之间")
	public String getShebeifj() {
		return shebeifj;
	}

	public void setShebeifj(String shebeifj) {
		this.shebeifj = shebeifj;
	}
	
	@Length(min=0, max=200, message="厂商长度必须介于 0 和 200 之间")
	public String getChangshang() {
		return changshang;
	}

	public void setChangshang(String changshang) {
		this.changshang = changshang;
	}
	
	@Length(min=0, max=10, message="设备状态长度必须介于 0 和 10 之间")
	public String getShebeizt() {
		return shebeizt;
	}

	public void setShebeizt(String shebeizt) {
		this.shebeizt = shebeizt;
	}

	public String getShebeifjmc() {
		return shebeifjmc;
	}

	public void setShebeifjmc(String shebeifjmc) {
		this.shebeifjmc = shebeifjmc;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}