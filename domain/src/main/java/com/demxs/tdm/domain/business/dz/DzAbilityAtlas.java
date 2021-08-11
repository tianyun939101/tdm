package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.TestCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class DzAbilityAtlas  extends DataEntity<DzAbilityAtlas> {
    
    private String id;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    private String ids;

    private String levelNumber;

    private String code;

    private String name;

    private String abilityGoal;

    private String testRange;

    private String category;

    private String inOut;

    private String commitment;

    private String abilityState;

    private String assessState;

    /**
     * 关联试验室id
     */
    private String labId;
    private String laboratory;

    private String typicalUndertaken;
    
    private String testTaskUndertaken;

    private String offer;
    
    private String contacts;
    
    private String qualifications;

    public String getEqqualification() {
        return eqqualification;
    }

    public void setEqqualification(String eqqualification) {
        this.eqqualification = eqqualification;
    }

    private String eqqualification;

    private String standard;
    
    private String createBy;
    
    private Date createDate;
    
    private String updateBy;

    private Date updateDate;

    private String state;

    /**
     * 关联试验类型对象
     */
    private TestCategory testCategory;
    
    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getLevelNumber() {
        return levelNumber;
    }
    
    public void setLevelNumber(String levelNumber) {
        this.levelNumber = levelNumber == null ? null : levelNumber.trim();
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public String getAbilityGoal() {
        return abilityGoal;
    }

    public void setAbilityGoal(String abilityGoal) {
        this.abilityGoal = abilityGoal == null ? null : abilityGoal.trim();
    }

    public String getTestRange() {
        return testRange;
    }
    
    public void setTestRange(String testRange) {
        this.testRange = testRange == null ? null : testRange.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getInOut() {
        return inOut;
    }
    
    public void setInOut(String inOut) {
        this.inOut = inOut == null ? null : inOut.trim();
    }
    
    public String getCommitment() {
        return commitment;
    }
    
    public void setCommitment(String commitment) {
        this.commitment = commitment == null ? null : commitment.trim();
    }

    public String getAbilityState() {
        return abilityState;
    }
    
    public void setAbilityState(String abilityState) {
        this.abilityState = abilityState == null ? null : abilityState.trim();
    }
    
    public String getAssessState() {
        return assessState;
    }

    public void setAssessState(String assessState) {
        this.assessState = assessState == null ? null : assessState.trim();
    }
    
    public String getLaboratory() {
        return laboratory;
    }
    
    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory == null ? null : laboratory.trim();
    }
    
    public String getTypicalUndertaken() {
        return typicalUndertaken;
    }
    
    public void setTypicalUndertaken(String typicalUndertaken) {
        this.typicalUndertaken = typicalUndertaken == null ? null : typicalUndertaken.trim();
    }

    public String getTestTaskUndertaken() {
        return testTaskUndertaken;
    }
    
    public void setTestTaskUndertaken(String testTaskUndertaken) {
        this.testTaskUndertaken = testTaskUndertaken == null ? null : testTaskUndertaken.trim();
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer == null ? null : offer.trim();
    }
    
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getQualifications() {
        return qualifications;
    }
    
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications == null ? null : qualifications.trim();
    }
    
    public String getStandard() {
        return standard;
    }
    
    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }


    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getLabId() {
        return labId;
    }

    public DzAbilityAtlas setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public TestCategory getTestCategory() {
        return testCategory;
    }

    public DzAbilityAtlas setTestCategory(TestCategory testCategory) {
        this.testCategory = testCategory;
        return this;
    }
}