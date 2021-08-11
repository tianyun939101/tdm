package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 数据集Entity
 * @author wbq
 * @version 2017-08-03
 */
public class Shujuj extends DataEntity<Shujuj> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 数据集名称

	private String master;		// 主表

	private List<Joininfo> joininfo;

	public Shujuj() {
		super();
	}

	public Shujuj(String id){
		super(id);
	}

	@Length(min=1, max=100, message="数据集名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}


	public List<Joininfo> getJoininfo() {
		return joininfo;
	}

	public void setJoininfo(List<Joininfo> joininfo) {
		this.joininfo = joininfo;
	}
}