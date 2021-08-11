package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备信息Entity
 * @author sunjunhui
 * @version 2017-06-14
 */
public class Shebei extends DataEntity<Shebei> {

	private static final long serialVersionUID = 1L;
	/**********************基本信息*******************/
	private String shebeimc; //设备名称
	private String shebeibh;//设备编码
	private String gudingzcbm;//固定资产编码
	private ShebeiLx shebeilx;//设备类型
	private String shebeilxId;
	private String shebeixh;//设备型号
	private Office shiyongdw;//使用单位
	private String shebeism;//设备说明
	private String shebeizt;//设备状态  数据字典
	private String shebeitp;//设备图片集合 存放数据库
	private String shebeizl;//设备资料 存放数据库
	private String recordNo;//设备履历编号
	private String kind;//设备类型 [01:台、02:套]
	private String parentId;//父级编号
	private String modelNorm;//规格型号
	private String measureScope;//测量范围或主要参数

	private List<AttachmentInfo> zpList = Lists.newArrayList();//设备图片集合
	private List<AttachmentInfo> zlList = Lists.newArrayList();//设备资料集合

	//所属科室
	private String officeId;
	private Office office;
	//所属技术室
	private String techniqueId;
	private Office technique;
	//子设备
	private List<Shebei> children;
	//设备编号
	private List<String> ids;
	//批量修改
    private String idList;

    /**********************计量信息*******************/
    //量程/范围
    private String range;
    //允差或不确定等级
    private String allowLevel;
    //管理分类
    private String classification;
    //是否是专用测试设备
    private String specialUse;
    //管理状态
    private String managementState;
    //有效日期
    private Date effectiveDate;
    //计量结果
    private String meteringResult;
    //限用范围
    private String limitedScope;

	/**********************使用信息*******************/
	private ShebeiCfwz shebeicfwz;//设备存放位置
	private  String shebeily;//设备来源
	private String shifouxypq;//是否需要排期
	private User jlsyfzr;//计量溯源负责人
	private String jlsyfzrdh;//计量溯源负责人电话
	private String shebeirl;//设备容量 统自动生成工位编号，用于排期
	private User shebeigly;//设备管理员
    private String shebeiglyId;
	private String shebeiglydh;//设备管理员电话
	private String shebeizz;//设备人员资质 存放数据库
	private List<Aptitude> zzList = Lists.newArrayList();//设备资质
	private String shebeiyt;//设备用途  数据字典
    private String isMetering;//是否已计量
    //下次计量日期
    private Date nextTime;

	private String certiNum;	//有资质设备操作人员数量

	/**********************采购信息*******************/
	private Changshanggysxx shebeics;//设备厂商
	private Changshanggysxx shebeigys;//设备供应商
	private String zzsMc;//制造商名称
	private String guobie;//国别  数据字典
	private String goumaijg;//购买价格
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date goumairq;//购买日期、验收日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date shengcrq;//生产日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date touyongrq;//投用日期
	private String chuchangbh;//出厂编号
	private Date baoxiusxrq;//保修失效日期
	private String shebeigl;//设备功率（KW）
	private String shiyongtj;//使用条件
	private String contract;//合同编号
	/**********************环境要求*******************/
	private String wendusx;//温度上限
	private String wenduxx;//温度下限
	private String zaoshengsx;//噪声上限
	private String zaoshengxx;//噪声下限
	private String shidusx;//湿度上限可试验样品数量
	private String shiduxx;//湿度下限
	private String zhendongsx;//振动上限
	private String zhendongxx;//振动下限
	private String beizhu;//备注

	private String yijian;

	private String overYijian;


	/**********************其他 别的模块*******************/
	private String keshiyanypsl;//
	private String shebeiqtzt;  //设备启停状态 新增默认是启动 1启动 0停止  数据字典
	private String visable;//是否在看版显示
	private Integer serialNumber;//序号


	/**********************设备板块*******************/
	private String shebeibk;//设备板块 字典

	private LabInfo labInfo;//所属试验室


	private String type;//保存 提交 1 保存 0提交

	private String addAuditStatus;//新增审核状态

	private String overAuditStatus;//报废审核状态
	private String from;

