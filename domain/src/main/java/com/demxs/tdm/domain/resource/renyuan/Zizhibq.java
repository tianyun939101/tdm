package com.demxs.tdm.domain.resource.renyuan;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 资质标签操作Entity
 * @author 谭冬梅
 * @version 2017-06-15
 */
public class Zizhibq extends DataEntity<Zizhibq> {
	
	private static final long serialVersionUID = 1L;
	private String zizhimc;		// 资质名称
	private String dsf;
	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	private RenyuanZz[] renyuanzz;

	public RenyuanZz[] getRenyuanzz() {
		return renyuanzz;
	}

	public void setRenyuanzz(RenyuanZz[] renyuanzz) {
		this.renyuanzz = renyuanzz;
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
	public Zizhibq() {
		super();
	}

	public Zizhibq(String id){
		super(id);
	}

	@Length(min=0, max=500, message="资质名称长度必须介于 0 和 500 之间")
	public String getZizhimc() {
		return zizhimc;
	}

	public void setZizhimc(String zizhimc) {
		this.zizhimc = zizhimc;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}