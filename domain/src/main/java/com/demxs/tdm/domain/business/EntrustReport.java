package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.Date;

/**
 * 申请单报告信息
 * @author 吴列鹏
 * @version 2017-10-30
 */
public class EntrustReport extends DataEntity<EntrustReport> {
	private static final long serialVersionUID = 1L;
	private EntrustInfo entrustInfo;		// 申请单
	private LabInfo lab;		// 试验室
	private String templateId;		// 报告模版ID
	private String code;		// 报告编号
	private Integer status;		// 报告状态
	private String owner;		//报告审核人ID
	private String ownerName;	//报告审核人名称
	private User auditUser;		// 报告审核信息
	private Date auditDate;		//审核时间
	private User approvalUser;		// 报告批准信息
	private Date approvalDate;	//批准时间
	private String reportFile;	//报告文件
	private User drawUser;	//报告编制人
	private Date drawDate;	//报告编制时间
	private String pdfFile;


	private Date startCreateDate;
	private Date endCreateDate;
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public EntrustReport() {
		super();
	}

	public EntrustReport(String id){
		super(id);
	}

	public EntrustInfo getEntrustInfo() {
		return entrustInfo;
	}

	public void setEntrustInfo(EntrustInfo entrustInfo) {
		this.entrustInfo = entrustInfo;
	}

	public LabInfo getLab() {
		return lab;
	}

	public void setLab(LabInfo lab) {
		this.lab = lab;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public User getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(User approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Boolean getEditFlag(){
		if (StringUtils.isNotEmpty(this.owner) && this.owner.indexOf(UserUtils.getUser().getId()) >= 0){
			return true;
		}
		return false;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public User getDrawUser() {
		return drawUser;
	}

	public void setDrawUser(User drawUser) {
		this.drawUser = drawUser;
	}

	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}

	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}
}