	private Date firstStartDate;//首次启动时间
	/**********************设备新增字段**********************/
	//是否由替代设备
	private String isReplaceSB;
	//年度总工时数(单位：小时）
	private String totalWorkHours;
	private List<ShebeiGShi> shebeiGShis;
	private String businessProcess; //对应业务流程

	private String firstPlanHours;	//计划工时
	private String firstpracticalHours;	//实际工时
	private String firstUserRatio;	//利用率
	private String firstFailure;;	//故障率
	private String secPlanHours;
	private String secpracticalHours;
	private String secUserRatio;
	private String secFailure;
	private String thirPlanHours;
	private String thirpracticalHours;
	private String thirUserRatio;
	private String thirFailure;
	private String fourPlanHours;
	private String fourpracticalHours;
	private String fourUserRatio;
	private String fourFailure;





	/**********************设备新增字段*******************/
    //设备名称 上面有 a
    //新设备编号 b
    private String newCode;
    //固定资产编号 上面有 c
    //设备运行状态 d
    private String runningState;
    //所属科室 上面有 e
    //设备安放地点 上面有 f
    //设备负责人 g
    private String responsibleId;
    private User responsible;
    //设备资质 上面有
    //维护周期 h
    private String maintainCycle;
    //上次维护时间 i
    private Date lastMaintainDate;
    //维护内容 j
    private String maintainContent;
    //维护工具 k
    private String maintainUtil;
    //是否有维修计划 l
    private String isMaintainPlan;
    //使用环境要求 m
    private String environmentRequire;
    //是否需要计量 n
    private String isNeedMetering;
    //设备完好标签 o
    private String  intactLabel;
    //是否参与适航验证试验 p
    private String isAirworthy;
    //备件数量 q
    private String sparePartsNum;
    //设备培养情况 r
    private String culture;
    //备件管理要求 s
    private String sparePartsManage;
    //备注 上面有
    //设备照片 上面有 t
    //操作规程及操作文件 u
    private String operationFile;
    //维护作业文件 v
    private String operationDoc;

    private  String  defendPeriod;
    //维护状态
    private String operationStatus;

    //新增设备操作文件
	private List<EquipmentFile>  equipmentFileList;
	//设备维护内容信息
	private List<EquipmentInfo>  equipmentInfoList;
	//子设备列表
	private List<Shebei> subEquipmentList;

	private  EquipmentInfo  equipmentInfo;

	private  List<EquipmentContent>  equipmentContentList;


	private  String subCenter;


    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public void setShebeilxId(String shebeilxId) {
		this.shebeilxId = shebeilxId;
	}

	public List<EquipmentContent> getEquipmentContentList() {
		return equipmentContentList;
	}

	public void setEquipmentContentList(List<EquipmentContent> equipmentContentList) {
		this.equipmentContentList = equipmentContentList;
	}

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public EquipmentInfo getEquipmentInfo() {
		return equipmentInfo;
	}

	public void setEquipmentInfo(EquipmentInfo equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}

	public String getDefendPeriod() {
		return defendPeriod;
	}

	public void setDefendPeriod(String defendPeriod) {
		this.defendPeriod = defendPeriod;
	}

	public List<EquipmentFile> getEquipmentFileList() {
		return equipmentFileList;
	}

	public void setEquipmentFileList(List<EquipmentFile> equipmentFileList) {
		this.equipmentFileList = equipmentFileList;
	}

	public List<EquipmentInfo> getEquipmentInfoList() {
		return equipmentInfoList;
	}

	public void setEquipmentInfoList(List<EquipmentInfo> equipmentInfoList) {
		this.equipmentInfoList = equipmentInfoList;
	}

	private List<Map<String,Object>> djList = new ArrayList<>();

