package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 标准物质存放位置Entity
 * @author zhangdengcai
 * @version 2017-06-17
 */
public class BiaozhunwzCfwz extends DataEntity<BiaozhunwzCfwz> {
	
	private static final long serialVersionUID = 1L;
	private String weizhimc;		// 位置名称
	private String youxiaox;		// 有效性
	private String jibieh;		// 级别号
	private String fuzhuj;		// 父主键
	private String subcount;	//子节点数量

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

	public BiaozhunwzCfwz() {
		super();
	}

	public BiaozhunwzCfwz(String id){
		super(id);
	}

	@Length(min=0, max=200, message="位置名称长度必须介于 0 和 200 之间")
	public String getWeizhimc() {
		return weizhimc;
	}

	public void setWeizhimc(String weizhimc) {
		this.weizhimc = weizhimc;
	}
	
	@Length(min=0, max=200, message="有效性长度必须介于 0 和 200 之间")
	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}
	
	@Length(min=0, max=200, message="级别号长度必须介于 0 和 200 之间")
	public String getJibieh() {
		return jibieh;
	}

	public void setJibieh(String jibieh) {
		this.jibieh = jibieh;
	}
	
	@Length(min=0, max=200, message="父主键长度必须介于 0 和 200 之间")
	public String getFuzhuj() {
		return fuzhuj;
	}

	public void setFuzhuj(String fuzhuj) {
		this.fuzhuj = fuzhuj;
	}

	public String getSubcount() {
		return subcount;
	}

	public void setSubcount(String subcount) {
		this.subcount = subcount;
	}
	public User getDangqianR() {
		return dangqianR;
	}

	public void setDangqianR(User dangqianR) {
		this.dangqianR = dangqianR;
	}

	public String getCaozuoqx() {
		return caozuoqx;
	}

	public void setCaozuoqx(String caozuoqx) {
		this.caozuoqx = caozuoqx;
	}
}