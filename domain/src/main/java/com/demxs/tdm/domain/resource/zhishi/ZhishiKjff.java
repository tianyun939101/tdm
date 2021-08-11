package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 知识可见范围Entity
 * @author 詹小梅
 * @version 2017-06-17
 */
public class ZhishiKjff extends DataEntity<ZhishiKjff> {
	
	private static final long serialVersionUID = 1L;
	private String zhishiid;		// 知识ID
	private String kejianbmid;		// 可见部门ID
	private String kejianzbmid;		// 可见子部门ID
	private String kejianbmmc;
	private int  status ; //	状态（删除1 或 新增2）
	
	public ZhishiKjff() {
		super();
	}

	public ZhishiKjff(String id){
		super(id);
	}

	public ZhishiKjff(String id,String zhishiid){
		super(id);
		this.zhishiid = zhishiid;
	}

	public ZhishiKjff(String id,String kejianbmid,String zhishiid){
		super(id);
		this.zhishiid = zhishiid;
		this.kejianbmid = kejianbmid;
	}



	@Length(min=0, max=200, message="知识ID长度必须介于 0 和 200 之间")
	public String getZhishiid() {
		return zhishiid;
	}

	public void setZhishiid(String zhishiid) {
		this.zhishiid = zhishiid;
	}
	
	@Length(min=0, max=200, message="可见部门ID长度必须介于 0 和 200 之间")
	public String getKejianbmid() {
		return kejianbmid;
	}

	public void setKejianbmid(String kejianbmid) {
		this.kejianbmid = kejianbmid;
	}
	
	@Length(min=0, max=200, message="可见子部门ID长度必须介于 0 和 200 之间")
	public String getKejianzbmid() {
		return kejianzbmid;
	}

	public void setKejianzbmid(String kejianzbmid) {
		this.kejianzbmid = kejianzbmid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKejianbmmc() {
		return kejianbmmc;
	}

	public void setKejianbmmc(String kejianbmmc) {
		this.kejianbmmc = kejianbmmc;
	}
}