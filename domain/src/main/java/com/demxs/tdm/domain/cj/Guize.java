package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集规则Entity
 * @author wbq
 * @version 2017-08-20
 */
public class Guize extends DataEntity<Guize> {

	private static final long serialVersionUID = 1L;
	private String guizemc;		// 规则名称
	private String guizelx;		// 规则类型
	private String pipeiff;		// 匹配方法
	private String pipeiffmc;		// 匹配方法名称
	private String pipeisb;		// 匹配设备
	private String pipeisbmc;		// 匹配设备名称
	private String pipeimc;		// 匹配名称
	private String pipeilx;		// 匹配类型
	private String infoid;		// 信息表ID
	private String dataid;		// 数据表ID
	private String txtcontent;		// txt解析内容
	private Long startindex;		// 起始行
	private Long startcolumn;
	private String sheetname;
	private String filepath;
	private String txtfengef;
	private String autostart;
	private String autoend;
	private Long datapianyil;
	private String ipaddress;
	private String datasource;
	private String username;
	private String userpassword;
	private String accesstablename;
	private String jieshubs;
	private String shifouzgz;//是否主规则
	private String ziguizid;//子规则ID
	private String ziguizmc;//子规则名称

	private String isResult;//是否结果规则（用于查询）
	private String tableName;//表名

	public Guize() {
		super();
	}

	public Guize(String id){
		super(id);
	}

	@Length(min=1, max=100, message="规则名称长度必须介于 1 和 100 之间")
	public String getGuizemc() {
		return guizemc;
	}

	public void setGuizemc(String guizemc) {
		this.guizemc = guizemc;
	}

	@Length(min=0, max=64, message="规则类型长度必须介于 0 和 64 之间")
	public String getGuizelx() {
		return guizelx;
	}

	public void setGuizelx(String guizelx) {
		this.guizelx = guizelx;
	}

	@Length(min=0, max=100, message="匹配名称长度必须介于 0 和 100 之间")
	public String getPipeimc() {
		return pipeimc;
	}

	public void setPipeimc(String pipeimc) {
		this.pipeimc = pipeimc;
	}

	@Length(min=0, max=100, message="匹配类型长度必须介于 0 和 100 之间")
	public String getPipeilx() {
		return pipeilx;
	}

	public void setPipeilx(String pipeilx) {
		this.pipeilx = pipeilx;
	}

	@Length(min=0, max=64, message="信息表ID长度必须介于 0 和 64 之间")
	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}

	@Length(min=0, max=64, message="数据表ID长度必须介于 0 和 64 之间")
	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getTxtcontent() {
		return txtcontent;
	}

	public void setTxtcontent(String txtcontent) {
		this.txtcontent = txtcontent;
	}

	public Long getStartindex() {
		return startindex;
	}

	public void setStartindex(Long startindex) {
		this.startindex = startindex;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Long getStartcolumn() {
		return startcolumn;
	}

	public void setStartcolumn(Long startcolumn) {
		this.startcolumn = startcolumn;
	}

	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public String getTxtfengef() {
		return txtfengef;
	}

	public void setTxtfengef(String txtfengef) {
		this.txtfengef = txtfengef;
	}

	public String getAutostart() {
		return autostart;
	}

	public void setAutostart(String autostart) {
		this.autostart = autostart;
	}

	public String getAutoend() {
		return autoend;
	}

	public void setAutoend(String autoend) {
		this.autoend = autoend;
	}

	public Long getDatapianyil() {
		return datapianyil;
	}

	public void setDatapianyil(Long datapianyil) {
		this.datapianyil = datapianyil;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}


	public String getAccesstablename() {
		return accesstablename;
	}

	public void setAccesstablename(String accesstablename) {
		this.accesstablename = accesstablename;
	}

	public String getJieshubs() {
		return jieshubs;
	}

	public void setJieshubs(String jieshubs) {
		this.jieshubs = jieshubs;
	}

	public String getPipeisb() {
		return pipeisb;
	}

	public void setPipeisb(String pipeisb) {
		this.pipeisb = pipeisb;
	}

	public String getPipeiff() {
		return pipeiff;
	}

	public void setPipeiff(String pipeiff) {
		this.pipeiff = pipeiff;
	}

	public String getPipeiffmc() {
		return pipeiffmc;
	}

	public void setPipeiffmc(String pipeiffmc) {
		this.pipeiffmc = pipeiffmc;
	}

	public String getPipeisbmc() {
		return pipeisbmc;
	}

	public void setPipeisbmc(String pipeisbmc) {
		this.pipeisbmc = pipeisbmc;
	}

	public String getShifouzgz() {
		return shifouzgz;
	}

	public void setShifouzgz(String shifouzgz) {
		this.shifouzgz = shifouzgz;
	}

	public String getZiguizid() {
		return ziguizid;
	}

	public void setZiguizid(String ziguizid) {
		this.ziguizid = ziguizid;
	}

	public String getZiguizmc() {
		return ziguizmc;
	}

	public void setZiguizmc(String ziguizmc) {
		this.ziguizmc = ziguizmc;
	}

	public String getIsResult() {
		return isResult;
	}

	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}