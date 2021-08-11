package com.demxs.tdm.domain.resource.shebei;


import java.util.ArrayList;

/**
 * 设备类型和设备存放位置树
 * Created by zhangdengcai on 2017/6/15 18:44.
 */
public class ShebeiLxWzTree {
	private String nodeValue;
	private String fuzhuj;
	private String label;
	private ArrayList<ShebeiLxWzTree> children = new ArrayList<ShebeiLxWzTree>();
	public ShebeiLxWzTree(String nodeValue, String fuzhuj, String label) {
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

	public String getLabel() { return label; }

	public void setLabel(String leixingmc) {
		this.label = label;
	}

	public ArrayList<ShebeiLxWzTree> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ShebeiLxWzTree> children) {
		this.children = children;
	}
}