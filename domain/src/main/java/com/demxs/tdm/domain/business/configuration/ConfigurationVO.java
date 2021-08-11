package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.instrument.DzInstrumentsApparatuses;

import java.util.List;

public class ConfigurationVO extends DataEntity<ConfigurationVO> {

    private static final long serialVersionUID = 1L;
    //基础构型id
    private String baseId;
    //版本id
    private String cvId;
    //基础构型
    private BaseConfiguration baseConfiguration;
    //版本
    private ConfigurationVersion version;
    //模版信息
    private ConfigurationReport report;
    //试验件信息
    private List<ConfigurationTestArticle> testPieceList;
    //软件构型信息
    private List<ConfigurationSoftArticle> softwareLibraryList;
    //设施构型信息
    private List<ConfigurationFacilityArticle> sheShiList;
    //设备构型信息
    private List<ConfigurationDeviceArticle> shebeiList;
    //电缆信息
    private List<ConfigurationCable> dianLanList;
    //传感器信息
    private List<ConfigurationSensor> chuanGanQiList;
    //仪器仪表
    private List<ConfigurationMeter> meterList;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public BaseConfiguration getBaseConfiguration() {
        return baseConfiguration;
    }

    public void setBaseConfiguration(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    public ConfigurationVersion getVersion() {
        return version;
    }

    public void setVersion(ConfigurationVersion version) {
        this.version = version;
    }

    public List<ConfigurationTestArticle> getTestPieceList() {
        return testPieceList;
    }

    public void setTestPieceList(List<ConfigurationTestArticle> testPieceList) {
        this.testPieceList = testPieceList;
    }

    public List<ConfigurationSoftArticle> getSoftwareLibraryList() {
        return softwareLibraryList;
    }

    public void setSoftwareLibraryList(List<ConfigurationSoftArticle> softwareLibraryList) {
        this.softwareLibraryList = softwareLibraryList;
    }

    public List<ConfigurationFacilityArticle> getSheShiList() {
        return sheShiList;
    }

    public void setSheShiList(List<ConfigurationFacilityArticle> sheShiList) {
        this.sheShiList = sheShiList;
    }

    public List<ConfigurationDeviceArticle> getShebeiList() {
        return shebeiList;
    }

    public void setShebeiList(List<ConfigurationDeviceArticle> shebeiList) {
        this.shebeiList = shebeiList;
    }

    public List<ConfigurationCable> getDianLanList() {
        return dianLanList;
    }

    public void setDianLanList(List<ConfigurationCable> dianLanList) {
        this.dianLanList = dianLanList;
    }

    public List<ConfigurationSensor> getChuanGanQiList() {
        return chuanGanQiList;
    }

    public void setChuanGanQiList(List<ConfigurationSensor> chuanGanQiList) {
        this.chuanGanQiList = chuanGanQiList;
    }

    public ConfigurationReport getReport() {
        return report;
    }

    public void setReport(ConfigurationReport report) {
        this.report = report;
    }

    public List<ConfigurationMeter> getMeterList() {
        return meterList;
    }

    public void setMeterList(List<ConfigurationMeter> meterList) {
        this.meterList = meterList;
    }
}
