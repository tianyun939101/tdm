package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.dto.AbilityChart;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.LabInfoStateDao;
import com.demxs.tdm.domain.ability.AbilityLabPredict;
import com.demxs.tdm.domain.ability.LabInfoState;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/10/28 11:16
 **/
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabInfoStateService extends CrudService<LabInfoStateDao, LabInfoState>  {
    @Autowired
    private TestCategoryService testCategoryService;

    public LabInfoState getByEntity(LabInfoState state){
        return this.dao.getByEntity(state);
    }


    /**
     * @Describe:更新试验室整体描述
     * @Author:WuHui
     * @Date:19:09 2020/10/28
     * @param vId
     * @param labId
     * @return:void
    */
    public void updateLabinfoDescribe(String vId,String labId){
        LabInfoState state = new LabInfoState(vId,labId);
        state = this.dao.getByEntity(state);
        String describe = "";
        if(state == null){
            state = new LabInfoState(vId,labId);
            state.setStaffNum(0);
            state.setPosition("");
        }
        List<Map<String,String>> map = this.dao.countAbilityByLabId(labId);
        Map<String,Integer> data = new HashMap<>();
        for(Map<String,String> row :map){
            data.put(row.get("code"),Integer.valueOf(row.get("cnt")));
        }
        //获取试验室能力汇总
        AbilityChart ac = testCategoryService.getLabAbilitySum(labId);
        Integer total = ac.getA() + ac.getB() + ac.getC() + ac.getD();
        describe = "本试验室现有"+state.getStaffNum()+"人,功能定位"+state.getPosition()+"," +
                "试验室应具备的试验能力"+total+"项,目前已具备的试验能力"+ac.getD()+"项," +
                "初步具备试验能力"+ac.getC()+"项,试运行试验能力"+ac.getB()+"项,未形成的试验能力"+ac.getA()+"项";
        state.setDescribe(describe);
        this.save(state);
    }

    LabInfoState findLabInfoStateByLabId(String labId){
        return this.dao.findLabInfoStateByLabId(labId);
    }
}
