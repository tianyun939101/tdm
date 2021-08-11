/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 构型试验件信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationTestArticle extends DataEntity<ConfigurationTestArticle> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String articleId;		// 试验件ID
	private String amount;		// 试验件数量
	//试验件
	private TestPiece testPiece;
	
	public ConfigurationTestArticle() {
		super();
	}

	public ConfigurationTestArticle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=64, message="试验件ID长度必须介于 0 和 64 之间")
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	@Length(min=0, max=11, message="试验件数量长度必须介于 0 和 11 之间")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public TestPiece getTestPiece() {
		return testPiece;
	}

	public void setTestPiece(TestPiece testPiece) {
		this.testPiece = testPiece;
	}

}