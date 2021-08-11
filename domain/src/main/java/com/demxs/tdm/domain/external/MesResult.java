package com.demxs.tdm.domain.external;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * MES系统样品BOM信息实体
 * @author ranhl
 * @version 2017-11-30
 */
public class MesResult {
    private static final long serialVersionUID = 1L;
    /**
     * 物料编号
     */
    private String productName="";
    /**
     * 材料名称
     */
    private String productFamilyName="";
    /**
     * 厂家
     */
    private String ProductVendor="";
    /**
     * 产品描述
     */
    private String ProductDesc="";
    /**
     * 批次号
     */
    private String ProductLotSN="";
    /**
     * 序号
     */
    private Integer sort;

    public MesResult() {
    }

    public MesResult(String productFamilyName) {
        this.productFamilyName = productFamilyName;
    }

    public MesResult(String productFamilyName,Integer sort) {
        this.productFamilyName = productFamilyName;
        this.sort = sort;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductFamilyName() {
        return productFamilyName;
    }

    public void setProductFamilyName(String productFamilyName) {
        this.productFamilyName = productFamilyName;
    }

    public String getProductVendor() {
        return ProductVendor;
    }

    public void setProductVendor(String productVendor) {
        ProductVendor = productVendor;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getProductLotSN() {
        return ProductLotSN;
    }

    public void setProductLotSN(String productLotSN) {
        ProductLotSN = productLotSN;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("productName", productName)
                .append("productFamilyName", productFamilyName)
                .append("ProductVendor", ProductVendor)
                .append("ProductDesc", ProductDesc)
                .append("ProductLotSN", ProductLotSN)
                .toString();
    }
}
