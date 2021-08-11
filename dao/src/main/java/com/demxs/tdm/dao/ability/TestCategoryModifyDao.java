package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategory;
import com.demxs.tdm.domain.ability.TestCategoryModify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/29 10:50
 * @Description:
 */
@MyBatisDao
public interface TestCategoryModifyDao extends CrudDao<TestCategoryModify> {

    /**
    * @author Jason
    * @date 2020/7/29 12:27
    * @params []
    * 查找所有新增操作
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findNewRecord(TestCategory category);

    /**
    * @author Jason
    * @date 2020/7/29 14:38
    * @params [category]
    * 查询条目数，辅助分页
    * @return int
    */
    int findNewRecordCount(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/5 17:28
    * @params [category]
    * 多条件查询，返回试验能力对象，用作动态加载父级节点树
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategory> findByRvlId(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/27 15:34
    * @params [category]
    * 联合查询，将修改申请中的新增项和版本原有记录合并
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findModifyUnionTestCategory(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/12 10:07
    * @params [modify]
    * 根据版本id、试验能力id、修改申请id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategoryModify> findByVcrId(TestCategoryModify modify);
    /**
    * @author Jason
    * @date 2020/8/6 14:17
    * @params [category]
    * 根据修改申请id查询，返回TestCategoryModify修改对象
    * 升序（当存在新增类型父级引用时，必须升序排序，业务处理时先处理最先声明的父级）
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategoryModify> findByRequestIdReturnOriginal(String rId);

    /**
    * @author Jason
    * @date 2020/9/8 16:08
    * @params [rId]
    * 根据修改申请id去重修改项查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategoryModify> findByRequestIdDistinct(String rId);

    /**
    * @author Jason
    * @date 2020/8/6 16:40
    * @params [modify]
    * 根据修改申请id和试验能力id删除
    * @return int
    */
    int deleteByRequestAndCategoryId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/6 17:26
    * @params [modify]
    * 根据父级id删除
    * @return int
    */
    int deleteByParentId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/12 9:14
    * @params [modify]
    * 更改状态方法
    * @return int
    */
    int changeStatus(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/6 17:29
    * @params [category]
    * 根据父级id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategoryModify> findByParentId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/9/9 11:08
    * @params [modify]
    * 根据父级id模糊查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategoryModify> findByParentIdLike(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/7 10:46
    * @params [modify]
    * 根据编号和修改申请id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategoryModify> findByCodeAndRequestId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/19 17:40
    * @params [setvId]
    * 根据版本查询所有修改信息
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategoryModify> findByVersionId(TestCategoryModify setvId);

    /**
    * @author Jason
    * @date 2020/8/20 19:03
    * @params [modify]
    * 根据版本id和试验能力id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategoryModify> findByVersionIdAndCategoryId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/21 16:53
    * @params []
    * 删除试验能力时，禁用该试验能力的其他修改项
    * @return int
    */
    int disabledModifyByCvrId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/21 16:54
    * @params []
    * 撤销删除试验能力时，恢复该试验能力的其他修改项
    * @return int
    */
    int rollbackRemoveByCvrId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/8/21 16:56
    * @params [entity]
    * 物理删除
    * @return int
    */
    @Override
    int delete(TestCategoryModify entity);

    /**
    * @author Jason
    * @date 2020/8/26 9:21
    * @params [entity]
    * 修改参数不为空的值
    * @return int
    */
    int updateActive(TestCategoryModify entity);

    /**
    * @author Jason
    * @date 2020/8/21 17:49
    * @params [getcId]
    * 返回子级数量
    * @return int
    */
    int findChildrenCount(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/9/1 16:08
    * @params [rId]
    * 根据修改申请id删除
    * @return int
    */
    int deleteByRequestId(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/9/2 15:22
    * @params [modify]
    * 根据关注试验能力，查询该能力及子级的全部修改申请
    * 忽略驳回项
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryModify>
    */
    List<TestCategoryModify> findObserver(TestCategoryModify modify);

    /**
    * @author Jason
    * @date 2020/9/9 10:32
    * @params [createBy, labId]
    * 人员变迁试验室，需要将此人创建的修改项所属试验室修改
    * @return int
    */
    int updateLabIdByCreateBy(@Param("createBy") String createBy, @Param("labId") String labId);
}