	public Shebei() {
		super();
	}
	public Shebei(String id) {
		this.id = id;
	}

	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}

	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
	}

	public String getGudingzcbm() {
		return gudingzcbm;
	}

	public void setGudingzcbm(String gudingzcbm) {
		this.gudingzcbm = gudingzcbm;
	}

	public ShebeiLx getShebeilx() {
		return shebeilx;
	}

	public void setShebeilx(ShebeiLx shebeilx) {
		this.shebeilx = shebeilx;
	}

	public String getShebeixh() {
		return shebeixh;
	}

	public void setShebeixh(String shebeixh) {
		this.shebeixh = shebeixh;
	}

	public Office getShiyongdw() {
		return shiyongdw;
	}

	public void setShiyongdw(Office shiyongdw) {
		this.shiyongdw = shiyongdw;
	}

	public String getShebeism() {
		return shebeism;
	}

	public void setShebeism(String shebeism) {
		this.shebeism = shebeism;
	}


	public ShebeiCfwz getShebeicfwz() {
		return shebeicfwz;
	}

	public void setShebeicfwz(ShebeiCfwz shebeicfwz) {
		this.shebeicfwz = shebeicfwz;
	}

	public String getShebeily() {
		return shebeily;
	}

	public void setShebeily(String shebeily) {
		this.shebeily = shebeily;
	}

	public String getShifouxypq() {
		return shifouxypq;
	}

	public void setShifouxypq(String shifouxypq) {
		this.shifouxypq = shifouxypq;
	}

	public User getJlsyfzr() {
		return jlsyfzr;
	}

	public void setJlsyfzr(User jlsyfzr) {
		this.jlsyfzr = jlsyfzr;
	}

	public String getJlsyfzrdh() {
		return jlsyfzrdh;
	}

	public void setJlsyfzrdh(String jlsyfzrdh) {
		this.jlsyfzrdh = jlsyfzrdh;
	}

	public String getShebeirl() {
		return shebeirl;
	}

	public void setShebeirl(String shebeirl) {
		this.shebeirl = shebeirl;
	}

	public User getShebeigly() {
		return shebeigly;
	}

	public void setShebeigly(User shebeigly) {
		this.shebeigly = shebeigly;
	}

	public String getShebeiglydh() {
		return shebeiglydh;
	}

	public void setShebeiglydh(String shebeiglydh) {
		this.shebeiglydh = shebeiglydh;
	}



	public Changshanggysxx getShebeics() {
		return shebeics;
	}

	public void setShebeics(Changshanggysxx shebeics) {
		this.shebeics = shebeics;
	}

	public Changshanggysxx getShebeigys() {
		return shebeigys;
	}

	public void setShebeigys(Changshanggysxx shebeigys) {
		this.shebeigys = shebeigys;
	}



	public String getGoumaijg() {
		return goumaijg;
	}

	public void setGoumaijg(String goumaijg) {
		this.goumaijg = goumaijg;
	}

	public Date getGoumairq() {
		return goumairq;
	}

	public void setGoumairq(Date goumairq) {
		this.goumairq = goumairq;
	}

	public Date getTouyongrq() {
		return touyongrq;
	}

	public void setTouyongrq(Date touyongrq) {
		this.touyongrq = touyongrq;
	}


	public void setChuchangbh(String chuchangbh) {
		this.chuchangbh = chuchangbh;
	}

	public Date getBaoxiusxrq() {
		return baoxiusxrq;
	}

	public void setBaoxiusxrq(Date baoxiusxrq) {
		this.baoxiusxrq = baoxiusxrq;
	}

	public String getShebeigl() {
		return shebeigl;
	}

	public void setShebeigl(String shebeigl) {
		this.shebeigl = shebeigl;
	}

	public String getShiyongtj() {
		return shiyongtj;
	}

	public void setShiyongtj(String shiyongtj) {
		this.shiyongtj = shiyongtj;
	}

	public String getWendusx() {
		return wendusx;
	}

	public void setWendusx(String wendusx) {
		this.wendusx = wendusx;
	}

	public String getWenduxx() {
		return wenduxx;
	}

	public void setWenduxx(String wenduxx) {
		this.wenduxx = wenduxx;
	}

	public String getZaoshengsx() {
		return zaoshengsx;
	}

	public void setZaoshengsx(String zaoshengsx) {
		this.zaoshengsx = zaoshengsx;
	}

	public String getZaoshengxx() {
		return zaoshengxx;
	}

	public void setZaoshengxx(String zaoshengxx) {
		this.zaoshengxx = zaoshengxx;
	}

	public String getShidusx() {
		return shidusx;
	}

	public void setShidusx(String shidusx) {
		this.shidusx = shidusx;
	}

	public String getShiduxx() {
		return shiduxx;
	}

	public void setShiduxx(String shiduxx) {
		this.shiduxx = shiduxx;
	}

	public String getZhendongsx() {
		return zhendongsx;
	}

	public void setZhendongsx(String zhendongsx) {
		this.zhendongsx = zhendongsx;
	}

	public String getZhendongxx() {
		return zhendongxx;
	}

	public void setZhendongxx(String zhendongxx) {
		this.zhendongxx = zhendongxx;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getKeshiyanypsl() {
		return keshiyanypsl;
	}

	public void setKeshiyanypsl(String keshiyanypsl) {
		this.keshiyanypsl = keshiyanypsl;
	}

	public String getBusinessProcess() {
		return businessProcess;
	}

	public void setBusinessProcess(String businessProcess) {
		this.businessProcess = businessProcess;
	}

	public String getShebeiqtzt() {
		return shebeiqtzt;
	}

	public void setShebeiqtzt(String shebeiqtzt) {
		this.shebeiqtzt = shebeiqtzt;
	}

	public String getShebeizt() {
		return shebeizt;
	}

	public void setShebeizt(String shebeizt) {
		this.shebeizt = shebeizt;
	}

	public String getShebeitp() {
		return shebeitp;
	}

	public void setShebeitp(String shebeitp) {
		this.shebeitp = shebeitp;
	}

	public String getShebeizl() {
		return shebeizl;
	}

	public void setShebeizl(String shebeizl) {
		this.shebeizl = shebeizl;
	}


	public void setShebeizz(String shebeizz) {
		this.shebeizz = shebeizz;
	}

	public List<AttachmentInfo> getZpList() {
		return zpList;
	}

	public void setZpList(List<AttachmentInfo> zpList) {
		this.zpList = zpList;
	}

	public List<AttachmentInfo> getZlList() {
		return zlList;
	}

	public void setZlList(List<AttachmentInfo> zlList) {
		this.zlList = zlList;
	}

	public List<Aptitude> getZzList() {
		return zzList;
	}

	public void setZzList(List<Aptitude> zzList) {
		this.zzList = zzList;
	}

	public String getShebeiyt() {
		return shebeiyt;
	}

	public void setShebeiyt(String shebeiyt) {
		this.shebeiyt = shebeiyt;
	}

	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}

	public String getShebeibk() {
		return shebeibk;
	}

	public void setShebeibk(String shebeibk) {
		this.shebeibk = shebeibk;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Shebei)) return false;
		if (!super.equals(o)) return false;

		Shebei shebei = (Shebei) o;

		return shebeibh.equals(shebei.shebeibh);
	}

	/*@Override
	public int hashCode() {
		return shebeibh.hashCode();
	}*/

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddAuditStatus() {
		return addAuditStatus;
	}

	public void setAddAuditStatus(String addAuditStatus) {
		this.addAuditStatus = addAuditStatus;
	}

	public String getOverAuditStatus() {
		return overAuditStatus;
	}

	public void setOverAuditStatus(String overAuditStatus) {
		this.overAuditStatus = overAuditStatus;
	}


	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<Map<String, Object>> getDjList() {
		return djList;
	}

	public void setDjList(List<Map<String, Object>> djList) {
		this.djList = djList;
	}

	public String getVisable() {
		return visable;
	}

	public void setVisable(String visable) {
		this.visable = visable;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getYijian() {
		return yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
	}

	public String getOverYijian() {
		return overYijian;
	}

	public void setOverYijian(String overYijian) {
		this.overYijian = overYijian;
	}

	public Date getFirstStartDate() {
		return firstStartDate;
	}

	public void setFirstStartDate(Date firstStartDate) {
		this.firstStartDate = firstStartDate;
	}

    public String getIsMetering() {
        return isMetering;
    }

    public Shebei setIsMetering(String isMetering) {
        this.isMetering = isMetering;
        return this;
    }

    public static  final String BAOCUN = "1";
	public static final String TIJIAO  = "0";

    public String getRange() {
        return range;
    }

    public Shebei setRange(String range) {
        this.range = range;
        return this;
    }

    public String getAllowLevel() {
        return allowLevel;
    }

    public Shebei setAllowLevel(String allowLevel) {
        this.allowLevel = allowLevel;
        return this;
    }

    public String getClassification() {
        return classification;
    }

    public Shebei setClassification(String classification) {
        this.classification = classification;
        return this;
    }

    public String getSpecialUse() {
        return specialUse;
    }

    public Shebei setSpecialUse(String specialUse) {
        this.specialUse = specialUse;
        return this;
    }

    public String getManagementState() {
        return managementState;
    }

    public Shebei setManagementState(String managementState) {
        this.managementState = managementState;
        return this;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Shebei setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public String getMeteringResult() {
        return meteringResult;
    }

    public Shebei setMeteringResult(String meteringResult) {
        this.meteringResult = meteringResult;
        return this;
    }

    public String getLimitedScope() {
        return limitedScope;
    }

    public Shebei setLimitedScope(String limitedScope) {
        this.limitedScope = limitedScope;
        return this;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public Shebei setNextTime(Date nextTime) {
        this.nextTime = nextTime;
        return this;
    }

    public String getNewCode() {
        return newCode;
    }

    public Shebei setNewCode(String newCode) {
        this.newCode = newCode;
        return this;
    }

    public String getRunningState() {
        return runningState;
    }

    public Shebei setRunningState(String runningState) {
        this.runningState = runningState;
        return this;
    }

    public User getResponsible() {
        return responsible;
    }

    public Shebei setResponsible(User responsible) {
        this.responsible = responsible;
        return this;
    }

    public String getResponsibleId() {
        return responsibleId;
    }

    public Shebei setResponsibleId(String responsibleId) {
        this.responsibleId = responsibleId;
        return this;
    }

    public String getMaintainCycle() {
        return maintainCycle;
    }

    public Shebei setMaintainCycle(String maintainCycle) {
        this.maintainCycle = maintainCycle;
        return this;
    }

    public String getMaintainCycele() {
        return maintainCycle;
    }

    public Shebei setMaintainCycele(String maintainCycle) {
        this.maintainCycle = maintainCycle;
        return this;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getLastMaintainDate() {
        return lastMaintainDate;
    }

    public Shebei setLastMaintainDate(Date lastMaintainDate) {
        this.lastMaintainDate = lastMaintainDate;
        return this;
    }

    public String getMaintainContent() {
        return maintainContent;
    }

    public Shebei setMaintainContent(String maintainContent) {
        this.maintainContent = maintainContent;
        return this;
    }

    public String getMaintainUtil() {
        return maintainUtil;
    }

    public Shebei setMaintainUtil(String maintainUtil) {
        this.maintainUtil = maintainUtil;
        return this;
    }

    public String getIsMaintainPlan() {
        return isMaintainPlan;
    }

    public Shebei setIsMaintainPlan(String isMaintainPlan) {
        this.isMaintainPlan = isMaintainPlan;
        return this;
    }

    public String getEnvironmentRequire() {
        return environmentRequire;
    }

    public Shebei setEnvironmentRequire(String environmentRequire) {
        this.environmentRequire = environmentRequire;
        return this;
    }

    public String getIsNeedMetering() {
        return isNeedMetering;
    }

    public Shebei setIsNeedMetering(String isNeedMetering) {
        this.isNeedMetering = isNeedMetering;
        return this;
    }

    public String getIntactLabel() {
        return intactLabel;
    }

    public Shebei setIntactLabel(String intactLabel) {
        this.intactLabel = intactLabel;
        return this;
    }

    public String getIsAirworthy() {
        return isAirworthy;
    }

    public Shebei setIsAirworthy(String isAirworthy) {
        this.isAirworthy = isAirworthy;
        return this;
    }

    public String getSparePartsNum() {
        return sparePartsNum;
    }

    public Shebei setSparePartsNum(String sparePartsNum) {
        this.sparePartsNum = sparePartsNum;
        return this;
    }

    public String getCulture() {
        return culture;
    }

    public Shebei setCulture(String culture) {
        this.culture = culture;
        return this;
    }

    public String getSparePartsManage() {
        return sparePartsManage;
    }

    public Shebei setSparePartsManage(String sparePartsManage) {
        this.sparePartsManage = sparePartsManage;
        return this;
    }

    public String getOperationFile() {
        return operationFile;
    }

    public Shebei setOperationFile(String operationFile) {
        this.operationFile = operationFile;
        return this;
    }

    public String getOperationDoc() {
        return operationDoc;
    }

    public Shebei setOperationDoc(String operationDoc) {
        this.operationDoc = operationDoc;
        return this;
    }


	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}


	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public void setModelNorm(String modelNorm) {
		this.modelNorm = modelNorm;
	}


	public void setMeasureScope(String measureScope) {
		this.measureScope = measureScope;
	}

	public List<Shebei> getSubEquipmentList() {
		return subEquipmentList;
	}

	public void setSubEquipmentList(List<Shebei> subEquipmentList) {
		this.subEquipmentList = subEquipmentList;
	}

	public List<Shebei> getChildren() {
    	if(children == null){
    		children = new ArrayList<>();
		}
		return children;
	}

	public void setChildren(List<Shebei> children) {
		this.children = children;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public Office getOffice() {
		return office;
	}

	public String getCertiNum() {
		return certiNum;
	}

	public void setCertiNum(String certiNum) {
		this.certiNum = certiNum;
	}

	public String getZzsMc() {
		return zzsMc;
	}

	public void setZzsMc(String zzsMc) {
		this.zzsMc = zzsMc;
	}

	public Date getShengcrq() {
		return shengcrq;
	}

	public void setShengcrq(Date shengcrq) {
		this.shengcrq = shengcrq;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getIsReplaceSB() {
		return isReplaceSB;
	}

	public void setIsReplaceSB(String isReplaceSB) {
		this.isReplaceSB = isReplaceSB;
	}

	public String getTotalWorkHours() {
		return totalWorkHours;
	}

	public void setTotalWorkHours(String totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
	}

	public List<ShebeiGShi> getShebeiGShis() {
		return shebeiGShis;
	}

	public void setShebeiGShis(List<ShebeiGShi> shebeiGShis) {
		this.shebeiGShis = shebeiGShis;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	public String getExportMaintainCycle(){
		return maintainCycle;
	}
	public String getExportLastMaintainDate(){
		if(null != lastMaintainDate){
			return new SimpleDateFormat("yyyy/MM/dd").format(lastMaintainDate);
		}
		return null;
	}

	public String gerExportMaintainContent(){
		return maintainContent;
	}

	public String getExportIsMaintainPlan(){
		return isMaintainPlan;
	}
	public LabInfo getLabInfo() {
		return labInfo;
	}
	public String getShebeizz() {
		return shebeizz;
	}
	public String getExportIsNeedMetering(){
		return isNeedMetering;
	}
	public String getShebeilxId() {
		return shebeilxId;
	}
	public String getKind() {
		return kind;
	}
	/**************************Excel导出begin******************************/
	@ExcelField(title = "   ",sort = 1,type = 1, align = 2)
	public String getList(){
		if("01".equals(kind)){
			return "";
		}else{
			return "*";
		}
	}
    @ExcelField(title = "设备名称",sort = 2,type = 1)
    public String getExportName(){
        return shebeimc;
    }
    @ExcelField(title = "新设备编号",sort = 3,type = 1)
    public String getExportCode(){
        return newCode;
    }
    @ExcelField(title = "固定资产编号",sort = 4,type = 1)
    public String getExportGdzcbh(){
        return gudingzcbm;
    }
	@ExcelField(title = "设备台套",sort = 5,type = 1)
	public String getKinds() {
		if("01".equals(kind)){
			return "台";
		}else{
			return "套";
		}
	}
    @ExcelField(title = "设备类型",sort = 6,type = 1)
	 public String getShebeilxName(){
    	if(shebeilx!=null){
			return shebeilx.getLeixingmc();
		}
    	return null;
	}
	@ExcelField(title = "出厂/设备编号",sort =7,type = 1)
	public String getChuchangbh() {
		return chuchangbh;
	}
    @ExcelField(title = "设备运行状态",sort = 8,dictType = "equipment_running_state",type = 1)
    public String getExportRunningState(){
        return runningState;
    }
    @ExcelField(title = "所属科室",sort = 9,type = 1)
    public String getExportOfficcName(){
		if(office != null){
			return office.getName();
		}
		return null;
    }
	@ExcelField(title = "所属试验室",sort = 10,type = 1)
	public String getExportLabName(){
    	if(labInfo != null){
			return labInfo.getName();
		}
    	return null;
	}
    @ExcelField(title = "设备安放地点",sort = 11,type = 1)
    public String getExportCfwzName(){
        if(null != shebeicfwz){
            return shebeicfwz.getName();
        }
        return null;
    }
    @ExcelField(title = "设备负责人",sort = 12,type = 1)
    public String getExportResponsibleName(){
        if(null != responsible){
            return responsible.getName();
        }
        return null;
    }
	@ExcelField(title = "设备履历簿编号",sort = 13,type = 1)
	public String getRecordNo() {
		return recordNo;
	}
	@ExcelField(title = "设备资质",sort = 14,type = 1)
	public String getShebeizzName() {
		if(CollectionUtils.isNotEmpty(zzList)){
			StringBuffer zzName = new StringBuffer();
			for(Aptitude aptitude:zzList){
				zzName.append(aptitude.getName()+",");
			}
			StringBuffer replace = zzName.replace(zzName.length() - 1, zzName.length(), "");
			return  replace.toString();
		}
		return null;
	}
	@ExcelField(title = "规格型号",sort = 15,type = 1)
	public String getModelNorm() {
		return modelNorm;
	}
	@ExcelField(title = "测量范围或主要参数",sort = 16,type = 1)
	public String getMeasureScope() {
		return measureScope;
	}
    @ExcelField(title = "使用环境要求",sort = 17,type = 1)
	public String getExportEnvironmentRequire(){
		return environmentRequire;
	}
    @ExcelField(title = "备注",sort = 18,type = 1)
    public String getExportRemarks(){
        return remarks;
    }
    /**************************Excel导出End******************************/

    /**************************Excel导入begin******************************/
    @ExcelField(title = "设备名称",type = 2,sort = 0)
    public void setImportName(String name){
        this.shebeimc = name;
    }
    @ExcelField(title = "新设备编号",type = 2,sort = 1)
    public void setImportNewCode(String code){
        this.newCode = code;
    }
    @ExcelField(title = "固定资产编号",type = 2,sort = 2)
    public void setImportGdzcbh(String bh){
        this.gudingzcbm = bh;
    }
    @ExcelField(title = "维护周期",type = 2,sort = 3)
    public void setImportMaintainCycle(String maintainCycle){
        this.maintainCycle = maintainCycle;
    }
    @ExcelField(title = "上次维护时间",type = 2,sort = 4)
    public void setImportLastMaintainDate(String lastMaintainDate){
        if(StringUtils.isNotBlank(lastMaintainDate)){
            try {
                this.lastMaintainDate = new SimpleDateFormat("yyyy/MM/dd").parse(lastMaintainDate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @ExcelField(title = "设备运行状态",dictType = "equipment_running_state",type = 2,sort = 5)
    public void setImportRunningState(String runningState){
        this.runningState = runningState;
    }
    @ExcelField(title = "所属科室",type = 2,sort = 6)
    public void setImportLabInfo(String labId){
        this.setLabInfoId(labId);
    }
    @ExcelField(title = "设备安放地点",type = 2,sort = 7)
    public void setImportCfwz(String cfwzId){
        if(null == this.shebeicfwz){
            this.shebeicfwz = new ShebeiCfwz();
        }
        this.shebeicfwz.setId(cfwzId);
    }
    @ExcelField(title = "设备负责人",type = 2,sort = 8)
    public void setImportResponsibleId(String responsibleId){
        this.responsibleId = responsibleId;
    }
    @ExcelField(title = "使用环境要求",type = 2,sort = 9)
    public void setImportEnvironmentRequire(String environmentRequire){
        this.environmentRequire = environmentRequire;
    }
    @ExcelField(title = "维护内容",type = 2,sort = 10)
    public void setImportMaintainContent(String maintainContent){
        this.maintainContent = maintainContent;
    }
    @ExcelField(title = "是否有维修计划",dictType = "yes_no",type = 2,sort = 11)
    public void setImportIsMaintainPlan(String isMaintainPlan){
        this.isMaintainPlan = isMaintainPlan;
    }
    @ExcelField(title = "是否需要计量",dictType = "yes_no",type = 2,sort = 12)
    public void setImportIsNeedMetering(String isNeedMetering){
        this.isNeedMetering = isNeedMetering;
    }
    @ExcelField(title = "备注",type = 2,sort = 13)
    public void setImportRemarks(String remarks){
        this.remarks = remarks;
    }
    /**************************Excel导入End******************************/


	public String getTechniqueId() {
		return techniqueId;
	}

	public void setTechniqueId(String techniqueId) {
		this.techniqueId = techniqueId;
	}

	public Office getTechnique() {
		return technique;
	}

	public void setTechnique(Office technique) {
		this.technique = technique;
	}

	public String getFirstPlanHours() {
		return firstPlanHours;
	}

	public void setFirstPlanHours(String firstPlanHours) {
		this.firstPlanHours = firstPlanHours;
	}

	public String getFirstpracticalHours() {
		return firstpracticalHours;
	}

	public void setFirstpracticalHours(String firstpracticalHours) {
		this.firstpracticalHours = firstpracticalHours;
	}

	public String getFirstUserRatio() {
		return firstUserRatio;
	}

	public void setFirstUserRatio(String firstUserRatio) {
		this.firstUserRatio = firstUserRatio;
	}

	public String getFirstFailure() {
		return firstFailure;
	}

	public void setFirstFailure(String firstFailure) {
		this.firstFailure = firstFailure;
	}

	public String getSecPlanHours() {
		return secPlanHours;
	}

	public void setSecPlanHours(String secPlanHours) {
		this.secPlanHours = secPlanHours;
	}

	public String getSecpracticalHours() {
		return secpracticalHours;
	}

	public void setSecpracticalHours(String secpracticalHours) {
		this.secpracticalHours = secpracticalHours;
	}

	public String getSecUserRatio() {
		return secUserRatio;
	}

	public void setSecUserRatio(String secUserRatio) {
		this.secUserRatio = secUserRatio;
	}

	public String getSecFailure() {
		return secFailure;
	}

	public void setSecFailure(String secFailure) {
		this.secFailure = secFailure;
	}

	public String getThirPlanHours() {
		return thirPlanHours;
	}

	public void setThirPlanHours(String thirPlanHours) {
		this.thirPlanHours = thirPlanHours;
	}

	public String getThirpracticalHours() {
		return thirpracticalHours;
	}

	public void setThirpracticalHours(String thirpracticalHours) {
		this.thirpracticalHours = thirpracticalHours;
	}

	public String getThirUserRatio() {
		return thirUserRatio;
	}

	public void setThirUserRatio(String thirUserRatio) {
		this.thirUserRatio = thirUserRatio;
	}

	public String getThirFailure() {
		return thirFailure;
	}

	public void setThirFailure(String thirFailure) {
		this.thirFailure = thirFailure;
	}

	public String getFourPlanHours() {
		return fourPlanHours;
	}

	public void setFourPlanHours(String fourPlanHours) {
		this.fourPlanHours = fourPlanHours;
	}

	public String getFourpracticalHours() {
		return fourpracticalHours;
	}

	public void setFourpracticalHours(String fourpracticalHours) {
		this.fourpracticalHours = fourpracticalHours;
	}

	public String getFourUserRatio() {
		return fourUserRatio;
	}

	public void setFourUserRatio(String fourUserRatio) {
		this.fourUserRatio = fourUserRatio;
	}

	public String getFourFailure() {
		return fourFailure;
	}

	public void setFourFailure(String fourFailure) {
		this.fourFailure = fourFailure;
	}

    public String getShebeiglyId() {
        return shebeiglyId;
    }

    public void setShebeiglyId(String shebeiglyId) {
        this.shebeiglyId = shebeiglyId;
    }
}