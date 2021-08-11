package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.instrument.DzInstrumentsApparatuses;

/**
 * 构型仪器仪表
 * @author wuhui
 * @date 2021/2/2 19:08
 **/
public class ConfigurationMeter extends DataEntity<ConfigurationMeter> {

    private static final long serialVersionUID = 1L;
    private String cvId;		// 构型版本ID
    private String meterId;		// 设施ID
    private DzInstrumentsApparatuses instrument;//仪器仪表

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public DzInstrumentsApparatuses getInstrument() {
        return instrument;
    }

    public void setInstrument(DzInstrumentsApparatuses instrument) {
        this.instrument = instrument;
    }
}
