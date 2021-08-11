package com.demxs.tdm.domain.business.vo;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.ModelPlan;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ModelPlanVO extends DataEntity<ModelPlanVO> {
    private List<ModelPlan> reName;

    public List<ModelPlan> getReName() {
        return reName;
    }

    public void setReName(List<ModelPlan> reName) {
        this.reName = reName;
    }
}
