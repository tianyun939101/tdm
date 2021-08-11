package com.demxs.tdm.service.portal;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.business.instrument.impl.ApparatusesServiceImpl;
import com.demxs.tdm.service.resource.shebei.ShebeiService;
import com.demxs.tdm.service.resource.yuangong.YuangongService;
import com.demxs.tdm.service.resource.zhishi.ZhishiXxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/8/31 9:07
 **/
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class PortalService {

    @Autowired
    private ShebeiService shebeiService;
    @Autowired
    private YuangongService yuangongService;
    @Autowired
    private ApparatusesServiceImpl apparatusesService;
    @Autowired
    private ZhishiXxService zhishiXxService;

    /**
     * @Describe:获取实验资源汇总数据
     * @Author:WuHui
     * @Date:9:14 2020/8/31
     * @param centerId
     * @return:int
    */
    public Map<String,Integer> getTestResourceCollect(String centerId,String departmentId) {
        Map<String,Integer> counts = new HashMap<>();
        //获取实验设备
        Integer shebei = shebeiService.countShebeiByCenter(centerId);
        counts.put("shebei",shebei);
        //获取实验人员
        List<Integer> integers = yuangongService.countYuangongByCenter(centerId);
        Integer yuangong = 0;
        for(Integer integer : integers){
            yuangong+=integer;
        }
        counts.put("yuangong",yuangong);
        //获取仪表仪器
        Integer instrument = apparatusesService.countInstrumentByDepartment(departmentId);
        counts.put("instrument",instrument);
        //获取知识流程
        Integer zhishi = zhishiXxService.countZhishiByCenter(centerId);
        counts.put("zhishi",zhishi);
        return counts;
    }
}
