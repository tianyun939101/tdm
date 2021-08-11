package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;

public class DzDevelopmentPlanning   extends DataEntity<DzDevelopmentPlanning> {
    
    private String id;

    private String abilityId;

    private String sourceDemand;

    private String demandIndex;
    
    private String currentState;
    
    private String competencyGap;
    
    private String builtNode;
    
    private String planningId;
    
    private String extendId;

    private String presentationId;

    private String agreementId;
    
    private String deviateId;

    private String applicationId;

    private String currentId;
    
    private String state;
    
    private static final long serialVersionUID = 1L;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId == null ? null : abilityId.trim();
    }
    
    public String getSourceDemand() {
        return sourceDemand;
    }

    public void setSourceDemand(String sourceDemand) {
        this.sourceDemand = sourceDemand == null ? null : sourceDemand.trim();
    }

    public String getDemandIndex() {
        return demandIndex;
    }
    
    public void setDemandIndex(String demandIndex) {
        this.demandIndex = demandIndex == null ? null : demandIndex.trim();
    }
    
    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState == null ? null : currentState.trim();
    }
    
    public String getCompetencyGap() {
        return competencyGap;
    }

    public void setCompetencyGap(String competencyGap) {
        this.competencyGap = competencyGap == null ? null : competencyGap.trim();
    }
    
    public String getBuiltNode() {
        return builtNode;
    }
    
    public void setBuiltNode(String builtNode) {
        this.builtNode = builtNode == null ? null : builtNode.trim();
    }
    
    public String getPlanningId() {
        return planningId;
    }
    
    public void setPlanningId(String planningId) {
        this.planningId = planningId == null ? null : planningId.trim();
    }

    public String getExtendId() {
        return extendId;
    }

    public void setExtendId(String extendId) {
        this.extendId = extendId == null ? null : extendId.trim();
    }
    
    public String getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(String presentationId) {
        this.presentationId = presentationId == null ? null : presentationId.trim();
    }
    
    public String getAgreementId() {
        return agreementId;
    }
    
    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId == null ? null : agreementId.trim();
    }

    public String getDeviateId() {
        return deviateId;
    }
    
    public void setDeviateId(String deviateId) {
        this.deviateId = deviateId == null ? null : deviateId.trim();
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }
    
    public String getCurrentId() {
        return currentId;
    }
    
    public void setCurrentId(String currentId) {
        this.currentId = currentId == null ? null : currentId.trim();
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}