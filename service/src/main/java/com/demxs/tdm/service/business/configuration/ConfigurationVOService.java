package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.*;
import com.demxs.tdm.domain.business.configuration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ConfigurationVOService extends CrudService<ConfigurationVODao, ConfigurationVO> {

    @Autowired
    private ConfigurationReportService reportService;
    @Autowired
    private ConfigurationTestArticleService testArticleService;
    @Autowired
    private ConfigurationSoftArticleService softArticleService;
    @Autowired
    private ConfigurationFacilityArticleService facilityArticleService;
    @Autowired
    private ConfigurationDeviceArticleService deviceArticleService;
    @Autowired
    private ConfigurationCableService cableService;
    @Autowired
    private ConfigurationSensorService sensorService;
    @Autowired
    private BaseConfigurationService baseConfigurationService;
    @Autowired
    private ConfigurationVersionService versionService;
    @Autowired
    private ConfigurationMeterService meterService;

    @Transactional(readOnly = false)
    public void save(ConfigurationVO configurationVO){
        String cvId = configurationVO.getId();
        reportService.deleteByVersionId(cvId);
        if(null != configurationVO.getReport()){
            reportService.save(configurationVO.getReport());
        }
        if(null != configurationVO.getBaseConfiguration()){
            baseConfigurationService.updateBase(configurationVO.getBaseConfiguration());
        }
        if(null != configurationVO.getVersion()){
            versionService.updateActive(configurationVO.getVersion());
        }
        List<ConfigurationTestArticle> testPieceList = configurationVO.getTestPieceList();
        testArticleService.deleteByVersionId(cvId);
        for(int i=0;i<testPieceList.size();i++){
            ConfigurationTestArticle testArticle = testPieceList.get(i);
            testArticle.setId(null);
            testArticleService.save(testArticle);
        }
        List<ConfigurationSoftArticle> softwareLibraryList = configurationVO.getSoftwareLibraryList();
        softArticleService.deleteByVersionId(cvId);
        for(int i=0;i<softwareLibraryList.size();i++){
            ConfigurationSoftArticle softArticle = softwareLibraryList.get(i);
            softArticle.setId(null);
            softArticleService.save(softArticle);
        }
        List<ConfigurationFacilityArticle> sheShiList = configurationVO.getSheShiList();
        facilityArticleService.deleteByVersionId(cvId);
        for(int i=0;i<sheShiList.size();i++){
            ConfigurationFacilityArticle configurationFacilityArticle = sheShiList.get(i);
            configurationFacilityArticle.setId(null);
            facilityArticleService.save(configurationFacilityArticle);
        }
        List<ConfigurationCable> dianLanList = configurationVO.getDianLanList();
        cableService.deleteByVersionId(cvId);
        for(int i=0;i<dianLanList.size();i++){
            ConfigurationCable configurationCable = dianLanList.get(i);
            configurationCable.setId(null);
            cableService.save(configurationCable);
        }
        //设备
        List<ConfigurationDeviceArticle> shebeiList = configurationVO.getShebeiList();
        deviceArticleService.deleteByVersionId(cvId);
        for(int i=0;i<shebeiList.size();i++){
            ConfigurationDeviceArticle configurationDeviceArticle = shebeiList.get(i);
            configurationDeviceArticle.setId(null);
            deviceArticleService.save(configurationDeviceArticle);
        }
        //传感器
        List<ConfigurationSensor> chuanGanQiList = configurationVO.getChuanGanQiList();
        sensorService.deleteByVersionId(cvId);
        for(int i=0;i<chuanGanQiList.size();i++){
            ConfigurationSensor configurationSensor = chuanGanQiList.get(i);
            configurationSensor.setId(null);
            sensorService.save(configurationSensor);
        }
        //仪器仪表保存
        List<ConfigurationMeter>  meters = configurationVO.getMeterList();
        meterService.deleteByVersionId(cvId);
        for(int i=0;i<meters.size();i++){
            ConfigurationMeter meter = meters.get(i);
            meter.setId(null);
            meterService.save(meter);
        }
    }

    public ConfigurationVO getCurVersionDetail(String baseId){
        return this.dao.getCurVersionDetail(baseId);
    }

}
