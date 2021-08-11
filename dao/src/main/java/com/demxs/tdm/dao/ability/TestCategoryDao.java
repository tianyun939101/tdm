package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.dto.AbilityChart;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategory;
import com.demxs.tdm.domain.ability.TestCategoryChange;
import com.demxs.tdm.domain.dataCenter.ReportDataInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Jason
 * @Date: 2019/12/20 10:05
 * @Description:
 */

@MyBatisDao
public interface TestCategoryDao extends TreeDao<TestCategory> {

//    List<TestCategory> select(TestCategory testCategory);
    /**
    * @author Jason
    * @date 2020/6/4 17:19
    * @params []
    * 查找根节点
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findRootNode(TestCategory testCategory);

    /**
    * @author Jason
    * @date 2020/9/30 10:09
    * @params [testCategory]
    * 获得父级的直接子级（第一级）
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findDirectChildren(TestCategory testCategory);

    /**
    * @author Jason
    * @date 2020/6/4 17:19
    * @params [category]
    * 查找子节点ID
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<String> findChildren(TestCategory category);

    /**
    * @author Jason
    * @date 2020/7/29 9:13
    * @params [category]
    * 查找最低等级节点，和是否有更改或删除
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findLowestLevelNodeWithModify(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/10 15:12
    * @params [category]
    * 查找最低等级节点
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findLowestLevelNode(TestCategory category);

    List<TestCategory> findLowestLevelNodeWithAbility(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/31 15:19
    * @params [category]
    * 根据父级id模糊查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findByParentIdReturnObj(TestCategory category);

    /**
    * @author Jason
    * @date 2020/7/29 9:14
    * @params [category]
    * 修改有参数的值
    * @return int
    */
    int updateActive(TestCategory category);

    /**
    * @author Jason
    * @date 2020/7/29 14:39
    * @params [category]
    * 查询条目数，辅助分页
    * @return int
    */
    int findLowestLevelNodeWithModifyCount(TestCategory category);

