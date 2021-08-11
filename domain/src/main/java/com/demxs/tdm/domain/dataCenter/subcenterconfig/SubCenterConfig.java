package com.demxs.tdm.domain.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.dataCenter.DashboardConfig;

import java.util.List;
import java.util.Map;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:07
 * @Description:
 */
public class SubCenterConfig extends DataEntity<SubCenterConfig> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验室列表
     */
    private List<Lab> labList;
    /**
     * 其他配置
     */
    private Map<String,DashboardConfig> dashboardConfig;

    public SubCenterConfig() {
    }

    public SubCenterConfig(String id) {
        super(id);
    }

    public List<Lab> getLabList() {
        return labList;
    }

    public SubCenterConfig setLabList(List<Lab> labList) {
        this.labList = labList;
        return this;
    }

    public Map<String, DashboardConfig> getDashboardConfig() {
        return dashboardConfig;
    }

    public SubCenterConfig setDashboardConfig(Map<String, DashboardConfig> dashboardConfig) {
        this.dashboardConfig = dashboardConfig;
        return this;
    }
}
