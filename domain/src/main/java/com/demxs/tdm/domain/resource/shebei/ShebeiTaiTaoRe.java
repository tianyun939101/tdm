package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.lab.LabInfo;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 设备台套关联设备信息
 */
public class ShebeiTaiTaoRe extends DataEntity<ShebeiTaiTaoRe> {

    private static final long serialVersionUID = 1L;
    private String id;
    private String taitaoId; //台套ID
    private String shebeiId;//设备ID

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTaitaoId() {
        return taitaoId;
    }

    public void setTaitaoId(String taitaoId) {
        this.taitaoId = taitaoId;
    }

    public String getShebeiId() {
        return shebeiId;
    }

    public void setShebeiId(String shebeiId) {
        this.shebeiId = shebeiId;
    }
}