    /**
    * @author Jason
    * @date 2020/7/30 12:39
    * @params [category]
    * 根据编号查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findByCode(TestCategory category);

    /**
     * @author Jason
     * @date 2020/8/3 10:23
     * @params [category]
     * 查询相同层级下的相同名称项
     * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
     */
    List<TestCategory> findListBySameLevelAndName(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/4 11:26
    * @params [category]
    * 根据版本号查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findByVersionId(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/4 14:05
    * @params [category]
    * 根据版本删除
    * @return int
    */
    int deleteByVersionId(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/17 17:57
    * @params [category]
    * 查找指定父级下的第一级子级的编码
    * @return java.util.List<java.lang.String>
    */
    List<String> findByParentId(TestCategory category);

    /**
    * @author Jason
    * @date 2020/9/2 10:32
    * @params [category]
    * 根据祖先id模糊查询
    * @return java.util.List<java.lang.String>
    */
    List<TestCategory> fuzzyQueryByParentId(TestCategory category);

    /**
    * @author Jason
    * @date 2020/8/21 17:49
    * @params [getcId]
    * 返回子级数量
    * @return int
    */
    List<TestCategory> findChildrenCount(String cId);

    /**
     * @Describe:统计分中心能力图谱类型
     * @Author:WuHui
     * @Date:14:31 2020/9/1
     * @param centerId
     * @return:java.util.List<java.util.Map>
    */
    List<Map<String,String>> countAssessStatusByCenter(@Param("centerId") String centerId);

    /**
     * @Describe:根据分中心查询能力图谱列表
     * @Author:WuHui
     * @Date:14:30 2020/9/1
     * @param centerId
     * @return:java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findListByCenter( @Param("page") Page<TestCategory> page
            ,@Param("centerId") String centerId
            ,@Param("status")  String status
            ,@Param("parentId") String parentId);

    /**
     * @Describe:
     * @Author:WuHui
     * @Date:12:42 2020/9/5
     * @param page
 * @param category
     * @return:java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<TestCategory> findLabAbility( @Param("page") Page<TestCategory> page
            ,@Param("category") TestCategory category,@Param("labId") String labId);

    void saveCategoryChange(@Param("modify") TestCategoryChange modify);

    void recordCategoryChange(@Param("modify") TestCategoryChange modify);

    /**
     * @Describe:查询中间版本能力图谱
     * @Author:WuHui
     * @Date:15:50 2020/9/8
     * @return:
     */
    List<TestCategory>  findCategoryView(@Param("category") TestCategory category);

    /**
    * @author Jason
    * @date 2020/9/23 18:43
    * @params [testCategory]
    * 试验能力评估列表
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    List<Map<String,Object>> findLowestLevelAssessment(TestCategory testCategory);

    /**
    * @author Jason
    * @date 2020/9/27 10:32
    * @params [testCategory]
    * 返回未填写数据的试验能力数目
    * @return int
    */
    int checkRequestStatus(TestCategory testCategory);

    /**
     * @Describe:能力建设计划检查
     * @Author:WuHui
     * @Date:13:35 2020/10/22
     * @param testCategory
     * @return:int
    */
    int checkAbilityProject(TestCategory testCategory);

    /**
     * @Describe:试验室能力数据
     * @Author:WuHui
     * @Date:14:06 2020/11/11
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> labAbilityList(TestCategory testCategory);

    /**
     * @Describe:获取试验室能力统计数据
     * @Author:WuHui
     * @Date:14:45 2020/11/11
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> findLabAbilityLevel(@Param("centerId") String centerId,@Param("labId") String labId);

    /**
     * @Describe:获取中心能力统计数据
     * @Author:WuHui
     * @Date:18:29 2020/11/11
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String, String>> findCenterAbilityLevel(@Param("centerId") String centerId);

    /**
     * @Describe:获取公司能力统计数据
     * @Author:WuHui
     * @Date:18:57 2020/11/11
     * @param
     * @return:java.util.Map<java.lang.String,java.lang.String>
    */
    List<Map<String,String>> findCompanyAbilityLevel();


    /**
     * @Describe:能力图谱数据
     * @Author:WuHui
     * @Date:13:46 2020/11/18
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, Object>> abilitys(TestCategory testCategory);

    /**
     * @Describe:能力图谱试验室数据
     * @Author:WuHui
     * @Date:13:46 2020/11/18
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String, Object>> abilitysAndlabs(TestCategory testCategory);

    /**
     * @Describe:能力图谱试验室数据
     * @Author:WuHui
     * @Date:13:46 2020/11/18
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, String>> labAbilityEquipment(TestCategory testCategory);


    List<AbilityChart> labAbilityProjectBuildPlan(@Param("labId") String labId);

    List<AbilityChart> centerAbilityProjectBuildPlan(@Param("centerId") String centerId);

    List<AbilityChart> companyAbilityProjectBuildPlan();


    List<Map<String,String>> labAbilityListWithEquipment(TestCategory testCategory);

    List<Map<String,String>>  getRootlabAbility(String levelName);

    List<String>  getCenterSum(String levelName);

    List<Map<String,String>>  getAblitySum(@Param("levelName") String levelName,@Param("labName") String labName,@Param("status") String status);

    List<Map<String,String>>   getDataSum(TestCategory testCategory);

    List<Map<String,String>> getCenterTestSum();

    List<Map<String,String>> getCenterTestName(@Param("centerName") String centerName);

    List<Map<String,String>> getCenterTestData(@Param("levelName") String levelName,@Param("centerName") String centerName,@Param("labName") String labName);

    String  getMaxAblity(@Param("levelName") String levelNam,@Param("centerName") String centerName);

    List<Map<String,String>> getTestCount();

    List<Map<String,String>> getLabLevelSum();


    List<ReportDataInfo> getTestMessInfo();
}
