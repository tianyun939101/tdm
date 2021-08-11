package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.dataCenter.pr.Directory;

/**
 * @author: Jason
 * @Date: 2020/5/15 11:25
 * @Description: 试飞FTP数据绑定数据中心tree中的试验的实体类
 */
public class FlightBindTestInfoData extends DataEntity<FlightBindTestInfoData> {

    private static final long serialVersionUID = 1L;

    private String dataTestInfoId;

    private String flightDirId;
    private Directory directory;
    /**
     * 文件类型 0：试飞中心，1、2、3：阎良，4：东营，5：上海
     */
    private String type;

    public String getDataTestInfoId() {
        return dataTestInfoId;
    }

    public FlightBindTestInfoData setDataTestInfoId(String dataTestInfoId) {
        this.dataTestInfoId = dataTestInfoId;
        return this;
    }

    public String getFlightDirId() {
        return flightDirId;
    }

    public FlightBindTestInfoData setFlightDirId(String flightDirId) {
        this.flightDirId = flightDirId;
        return this;
    }

    public Directory getDirectory() {
        return directory;
    }

    public FlightBindTestInfoData setDirectory(Directory directory) {
        this.directory = directory;
        return this;
    }

    public String getType() {
        return type;
    }

    public FlightBindTestInfoData setType(String type) {
        this.type = type;
        return this;
    }
}
