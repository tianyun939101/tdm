package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.AbilityLabPredict;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/12/17 19:44
 **/
@MyBatisDao
public interface AbilityLabPredictDao extends CrudDao<AbilityLabPredict> {

    /**
     * @Describe:批量新增
     * @Author:WuHui
     * @Date:19:53 2020/12/17
     * @param predicts
     * @return:java.lang.Integer
    */
    Integer batchSave(@Param("predicts") List<AbilityLabPredict> predicts);

    /**
     * @Describe:清空表数据
     * @Author:WuHui
     * @Date:14:04 2020/12/19
     * @param
     * @return:java.lang.Integer
    */
    Integer truncatePredict();

    /**
     * @Describe:试验室能力预测
     * @Author:WuHui
     * @Date:15:31 2020/12/19
     * @param labId
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> countLabAbilityPredict(@Param("labId") String labId,@Param("year") String year);

    /**
     * @Describe:中心能力预测
     * @Author:WuHui
     * @Date:15:32 2020/12/19
     * @param center
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> countCenterAbilityPredict(@Param("center") String center,@Param("year") String year);
    /**
     * @Describe:公司试验能力预测
     * @Author:WuHui
     * @Date:15:32 2020/12/19
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> countCompanyAbilityPredict(@Param("year") String year);

    /**
     * @Describe:获取能力预测年份
     * @Author:WuHui
     * @Date:15:36 2020/12/19
     * @param
     * @return:java.util.List<java.lang.String>
    */
    List<String> findAbilityPredictYear();
}
