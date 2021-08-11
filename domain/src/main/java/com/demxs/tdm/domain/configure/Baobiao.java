package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 报表Entity
 * @author wbq
 * @version 2017-08-04
 */
public class Baobiao extends DataEntity<Baobiao> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 报表名称
	
	public Baobiao() {
		super();
	}

	public Baobiao(String id){
		super(id);
	}

	@Length(min=1, max=100, message="报表名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}