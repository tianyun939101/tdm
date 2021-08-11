package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryModifyRecord;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/4 16:03
 * @Description: 修改记录
 */
@MyBatisDao
public interface  TestCategoryModifyRecordDao extends CrudDao<TestCategoryModifyRecord> {

    /**
    * @author Jason
    * @date 2020/8/6 13:09
    * @params [record]
    * 根据修改申请id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModifyRecord>
    */
    List<TestCategoryModifyRecord> findByRequestId(String rId);

    /**
    * @author Jason
    * @date 2020/8/6 13:32
    * @params [cId]
    * 根据试验能力查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModifyRecord>
    */
    List<TestCategoryModifyRecord> findByCategoryId(String cId);


    /**
    * @author Jason
    * @date 2020/8/12 9:26
    * @params [record]
    * 根据修改试验申请id修改
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModifyRecord>
    */
    TestCategoryModifyRecord getByModifyId(TestCategoryModifyRecord record);

    /**
    * @author Jason
    * @date 2020/8/6 13:32
    * @params [record]
    * 根据修改申请id删除
    * @return int
    */
    int deleteByRequestId(TestCategoryModifyRecord record);

    /**
    * @author Jason
    * @date 2020/8/12 9:16
    * @params [record]
    * 改变状态方法
    * @return int
    */
    int changeStatus(TestCategoryModifyRecord record);

    /**
    * @author Jason
    * @date 2020/8/6 13:32
    * @params [record]
    * 根据试验能力id删除
    * @return int
    */
    int deleteByCategoryId(TestCategoryModifyRecord record);

    /**
     * 根据修改信息id删除
     * @param record
     * @return
     */
    int deleteByModifyId(TestCategoryModifyRecord record);

    /**
    * @author Jason
    * @date 2020/8/6 16:41
    * @params [record]
    * 根据修改申请id和试验能力id删除
    * @return int
    */
    int deleteByRequestAndCategoryId(TestCategoryModifyRecord record);
}
