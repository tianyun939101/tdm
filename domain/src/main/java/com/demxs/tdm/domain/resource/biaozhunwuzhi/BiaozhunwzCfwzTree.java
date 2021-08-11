package com.demxs.tdm.domain.resource.biaozhunwuzhi;


import java.util.ArrayList;

/**
 * 标准物质存放位置树
 * Created by zhangdengcai on 2017/6/15 18:44.
 */
public class BiaozhunwzCfwzTree {
	private String nodeValue;
	private String fuzhuj;
	private String label;
	private ArrayList<BiaozhunwzCfwzTree> children = new ArrayList<BiaozhunwzCfwzTree>();
	public BiaozhunwzCfwzTree(String nodeValue, String fuzhuj, String label) {
		this.nodeValue = nodeValue;
		this.fuzhuj = fuzhuj;
		this.label = label;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getFuzhuj() {
		return fuzhuj;
	}

	public void setFuzhuj(String fuzhuj) {
		this.fuzhuj = fuzhuj;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<BiaozhunwzCfwzTree> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<BiaozhunwzCfwzTree> children) {
		this.children = children;
	}
}