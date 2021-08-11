package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 知识可见范围-人员Entity
 * @author 詹小梅
 * @version 2017-07-12
 */
public class ZhishiKjffRy extends DataEntity<ZhishiKjffRy> {
	
	private static final long serialVersionUID = 1L;
	private String zhishiid;		// 知识ID
	private String kejianryid;		// 人员ID
	private String kejianrymc;
	private int  status ; //	状态（删除1 或 新增2）

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ZhishiKjffRy() {
		super();
	}

	public ZhishiKjffRy(String id){
		super(id);
	}

	public ZhishiKjffRy(String id,String zhishiid){
		super(id);
		this.zhishiid = zhishiid;
	}
	public ZhishiKjffRy(String id,String kejianryid,String zhishiid){
		super(id);
		this.kejianryid = kejianryid;
		this.zhishiid = zhishiid;
	}

	@Length(min=0, max=200, message="知识ID长度必须介于 0 和 200 之间")
	public String getZhishiid() {
		return zhishiid;
	}

	public void setZhishiid(String zhishiid) {
		this.zhishiid = zhishiid;
	}

	public String getKejianryid() {
		return kejianryid;
	}

	public void setKejianryid(String kejianryid) {
		this.kejianryid = kejianryid;
	}

	public String getKejianrymc() {
		return kejianrymc;
	}

	public void setKejianrymc(String kejianrymc) {
		this.kejianrymc = kejianrymc;
	}
}