package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.DataEntity;

public class TechnologyBwt extends DataEntity<TechnologyBwt> {

    private String  knowledgeId;
    private String technologyId;

    public String getKnowledgeBestId() {
        return knowledgeId;
    }

    public void setKnowledgeBestId(String knowledgeBestId) {
        this.knowledgeId = knowledgeBestId;
    }

    public String getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(String technologyId) {
        this.technologyId = technologyId;
    }
}
