package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.TreeEntity;

/**
 * 技术图谱
 */
public class Technology extends TreeEntity<Technology> {

    private String chargeUser;  //技术责任人
    private String chargeCode;  //技术责任人编码
    private String code;        //技术编码

    private String standardName;

    private String practicesCode;

    private String teName;

    private String  status;


    /**
     * 层级结构
     * @return
     */
    private String firstChargeUser;
    private String firstChargeCode;
    private String firstCode;
    private String firstName;

    private String seccendChargeUser;
    private String seccendChargeCode;
    private String seccendCode;
    private String seccendName;

    private String thirdChargeUser;
    private String thirdChargeCode;
    private String thirdCode;
    private String thirdName;



    private String forthChargeUser;
    private String forthChargeCode;
    private String forthCode;
    private String forthName;

    private String fifthChargeUser;
    private String fifthChargeCode;
    private String fifthCode;
    private String fifthName;

    private String sixthChargeUser;
    private String sixthChargeCode;
    private String sixthCode;
    private String sixthName;

    private String senthChargeUser;
    private String senthChargeCode;
    private String senthCode;
    private String senthName;


    /**
     *portal 门户/表格进入
     * @return
     */
    private String portal;

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeName() {
        return teName;
    }

    public void setTeName(String teName) {
        this.teName = teName;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getPracticesCode() {
        return practicesCode;
    }

    public void setPracticesCode(String practicesCode) {
        this.practicesCode = practicesCode;
    }
    public Technology() {
    }

    public Technology(String id) {
        super(id);
    }

    public Technology(String code,String name,String chargeUser,String chargeCode) {
        this.code = code;
        this.name = name;
        this.chargeUser = chargeUser;
        this.chargeCode = chargeCode;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Technology getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Technology parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Technology){
            if(((Technology) obj).getCode().equals(this.code) && ((Technology) obj).getName().equals(this.name)){
                return true;
            }
        }
        return false;
    }


    public String getFirstChargeUser() {
        return firstChargeUser;
    }

    public void setFirstChargeUser(String firstChargeUser) {
        this.firstChargeUser = firstChargeUser;
    }

    public String getFirstChargeCode() {
        return firstChargeCode;
    }

    public void setFirstChargeCode(String firstChargeCode) {
        this.firstChargeCode = firstChargeCode;
    }

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSeccendChargeUser() {
        return seccendChargeUser;
    }

    public void setSeccendChargeUser(String seccendChargeUser) {
        this.seccendChargeUser = seccendChargeUser;
    }

    public String getSeccendChargeCode() {
        return seccendChargeCode;
    }

    public void setSeccendChargeCode(String seccendChargeCode) {
        this.seccendChargeCode = seccendChargeCode;
    }

    public String getSeccendCode() {
        return seccendCode;
    }

    public void setSeccendCode(String seccendCode) {
        this.seccendCode = seccendCode;
    }

    public String getSeccendName() {
        return seccendName;
    }

    public void setSeccendName(String seccendName) {
        this.seccendName = seccendName;
    }

    public String getThirdChargeUser() {
        return thirdChargeUser;
    }

    public void setThirdChargeUser(String thirdChargeUser) {
        this.thirdChargeUser = thirdChargeUser;
    }

    public String getThirdChargeCode() {
        return thirdChargeCode;
    }

    public void setThirdChargeCode(String thirdChargeCode) {
        this.thirdChargeCode = thirdChargeCode;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getForthChargeUser() {
        return forthChargeUser;
    }

    public void setForthChargeUser(String forthChargeUser) {
        this.forthChargeUser = forthChargeUser;
    }

    public String getForthChargeCode() {
        return forthChargeCode;
    }

    public void setForthChargeCode(String forthChargeCode) {
        this.forthChargeCode = forthChargeCode;
    }

    public String getForthCode() {
        return forthCode;
    }

    public void setForthCode(String forthCode) {
        this.forthCode = forthCode;
    }

    public String getForthName() {
        return forthName;
    }

    public void setForthName(String forthName) {
        this.forthName = forthName;
    }

    public String getFifthChargeUser() {
        return fifthChargeUser;
    }

    public void setFifthChargeUser(String fifthChargeUser) {
        this.fifthChargeUser = fifthChargeUser;
    }

    public String getFifthChargeCode() {
        return fifthChargeCode;
    }

    public void setFifthChargeCode(String fifthChargeCode) {
        this.fifthChargeCode = fifthChargeCode;
    }

    public String getFifthCode() {
        return fifthCode;
    }

    public void setFifthCode(String fifthCode) {
        this.fifthCode = fifthCode;
    }

    public String getFifthName() {
        return fifthName;
    }

    public void setFifthName(String fifthName) {
        this.fifthName = fifthName;
    }

    public String getSixthChargeUser() {
        return sixthChargeUser;
    }

    public void setSixthChargeUser(String sixthChargeUser) {
        this.sixthChargeUser = sixthChargeUser;
    }

    public String getSixthChargeCode() {
        return sixthChargeCode;
    }

    public void setSixthChargeCode(String sixthChargeCode) {
        this.sixthChargeCode = sixthChargeCode;
    }

    public String getSixthCode() {
        return sixthCode;
    }

    public void setSixthCode(String sixthCode) {
        this.sixthCode = sixthCode;
    }

    public String getSixthName() {
        return sixthName;
    }

    public void setSixthName(String sixthName) {
        this.sixthName = sixthName;
    }

    public String getSenthChargeUser() {
        return senthChargeUser;
    }

    public void setSenthChargeUser(String senthChargeUser) {
        this.senthChargeUser = senthChargeUser;
    }

    public String getSenthChargeCode() {
        return senthChargeCode;
    }

    public void setSenthChargeCode(String senthChargeCode) {
        this.senthChargeCode = senthChargeCode;
    }

    public String getSenthCode() {
        return senthCode;
    }

    public void setSenthCode(String senthCode) {
        this.senthCode = senthCode;
    }

    public String getSenthName() {
        return senthName;
    }

    public void setSenthName(String senthName) {
        this.senthName = senthName;
    }
}
