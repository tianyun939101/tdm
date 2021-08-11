package com.demxs.tdm.service.dataCenter.subcenterconfig;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.TestInfoDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.IPC;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo;
import com.demxs.tdm.domain.lab.LabVideoEquipment;
import com.demxs.tdm.service.lab.LabVideoEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:50
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestInfoService extends CrudService<TestInfoDao, TestInfo> {

    @Autowired
    private LabVideoEquipmentService videoEquipmentService;
    @Autowired
    private IPCService ipcService;

    public TestInfo getByTestTaskId(String testTaskId){
        return this.dao.getByTestTaskId(testTaskId);
    }

    public TestInfo getByLabId(TestInfo testInfo){
        return this.dao.getByLabId(testInfo);
    }


    /**
     * @Describe:获取试验信息及配置
     * @Author:WuHui
     * @Date:15:08 2021/1/25
     * @param id
     * @return:com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo
    */
    public TestInfo getTestInfoId(String id){
        //获取试验信息
        TestInfo testInfo = this.dao.get(id);
        //查询远程服务器信息
        IPC ipc = new IPC();
        ipc.setConfigId(id);
        testInfo.setIpcs(ipcService.findList(ipc));
        //查询视频监控信息
        LabVideoEquipment video = new LabVideoEquipment();
        video.setConfigId(id);
        testInfo.setVideos(videoEquipmentService.findList(video));
        return testInfo;
    }

    /**
     * @Describe:获取所有大屏显示异地现场配置
     * @Author:WuHui
     * @Date:13:39 2021/1/26
     * @param
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo>
    */
    public List<TestInfo> getShowTestInfo() {
        TestInfo testInfo = new TestInfo();
        testInfo.setShow("1");
        List<TestInfo> tests = this.dao.findList(testInfo);
        for(TestInfo test:tests){
            //查询远程服务器信息
            IPC ipc = new IPC();
            ipc.setConfigId(test.getId());
            test.setIpcs(ipcService.findList(ipc));
            //查询视频监控信息
            LabVideoEquipment video = new LabVideoEquipment();
            video.setConfigId(test.getId());
            test.setVideos(videoEquipmentService.findList(video));
        }
        return tests;
    }
}
