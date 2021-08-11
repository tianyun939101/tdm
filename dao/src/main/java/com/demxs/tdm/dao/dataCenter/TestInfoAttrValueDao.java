package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.TestInfoAttrValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuhui
 * @date 2020/10/15 16:35
 **/
@MyBatisDao
public interface TestInfoAttrValueDao extends CrudDao<TestInfoAttrValue> {

    /**
     * @Describe:批量新增属性值
     * @Author:WuHui
     * @Date:16:49 2020/10/15
     * @param attrValues
     * @return:java.lang.Integer
    */
    Integer inserts(@Param("attrValues") List<TestInfoAttrValue> attrValues);

    /**
     * @Describe:根据测试任务编号删除属性值
     * @Author:WuHui
     * @Date:16:49 2020/10/15
     * @param testInfoId
     * @return:java.lang.Integer
    */
    Integer deleteByTestInfoId(@Param("testInfoId") String testInfoId);
}
