package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 统计标题Entity
 * @author wbq
 * @version 2017-08-07
 */
public class Tongjibt extends DataEntity<Tongjibt> {
	
	private static final long serialVersionUID = 1L;
	private String tongjiid;		// 统计ID
	private String biaotimc;		// 标题名称
	private String parentid;		// 父ID
	private Long shunxu;		// 顺序
	private String lujing;		// 路径
	private Long subcount;		// 子节点数
	private String sqlstr;		// sql语句
	private Long jiediancj;		// 节点层级
	private Long jiediangd;		// 节点高度
	private Long jiediankd;		// 节点宽度
	private Long isheji;		// 是否合计
	private Long isleaf;		// 是否叶子节点
	private String detailurl;		// 详细表url
	private String parentbiaotimc; //父标题名称
	private String hengzuobid; //横坐标id
	public Tongjibt() {
		super();
	}

	public Tongjibt(String id){
		super(id);
	}


	public String getHengzuobid() {
		return hengzuobid;
	}

	public void setHengzuobid(String hengzuobid) {
		this.hengzuobid = hengzuobid;
	}

	public String getTongjiid() {
		return tongjiid;
	}

	public void setTongjiid(String tongjiid) {
		this.tongjiid = tongjiid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Length(min=0, max=100, message="标题名称长度必须介于 0 和 100 之间")
	public String getBiaotimc() {
		return biaotimc;
	}

	public void setBiaotimc(String biaotimc) {
		this.biaotimc = biaotimc;
	}

	
	public Long getShunxu() {
		return shunxu;
	}

	public void setShunxu(Long shunxu) {
		this.shunxu = shunxu;
	}
	
	@Length(min=0, max=100, message="路径长度必须介于 0 和 100 之间")
	public String getLujing() {
		return lujing;
	}

	public void setLujing(String lujing) {
		this.lujing = lujing;
	}
	
	public Long getSubcount() {
		return subcount;
	}

	public void setSubcount(Long subcount) {
		this.subcount = subcount;
	}
	
	@Length(min=0, max=100, message="sql语句长度必须介于 0 和 100 之间")
	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	
	public Long getJiediancj() {
		return jiediancj;
	}

	public void setJiediancj(Long jiediancj) {
		this.jiediancj = jiediancj;
	}
	
	public Long getJiediangd() {
		return jiediangd;
	}

	public void setJiediangd(Long jiediangd) {
		this.jiediangd = jiediangd;
	}
	
	public Long getJiediankd() {
		return jiediankd;
	}

	public void setJiediankd(Long jiediankd) {
		this.jiediankd = jiediankd;
	}
	
	public Long getIsheji() {
		return isheji;
	}

	public void setIsheji(Long isheji) {
		this.isheji = isheji;
	}
	
	public Long getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Long isleaf) {
		this.isleaf = isleaf;
	}
	
	@Length(min=0, max=200, message="详细表url长度必须介于 0 和 200 之间")
	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}

	public String getParentbiaotimc() {
		return parentbiaotimc;
	}

	public void setParentbiaotimc(String parentbiaotimc) {
		this.parentbiaotimc = parentbiaotimc;
	}

	public static void sortList(List<Tongjibt> list, List<Tongjibt> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			Tongjibt e = sourcelist.get(i);
			if (e.getParentid()!=null && e.getParentid().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					Tongjibt child = sourcelist.get(j);
					if (child.getParentid()!=null&& child.getParentid().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
	
}