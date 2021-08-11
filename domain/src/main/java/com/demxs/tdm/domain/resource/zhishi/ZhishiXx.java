package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 知识信息操作Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class ZhishiXx extends DataEntity<ZhishiXx> {
	
	private static final long serialVersionUID = 1L;
	private String zhishibh;		// 知识编号
	private String zhishimc;		// 知识名称
	private String zhishilx;		// 知识类型
	private String zhishizy;		// 知识摘要
	private String shangchuanrid;		// 上传人ID
	private String shangchuanr;		// 上传人
	private String suoshubm;		// 所属部门
	private String suoshubmid;		// 所属部门ID
	private Date shangchuansj;		// 上传时间
	private AttachmentInfo fujian;		// 附件集合
	private String fujianid;		//附件id
	private String zhishizt;		// 知识状态：未审核，审核通过
	private Date shenqingsj;		// 申请时间
	private String shenherid;		// 审核人ID
	private String shenher;		// 审核人
	private Date shenhesj;		// 审核时间
	private String shenhejg;		// 审核结果
	private String shenheyj;		// 审核意见
	private String liulanl;		// 浏览量:实时更新
	private String xiazail;		// 下载量:实时更新
	private String pingjunf;		// 平均分:实时更新
	private String pinglunl;		// 评论量：实时更新
	private String zhishilbs;       //知识类别s
	private Boolean submit;		  //是否提交
	private String fangfaid;
	private String  kejianbmid;//知识可见范围
	private ZhishiGlff[] fangfalist;//知识关联方法
	private List<ZhishiKjff> kejianbm = Lists.newArrayList();//知识可见部门
	private List<ZhishiKjffRy> kejianry = Lists.newArrayList();//知识可见人员
	private String kejianbmids;//可见部门ids
	private String kejianryids;//可见人员ids
	private String zhishiglff;
	private String zhishikjfw;
	private String zhishilxmc;//知识类别名称  父级_自己
	private String fangfamcs;//知识关联方法名称
	private String leixingmc;//知识类别名称
	private String xiazaiip;  //知识下载人IP
	private String zhishiwjmc;//知识文件名称
	private String dsf;
	private Boolean shifoukxz; //是否有知识文件下载权限

	private Date beginShangchuansj;	//上传时间（开始）
	private Date endShangchuansj;	//上传时间（结束）
	private Date benginCreateDate;	//创建时间（开始）
	private Date endCreateDate;	//创建时间（结束）

	private User dangqianR;//当前人

	private Integer type;//0提交 1保存

	private String from;//来源
    /**
     * 流程配置类型（0：上传文件，1：手动绘制）
     */
    private String processType;
    public static final String UPLOAD_PROCESS = "0";
    public static final String DRAW_PROCESS = "1";
    /**
     * 上传文件流程图
     */
    private String processImg;
    /**
     * 标记位，是否提交过手绘流程图
     */
    private String hasDraw;


	private String yijian;

	private List<Office> offices = Lists.newArrayList();
	private List<User> users = Lists.newArrayList();
	private List<String> zhishiXxList = Lists.newArrayList();//知识集合 有权限的知识集合 在此集合里面查询知识表

	private String officeAllNames;//可见部门名称集合

	private String userAllNames;//可见人员名称集合

	public User getDangqianR() {
		return dangqianR;
	}

	public void setDangqianR(User dangqianR) {
		this.dangqianR = dangqianR;
	}
	private String caozuoqx;//操作权限 1可编辑 可删除  0只可以查看

	public String getCaozuoqx() {
		return caozuoqx;
	}
	public void setCaozuoqx(String caozuoqx) {
		this.caozuoqx = caozuoqx;
	}

	public Boolean getShifoukxz() {
		return shifoukxz;
	}

	public void setShifoukxz(Boolean shifoukxz) {
		this.shifoukxz = shifoukxz;
	}

	public String getZhishilbs() {
		return zhishilbs;
	}

	public void setZhishilbs(String zhishilbs) {
		this.zhishilbs = zhishilbs;
	}

	
	public ZhishiXx() {
		super();
	}

	public ZhishiXx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="知识编号长度必须介于 0 和 200 之间")
	public String getZhishibh() {
		return zhishibh;
	}

	public void setZhishibh(String zhishibh) {
		this.zhishibh = zhishibh;
	}
	
	@Length(min=0, max=200, message="知识名称长度必须介于 0 和 200 之间")
	public String getZhishimc() {
		return zhishimc;
	}

	public void setZhishimc(String zhishimc) {
		this.zhishimc = zhishimc;
	}
	
	@Length(min=0, max=200, message="知识类型长度必须介于 0 和 200 之间")
	public String getZhishilx() {
		return zhishilx;
	}

	public void setZhishilx(String zhishilx) {
		this.zhishilx = zhishilx;
	}
	
	@Length(min=0, max=2000, message="知识摘要长度必须介于 0 和 2000 之间")
	public String getZhishizy() {
		return zhishizy;
	}

	public void setZhishizy(String zhishizy) {
		this.zhishizy = zhishizy;
	}
	
	@Length(min=0, max=200, message="上传人ID长度必须介于 0 和 200 之间")
	public String getShangchuanrid() {
		return shangchuanrid;
	}

	public void setShangchuanrid(String shangchuanrid) {
		this.shangchuanrid = shangchuanrid;
	}
	
	@Length(min=0, max=200, message="上传人长度必须介于 0 和 200 之间")
	public String getShangchuanr() {
		return shangchuanr;
	}

	public void setShangchuanr(String shangchuanr) {
		this.shangchuanr = shangchuanr;
	}
	
	@Length(min=0, max=200, message="所属部门长度必须介于 0 和 200 之间")
	public String getSuoshubm() {
		return suoshubm;
	}

	public void setSuoshubm(String suoshubm) {
		this.suoshubm = suoshubm;
	}
	
	@Length(min=0, max=200, message="所属部门ID长度必须介于 0 和 200 之间")
	public String getSuoshubmid() {
		return suoshubmid;
	}

	public void setSuoshubmid(String suoshubmid) {
		this.suoshubmid = suoshubmid;
	}
	
	@Length(min=0, max=200, message="上传时间长度必须介于 0 和 200 之间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShangchuansj() {
		return shangchuansj;
	}

	public void setShangchuansj(Date shangchuansj) {
		this.shangchuansj = shangchuansj;
	}

	public AttachmentInfo getFujian() {
		return fujian;
	}

	public void setFujian(AttachmentInfo fujian) {
		this.fujian = fujian;
	}

	@Length(min=0, max=200, message="知识状态：未审核，审核通过长度必须介于 0 和 200 之间")
	public String getZhishizt() {
		return zhishizt;
	}

	public void setZhishizt(String zhishizt) {
		this.zhishizt = zhishizt;
	}
	
	@Length(min=0, max=200, message="申请时间长度必须介于 0 和 200 之间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShenqingsj() {
		return shenqingsj;
	}

	public void setShenqingsj(Date shenqingsj) {
		this.shenqingsj = shenqingsj;
	}
	
	@Length(min=0, max=200, message="审核人ID长度必须介于 0 和 200 之间")
	public String getShenherid() {
		return shenherid;
	}

	public void setShenherid(String shenherid) {
		this.shenherid = shenherid;
	}
	
	@Length(min=0, max=200, message="审核人长度必须介于 0 和 200 之间")
	public String getShenher() {
		return shenher;
	}

	public void setShenher(String shenher) {
		this.shenher = shenher;
	}
	
	@Length(min=0, max=200, message="审核时间长度必须介于 0 和 200 之间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShenhesj() {
		return shenhesj;
	}

	public void setShenhesj(Date shenhesj) {
		this.shenhesj = shenhesj;
	}
	
	@Length(min=0, max=200, message="审核结果长度必须介于 0 和 200 之间")
	public String getShenhejg() {
		return shenhejg;
	}

	public void setShenhejg(String shenhejg) {
		this.shenhejg = shenhejg;
	}
	
	@Length(min=0, max=2000, message="审核意见长度必须介于 0 和 2000 之间")
	public String getShenheyj() {
		return shenheyj;
	}

	public void setShenheyj(String shenheyj) {
		this.shenheyj = shenheyj;
	}
	
	@Length(min=0, max=200, message="浏览量:实时更新长度必须介于 0 和 200 之间")
	public String getLiulanl() {
		return liulanl;
	}

	public void setLiulanl(String liulanl) {
		this.liulanl = liulanl;
	}
	
	@Length(min=0, max=200, message="下载量:实时更新长度必须介于 0 和 200 之间")
	public String getXiazail() {
		return xiazail;
	}

	public void setXiazail(String xiazail) {
		this.xiazail = xiazail;
	}
	
	@Length(min=0, max=200, message="平均分:实时更新长度必须介于 0 和 200 之间")
	public String getPingjunf() {
		return pingjunf;
	}

	public void setPingjunf(String pingjunf) {
		this.pingjunf = pingjunf;
	}
	
	@Length(min=0, max=200, message="评论量：实时更新长度必须介于 0 和 200 之间")
	public String getPinglunl() {
		return pinglunl;
	}

	public void setPinglunl(String pinglunl) {
		this.pinglunl = pinglunl;
	}


	public String getZhishiglff() {
		return zhishiglff;
	}

	public void setZhishiglff(String zhishiglff) {
		this.zhishiglff = zhishiglff;
	}

	public String getZhishikjfw() {
		return zhishikjfw;
	}

	public void setZhishikjfw(String zhishikjfw) {
		this.zhishikjfw = zhishikjfw;
	}

	public String getZhishilxmc() {
		return zhishilxmc;
	}

	public void setZhishilxmc(String zhishilxmc) {
		this.zhishilxmc = zhishilxmc;
	}

	public String getFangfaid() {
		return fangfaid;
	}

	public void setFangfaid(String fangfaid) {
		this.fangfaid = fangfaid;
	}

	public String getKejianbmid() {
		return kejianbmid;
	}

	public void setKejianbmid(String kejianbmid) {
		this.kejianbmid = kejianbmid;
	}

	public String getFangfamcs() {
		return fangfamcs;
	}

	public void setFangfamcs(String fangfamcs) {
		this.fangfamcs = fangfamcs;
	}

	public String getLeixingmc() {
		return leixingmc;
	}

	public void setLeixingmc(String leixingmc) {
		this.leixingmc = leixingmc;
	}

	public ZhishiGlff[] getFangfalist() {
		return fangfalist;
	}

	public void setFangfalist(ZhishiGlff[] fangfalist) {
		this.fangfalist = fangfalist;
	}

	public List<ZhishiKjff> getKejianbm() {
		return kejianbm;
	}

	public void setKejianbm(List<ZhishiKjff> kejianbm) {
		this.kejianbm = kejianbm;
	}

	public List<ZhishiKjffRy> getKejianry() {
		return kejianry;
	}

	public void setKejianry(List<ZhishiKjffRy> kejianry) {
		this.kejianry = kejianry;
	}

	public String getXiazaiip() {
		return xiazaiip;
	}

	public void setXiazaiip(String xiazaiip) {
		this.xiazaiip = xiazaiip;
	}

	public String getZhishiwjmc() {
		return zhishiwjmc;
	}

	public void setZhishiwjmc(String zhishiwjmc) {
		this.zhishiwjmc = zhishiwjmc;
	}

	public Boolean getSubmit() {
		if(type==null){
			return null;
		}else{
			return type==0?true:false;
		}

	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}

	public Date getBeginShangchuansj() {
		return beginShangchuansj;
	}

	public void setBeginShangchuansj(Date beginShangchuansj) {
		this.beginShangchuansj = beginShangchuansj;
	}

	public Date getEndShangchuansj() {
		return endShangchuansj;
	}

	public void setEndShangchuansj(Date endShangchuansj) {
		this.endShangchuansj = endShangchuansj;
	}

	public Date getBenginCreateDate() {
		return benginCreateDate;
	}

	public void setBenginCreateDate(Date benginCreateDate) {
		this.benginCreateDate = benginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getFujianid() {
		return fujianid;
	}

	public void setFujianid(String fujianid) {
		this.fujianid = fujianid;
	}

	public String getKejianbmids() {
		return kejianbmids;
	}

	public void setKejianbmids(String kejianbmids) {
		this.kejianbmids = kejianbmids;
	}

	public String getKejianryids() {
		return kejianryids;
	}

	public void setKejianryids(String kejianryids) {
		this.kejianryids = kejianryids;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Office> getOffices() {
		return offices;
	}

	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public List<String> getZhishiXxList() {
		return zhishiXxList;
	}

	public void setZhishiXxList(List<String> zhishiXxList) {
		this.zhishiXxList = zhishiXxList;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


	public String getOfficeAllNames() {
		StringBuilder allName = new StringBuilder();
		if(CollectionUtils.isNotEmpty(offices)){
			for(Office o:offices){
				allName.append(o.getName()+",");
			}
		}
		return allName.toString();
	}

	public void setOfficeAllNames(String officeAllNames) {
		this.officeAllNames = officeAllNames;
	}

	public String getUserAllNames() {
		StringBuilder allName = new StringBuilder();
		if(CollectionUtils.isNotEmpty(users)){
			for(User u:users){
				allName.append(u.getName()+",");
			}
		}
		return allName.toString();
	}

	public void setUserAllNames(String userAllNames) {
		this.userAllNames = userAllNames;
	}

	public String getYijian() {
		return yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
	}

    public String getProcessType() {
        return processType;
    }

    public ZhishiXx setProcessType(String processType) {
        this.processType = processType;
        return this;
    }

    public String getProcessImg() {
        return processImg;
    }

    public ZhishiXx setProcessImg(String processImg) {
        this.processImg = processImg;
        return this;
    }

    public String getHasDraw() {
        return hasDraw;
    }

    public ZhishiXx setHasDraw(String hasDraw) {
        this.hasDraw = hasDraw;
        return this;
    }

    public static final String ACTSOURCE = "act";

	public static final String CHEXIAO = "-1";
	public static final String BOHUI = "0";
	public static final String TONGGUO = "1";

	public static final String EDIT = "1";
	public static final String VIEW = "0";
}