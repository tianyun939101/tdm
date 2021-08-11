package com.demxs.tdm.domain.resource.renyuan;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 个人日程Entity
 * @author zhangdengcai
 * @version 2017-08-09
 */
public class RenyuanGrrc extends DataEntity<RenyuanGrrc> {
	
	private static final long serialVersionUID = 1L;
	private String kaishisj;		// 开始时间
	private String jieshusj;		// 结束时间
	private String biaoti;		//标题
	private String neirong;		// 内容
	private String shifouwc;		//是否完成
	private String color;		//根据完成状态返回不同颜色
	
	public RenyuanGrrc() {
		super();
	}

	public RenyuanGrrc(String id){
		super(id);
	}

	@Length(min=0, max=200, message="开始时间长度必须介于 0 和 200 之间")
	public String getKaishisj() {
		return kaishisj;
	}

	public void setKaishisj(String kaishisj) {
		this.kaishisj = kaishisj;
	}
	
	@Length(min=0, max=200, message="结束时间长度必须介于 0 和 200 之间")
	public String getJieshusj() {
		return jieshusj;
	}

	public void setJieshusj(String jieshusj) {
		this.jieshusj = jieshusj;
	}

	@Length(min=0, max=2000, message="标题长度必须介于 0 和 2000 之间")
	public String getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	@Length(min=0, max=2000, message="内容长度必须介于 0 和 2000 之间")
	public String getNeirong() {
		return neirong;
	}

	public void setNeirong(String neirong) {
		this.neirong = neirong;
	}

	public String getShifouwc() {
		return shifouwc;
	}

	public void setShifouwc(String shifouwc) {
		this.shifouwc = shifouwc;
	}

	public String getColor() {
		if(Global.YES.equals(shifouwc)){
			color = "#3AB1FF";
		}else if(Global.NO.equals(shifouwc)){
			color = "#F15A24";
		}
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}