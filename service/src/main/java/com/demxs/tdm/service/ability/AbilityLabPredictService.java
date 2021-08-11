package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.AbilityLabPredictDao;
import com.demxs.tdm.domain.ability.AbilityLabPredict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/12/17 19:50
 **/
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class AbilityLabPredictService extends CrudService<AbilityLabPredictDao, AbilityLabPredict> {

    /**
     * @Describe:批量新增
     * @Author:WuHui
     * @Date:9:41 2020/12/19
     * @param predicts
     * @return:java.lang.Integer
    */
    public Integer batchSave(List<AbilityLabPredict> predicts){
        return this.dao.batchSave(predicts);
    }

    /**
     * @Describe:清空表数据
     * @Author:WuHui
     * @Date:14:06 2020/12/19
     * @param
     * @return:java.lang.Integer
    */
    public Integer truncatePredict(){
        return  this.dao.truncatePredict();
    }

    /**
     * @Describe:试验室能力预测
     * @Author:WuHui
     * @Date:15:40 2020/12/19
     * @param labId
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> labAbilityPredict(String labId,String year){
        return this.dao.countLabAbilityPredict(labId,year);
    }
    /**
     * @Describe:中心能力预测
     * @Author:WuHui
     * @Date:15:40 2020/12/19
     * @param center
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> centerAbilityPredict(String center,String year){
        return this.dao.countCenterAbilityPredict(center,year);
    }
    /**
     * @Describe:公司试验能力预测
     * @Author:WuHui
     * @Date:15:40 2020/12/19
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> companyAbilityPredict(String year){
        return this.dao.countCompanyAbilityPredict(year);
    }

    /**获取能力预测年份
     * @Describe:
     * @Author:WuHui
     * @Date:15:41 2020/12/19
     * @param
     * @return:java.util.List<java.lang.String>
    */
    List<String> getAbilityPredictYear(){
        return this.dao.findAbilityPredictYear();
    }
}
