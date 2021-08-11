package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryModifyRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/4 16:02
 * @Description:
 */
@MyBatisDao
public interface TestCategoryModifyRequestDao extends CrudDao<TestCategoryModifyRequest> {

    /**
    * @author Jason
    * @date 2020/8/4 16:53
    * @params [request]
    * 修改状态
    * @return int
    */
    int changeStatus(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/9/5 10:08
    * @params [request]
    * 修改会签人员
    * @return int
    */
    int changeCoSingerUser(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/9/5 12:30
    * @params [request]
    * 修改已会签人员
    * @return int
    */
    int changeSingedUser(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/9/5 10:13
    * @params [request]
    * 修改非空参数字段
    * @return int
    */
    int updateActive(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/18 11:02
    * @params [request]
    * 修改审核人
    * @return int
    */
    int changeAuditUser(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/6 16:44
    * @params [request]
    * 根据版本id删除
    * @return int
    */
    int deleteByVersionId(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/19 11:37
    * @params [request]
    * 禁用
    * @return int
    */
    int disabled(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/19 11:37
    * @params [request]
    * 启用
    * @return int
    */
    int enabled(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/19 17:45
    * @params [request]
    * 根据版本id查询所有修改申请
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModifyRequest>
    */
    List<TestCategoryModifyRequest> findByVersionId(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/25 13:06
    * @params [request]
    * 根据创建者id查询，返回最后一次创建的申请
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModifyRequest>
    */
    TestCategoryModifyRequest getByCreatorId(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/8/20 19:43
    * @params [vId]
    * 应用生效，改变生效版本字段
    * @return int
    */
    int effect(TestCategoryModifyRequest request);

    /**
    * @author Jason
    * @date 2020/9/9 10:37
    * @params [userId, labId]
    * 人员变迁试验室，需要将此人创建的修改申请所属试验室修改
    * @return int
    */
    int updateLabIdByCreateBy(@Param("createBy") String createBy,@Param("labId") String labId);
}
