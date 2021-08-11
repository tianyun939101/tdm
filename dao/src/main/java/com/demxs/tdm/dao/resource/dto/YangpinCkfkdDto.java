package com.demxs.tdm.dao.resource.dto;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 样品入库/出库/返库操作Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class YangpinCkfkdDto extends DataEntity<YangpinCkfkdDto> {

	private static final long serialVersionUID = 1L;
	private String shujubq;			//数据标签（一个出库单的样品对应一个标签）
	private String chukuglrwid;       //出库关联任务id

	public String getShujubq() {
		return shujubq;
	}

	public void setShujubq(String shujubq) {
		this.shujubq = shujubq;
	}

	public String getChukuglrwid() {
		return chukuglrwid;
	}

	public void setChukuglrwid(String chukuglrwid) {
		this.chukuglrwid = chukuglrwid;
	}
}