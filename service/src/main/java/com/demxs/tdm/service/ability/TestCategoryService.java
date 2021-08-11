package com.demxs.tdm.service.ability;

import com.demxs.tdm.comac.common.constant.RemovalTypeEnum;
import com.demxs.tdm.common.dto.AbilityChart;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.AbilityRemovalDao;
import com.demxs.tdm.dao.ability.TestCategoryDao;
import com.demxs.tdm.dao.ability.TestCategoryLabDao;
import com.demxs.tdm.domain.ability.*;
import com.demxs.tdm.domain.ability.constant.ModifyType;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessEnum;
import com.demxs.tdm.domain.dataCenter.ReportDataInfo;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.Lab;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.LabUser;
import com.demxs.tdm.domain.lab.SubCenter;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.lab.LabUserService;
import com.demxs.tdm.service.lab.SubCenterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringValueResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Jason
 * @Date: 2019/12/20 10:08
 * @Description:
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestCategoryService extends TreeService<TestCategoryDao, TestCategory> {

    @Autowired
    private TestCategoryLabDao categoryLabDao;
    @Autowired
    private LabUserService labUserService;
    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private AbilityEvaluationCheckService abilityEvaluationCheckService;
    @Autowired
    private TestCategoryLabService testCategoryLabService;
    @Autowired
    private TestCategoryAssessmentService assessmentService;
    @Autowired
    private SubCenterService subCenterService;
    @Autowired
    private TestCategoryVersionService versionService;
    @Autowired
    private TestCategoryAttrService testCategoryAttrService;
    @Autowired
    private AbilityEvaluationCheckService checkService;
    @Autowired
    private EquipmentTestService equipmentTestService;
    @Autowired
    private StandardTestService standardTestService;
    @Autowired
    private AbilityRemovalService abilityRemovalService;
    @Autowired
    private AbilityLabPredictService predictService;


    public TestCategoryDao getDao(){
        return this.dao;
    }

    @Override
    public TestCategory get(String id) {
        TestCategory c = this.dao.get(id);
        if(c.getParent()!=null && StringUtils.isNotBlank(c.getParent().getId())){
            c.setParent(this.dao.get(c.getParentId()));
        }
        return c;
    }

    @Override
    public List<TestCategory> findList(TestCategory entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    public List<TestCategory> findAllList(TestCategory testCategory){
        return super.dao.findAllList(testCategory);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(TestCategory entity) {
        super.delete(entity);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(TestCategory entity) {
        super.save(entity);
    }

    public List<TestCategory> findRootNode(TestCategory testCategory){
        return this.dao.findRootNode(testCategory);
    }

    public List<TestCategory> findDirectChildren(TestCategory testCategory){
        return this.dao.findDirectChildren(testCategory);
    }

    public List<String> findChildren(TestCategory category){
        return this.dao.findChildren(category);
    }

    public List<TestCategory> findLowestLevelNodeWithModify(TestCategory category){
        return this.dao.findLowestLevelNodeWithModify(category);
    }

    public int updateActive(TestCategory category){
        return this.dao.updateActive(category);
    }

    public int findLowestLevelNodeWithModifyCount(TestCategory category){
        return this.dao.findLowestLevelNodeWithModifyCount(category);
    }

    public List<TestCategory> findByList(TestCategory category){
        return this.dao.findByCode(category);
    }

    public List<TestCategory> findByCode(TestCategory category){
        return this.dao.findByCode(category);
    }

    public List<TestCategory>   findLowestLevelNode(TestCategory category){
        List<String> filterLabIdList = category.getFilterLabIdList();
        List<TestCategory> list = this.dao.findLowestLevelNode(category);
        if(null != list){
            for (TestCategory testCategory : list) {
                TestCategoryLab categoryLab = new TestCategoryLab();
                categoryLab.setFilterLabIdList(filterLabIdList);
                categoryLab.setcId(testCategory.getId());
                categoryLab.setvId(testCategory.getvId());
                categoryLab.setAssessStatus(category.getAssessStatus());
                List<TestCategoryLab> labList = categoryLabDao.findListWithCategoryId(categoryLab);
                testCategory.setLabList(labList);
            }
        }
        return list;
    }

    public List<TestCategory> findListBySameLevelAndName(TestCategory category){
        return this.dao.findListBySameLevelAndName(category);
    }

    public List<TestCategory> findByVersionId(TestCategory category){
        return this.dao.findByVersionId(category);
    }

    public List<TestCategory> fuzzyQueryByParentId(TestCategory category){
        return this.dao.fuzzyQueryByParentId(category);
    }

    public int deleteByVersionId(TestCategory category){
        return this.dao.deleteByVersionId(category);
    }

    public String getNextCode(TestCategory category){
        if(null != category && null != category.getParent() && StringUtils.isNoneBlank(category.getParent().getId())){
            List<String> list = this.dao.findByParentId(category);
            if (list != null) {
                for (String s : list) {
                    if(StringUtils.isNotBlank(s)){
                        String suffix = s.substring(s.length() - 2);
                        if(StringUtils.isNumeric(suffix)){
                            int code = Integer.parseInt(suffix);
                            //判断编码是否溢出
                            if(code + 1 > 99){
                                s += "01";
                            }else{
                                s = s.substring(0,s.length() - 2);
                                s += String.format("%02d", ++code);
                            }
                            return s;
                        }
                    }
                }
            }
            return category.getCode() == null ? null :  category.getCode() + "01";
        }
        return null;
    }

    /**
     * 统计分中心能力图谱类型
     * @param centerId
     * @return
     */
    public Map<String,String> countAssessStatusByCenter(String centerId){
        Map<String,String> counts = new HashMap<>();
        List<Map<String,String>> list = dao.countAssessStatusByCenter(centerId);
        for(Map<String,String> item : list){
            counts.put(item.get("NAME"),item.get("VALUE"));
        }
        return counts;
    }

    /**
     * @Describe:根据分中心查询能力图片列表
     * @Author:WuHui
     * @Date:14:27 2020/9/1
     * @param page
     * @param centerId
     * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    public Page<TestCategory> findListByCenter(Page<TestCategory> page,String centerId,String status, String parentId){
        List<TestCategory> list = dao.findListByCenter(page,centerId,status, parentId);
        page.setList(list);
        return page;
    }

    /**
     * @Describe:查询实验室能力
     * @Author:WuHui
     * @Date:12:56 2020/9/5
     * @param page
     * @param category
     * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    public Map<String,Object>  findLabAbility(Page<TestCategory> page,TestCategory category){
        boolean merge = false;
        String labId = null;
        if( CollectionUtils.isEmpty(category.getFilterLabIdList())){
            User user = UserUtils.getUser();
            LabUser labUser = new LabUser();
            labUser.setUserId(user.getId());
            List<LabUser> labUsers = labUserService.findByUserId(labUser);
            if(!CollectionUtils.isEmpty(labUsers)) {
                List<String> labs = new ArrayList<>();
                labId = labUsers.get(0).getLabId();
                labs.add(labId);
                category.setFilterLabIdList(labs);
            }else{
                merge = true;
            }
        }

        List<TestCategory> list = this.dao.findLabAbility(page,category,labId);
        page.setList(list);
        Map<String,Object> data = new HashMap<>();
        data.put("page",page);
        data.put("merge",merge);
        return data;
    }


    /**
     * @Describe:
     * @Author:WuHui
     * @Date:14:48 2020/9/8
     * @param modify 修改记录
     * @param type 修改类型
     * @return:int
    */
    public void recordCategoryChange(TestCategoryChange modify,ModifyType type){
        modify.setType(type.getCode());
        if(ModifyType.INSERT.equals(type)){
            //无意义id
            modify.setcId(IdGen.uuid());
            modify.preInsert();
            dao.saveCategoryChange(modify);
        }else{
             dao.recordCategoryChange(modify);
        }
    }

    /**
     * @Describe:
     * @Author:WuHui
     * @Date:15:57 2020/9/8
     * @param testCategory
     * @return:java.util.List<com.demxs.tdm.domain.ability.TestCategory>
    */
    public List<TestCategory> findCategoryView(TestCategory testCategory){
        return super.dao.findCategoryView(testCategory);
    }

    public List<TestCategory> findLowestLevelNodeWithAbility(TestCategory testCategory) {
        return this.dao.findLowestLevelNodeWithAbility(testCategory);
    }

    public List<Map<String,Object>> findLowestLevelAssessment(TestCategory testCategory) {
        String id = UserUtils.getUser().getId();
        List<Map<String,Object>> list = this.dao.findLowestLevelAssessment(testCategory);
        TestCategoryAssessment assessment = new TestCategoryAssessment();
        for(Map<String,Object> category:list){
            if(category.get("labId") != null && category.get("id") != null){
                assessment.setLabId(category.get("labId").toString());
                assessment.setcId(category.get("id").toString());
                TestCategoryAssessment testCategoryAssessment = assessmentService.getByLabIdAndCId(assessment);
                category.put("assessment",testCategoryAssessment);
            }
            if(category.get("userIds") != null){
                String userIds = category.get("userIds").toString();
                if(!userIds.equals(id)){
                    category.put("subscribe","");
                }
            }
        }
        return list;
    }

    /**
    * @author Jason
    * @date 2020/9/30 9:47
    * @params [testCategory]
    * 重新整理编号
    * @return void
    */
    public void reCode(TestCategory testCategory, HttpServletRequest request, HttpServletResponse response) {
        List<TestCategory> rootNode = this.dao.findRootNode(testCategory);
        List<TestCategory> dataList = this.dao.findList(testCategory);
        List<String> list = new ArrayList<>(300);
        if(null != rootNode && !rootNode.isEmpty()){
            for (TestCategory root : rootNode) {
                resetChildrenCode(root,dataList,list);
            }
        }
        try {
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename="+new String(("能力编码修改记录.txt").getBytes("gb2312"), "ISO8859-1"));
            BufferedWriter writer = new BufferedWriter(response.getWriter());
            if(list.isEmpty()){
                writer.write("无错误项");
            }else{
                for (String s : list) {
                    writer.write(s);
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void resetChildrenCode(TestCategory parent,List<TestCategory> dataList,List<String> modifiedList){
        int codeVal = 0;
        for (TestCategory record : dataList) {
            if(null != record.getParent() && record.getParentId().equals(parent.getId())){
                //得到顺序递增的正确编号
                String correctCode = parent.getCode() + String.format("%02d", ++codeVal);
                if(!record.getCode().equals(correctCode)){
                    modifiedList.add("错误编号：" + record.getCode() + "\t正确编号：" + correctCode
                            + "\t能力名称：" + record.getName() + "\t父级：[" + parent.getCode() + "]" + parent.getName());
                    record.setCode(correctCode);
                    this.dao.updateActive(new TestCategory(record.getId()).setCode(correctCode));
                }
                //不是最后一级，则查找子级
                if(record.getParentIds().split(",").length <= record.getLowestLevel()){
                    resetChildrenCode(record,dataList,modifiedList);
                }
            }
        }
    }


    /**
     * @Describe:查询能力自查表
     * @Author:WuHui
     * @Date:11:18 2020/10/17
     * @param check
     * @return:com.demxs.tdm.domain.ability.AbilityEvaluationCheck
    */
    public AbilityEvaluationCheck getEvalCheck(AbilityEvaluationCheck check){
        AbilityEvaluationCheck evalCheck = abilityEvaluationCheckService.getEvalCheckByCVLId(check.getcId(),check.getvId(),check.getLabId());
        check = evalCheck == null ? check : evalCheck;
        LabInfo labInfo = labInfoService.get(check.getLabId());
        check.setLabInfo(labInfo);
        TestCategory testCategory = this.get(check.getcId());
        check.setTestCategory(testCategory);
        return check;
    }

    /**
     * @Describe:能力自评表保存
     * @Author:WuHui
     * @Date:11:23 2020/10/17
     * @param check
     * @return:com.demxs.tdm.domain.ability.AbilityEvaluationCheck
    */
    public AbilityEvaluationCheck saveEvaluationCheck(AbilityEvaluationCheck check){
        //能力自查表存储
        abilityEvaluationCheckService.save(check);
        //能力状态评估
        TestCategoryAssessEnum status = TestCategoryAssessEnum.A;
        if(check.getAccept() || check.getRipe()){//具备 实验室认证 或者案例直接判断为形成能力
            status = TestCategoryAssessEnum.D;
        }else if(check.getTestStandard() && check.getInstall()
                && check.getWorkStandard() && check.getEquipmentAccept()
                && check.getDocumentA() && check.getDocumentB() ){
            status =  check.getDocumentC() ? TestCategoryAssessEnum.D:TestCategoryAssessEnum.C;
        }else if(check.getTestStandard() && check.getInstall() && check.getWorkStandard()){
            status = TestCategoryAssessEnum.B;
        }
        //更新当前版本能力状态
        TestCategoryLab lab = new TestCategoryLab(check.getcId(),check.getvId(),check.getLabId());
        lab = testCategoryLabService.getByCvlId(lab);
        if(lab == null){
            lab = new TestCategoryLab(check.getcId(),check.getvId(),check.getLabId());
            lab.setCreateBy(UserUtils.getUser());
            lab.setCreateDate(new Date());
        }
        lab.setAssessStatus(status.getCode());
        lab.setUpdateBy(UserUtils.getUser());
        lab.setUpdateDate(new Date());
        testCategoryLabService.save(lab);
        //更新能力评估”能力自评状态“
        TestCategoryAssessment assessment = new TestCategoryAssessment(check.getcId(),check.getLabId());
        assessment = assessmentService.getByLabIdAndCId(assessment);
        if(assessment == null){
            assessment = new TestCategoryAssessment(check.getcId(),check.getLabId(),check.getvId());
            assessment.setApplyStatus(TestCategoryAssessment.NOT_APPLIED);
        }
        assessment.setStatus(status.getCode());
        assessment.setUpdateBy(UserUtils.getUser());
        assessmentService.save(assessment);
        return check;
    }

    /**
     * @Describe:试验室能力
     * @Author:WuHui
     * @Date:16:26 2020/12/19
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> labAbilityList(TestCategory testCategory) {
        List<Map<String,String>> list = this.dao.labAbilityList(testCategory);
        return list;
    }

    public List<Map<String,String>> labAbilityListWithEquipment(TestCategory testCategory) {
        List<Map<String,String>> list = this.dao.labAbilityListWithEquipment(testCategory);
        return list;
    }

    /**
     * @Describe:获取试验室能力统计数据
     * @Author:WuHui
     * @Date:14:49 2020/11/11
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> getLabAbilityLevel(TestCategory testCategory) {
        List<Map<String,String>> list = this.dao.findLabAbilityLevel(testCategory.getSubCenterId(),null);
        return list;
    }


    /**
     * @Describe:获取中心能力统计数据
     * @Author:WuHui
     * @Date:18:29 2020/11/11
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> getCenterAbilityLevel() {
        List<Map<String,String>> list = this.dao.findCenterAbilityLevel(null);
        return list;
    }

    /**
     * @Describe:获取公司能力统计数据
     * @Author:WuHui
     * @Date:16:25 2020/12/19
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> getCompanyAbilityLevel(){
        return this.dao.findCompanyAbilityLevel();
    }

    /**
     * @Describe:获取试验室能力图谱数据
     * @Author:WuHui
     * @Date:16:25 2020/12/19
     * @param testCategory
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public List<Map<String,String>> abilitysAndLabData(TestCategory testCategory) {
        List<String> labs = new ArrayList<>();
        //获取试验室
        if(StringUtils.isNotEmpty(testCategory.getLabId())){
            labs.add(testCategory.getLabId());
        }else if(StringUtils.isNotEmpty(testCategory.getSubCenterId())){
            labs.addAll(labInfoService.getLabIdByCenter(testCategory.getSubCenterId()));
        }
        if(!CollectionUtils.isEmpty(labs)){
            testCategory.setLabIds(labs);
        }
        List<Map<String,String>> eqs = this.dao.labAbilityEquipment(testCategory);
        return eqs;
    }

    /**
     * @Describe:构建试验室能力预测图表数据
     * @Author:WuHui
     * @Date:15:43 2020/12/19
     * @param labId
     * @return:java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String,Object> labAbilityPredict(String labId) {
        //获取试验室建设计划数据
        Map<String,Object> result = new HashMap<>();
        result.put("list",predictService.labAbilityPredict(labId,null));
        result.put("xAxis",predictService.getAbilityPredictYear());
        return result;

        //早期计算逻辑废弃
        /*List<AbilityChart> list = this.dao.labAbilityProjectBuildPlan(labId);
        List<Map<String,String>> orig= this.dao.findLabAbilityLevel(null,labId);
        return this.labAbilityPredit(list,orig);*/
    }

    /**
     * @Describe:构建中心能力预测图表数据
     * @Author:WuHui
     * @Date:15:43 2020/12/19
     * @param center
     * @return:java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String,Object> centerAbilityPredict(String center) {
        //获取试验室建设计划数据
        Map<String,Object> result = new HashMap<>();
        result.put("list",predictService.centerAbilityPredict(center,null));
        result.put("xAxis",predictService.getAbilityPredictYear());
        return result;
        //废弃
        /*List<AbilityChart> list = this.dao.centerAbilityProjectBuildPlan(center);
        List<Map<String,String>> orig= this.dao.findCenterAbilityLevel(center);
        return this.labAbilityPredit(list,orig);*/
    }
    /**
     * @Describe:构建公司能力预测图表数据
     * @Author:WuHui
     * @Date:15:43 2020/12/19
     * @param
     * @return:java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String,Object> companyAbilityPredict() {
        Map<String,Object> result = new HashMap<>();
        result.put("list",predictService.companyAbilityPredict(null));
        result.put("xAxis",predictService.getAbilityPredictYear());
        return result;
        //废弃
        /*List<AbilityChart> list = this.dao.companyAbilityProjectBuildPlan();
        List<Map<String,String>> orig= this.dao.findCompanyAbilityLevel();
        return this.labAbilityPredit(list,orig);*/
    }

    /*private  List<Map<String,String>> buildData(List<String> dates,
                                                Map<String,Integer> origMap,
                                                List<Map<String,String>> grows,Map<String,Integer> year){
        List<Map<String,String>> datas = new ArrayList<>();
        String[] levels = new String[]{"A","B","C","D"};
        Integer total = 0;
        boolean exists = false;

        for(String level:levels){
            for(String date:dates){
                exists = false;
                Map<String,String> data = new HashMap<>();
                data.put("date",date);
                data.put("level",level);
                //遍历获取增量
                if(!level.equals("A")){
                    for(Map<String,String> grow:grows){
                        if(grow.get("date").equals(date) && grow.get("level").equals(level)){
                            total = Integer.valueOf(grow.get("total")) + (origMap.get(level) == null ? 0 :origMap.get(level));
                            data.put("total",String.valueOf(total));
                            exists = true;
                            break;
                        }
                    }
                }
                if(!exists){
                    total = 0 + (origMap.get(level) == null ? 0 :origMap.get(level));
                    if(level.equals("A")){
                        total -= year.get(date) ;
                    }
                    data.put("total",String.valueOf(total));
                }
                datas.add(data);
            }
        }
        return datas;
    }

    private List<Map<String,String>> buildDateLevel(List<String> dates,List<Map<String,String>> list){
        List<Map<String,String>> result = new ArrayList<>();
        String[] levels = new String[]{"A","B","C","D"};
        boolean exists = false;
        for(String level:levels){
            for(String date:dates){
                exists = false;
                for(Map<String,String> data:list){
                    if(level.equals(data.get("level")) && date.equals(data.get("date")) ){
                        result.add(data);
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    Map<String,String> data = new HashMap<>();
                    data.put("date",date);
                    data.put("level",level);
                    data.put("total","0");
                    result.add(data);
                }
            }
        }
        return result;
    }

    private List<String> buildDateOfShaft(List<Map<String,String>> list){
        List<String> xAxis = new ArrayList<>();
        String date = null;
        for(Map<String,String> data:list){
            date = data.get("date");
            if(!xAxis.contains(date)){
                xAxis.add(date);
            }
        }
        return xAxis;
    }

    public Map<String,Object>  labAbilityPredit(List<AbilityChart> list, List<Map<String,String>> orig){
        //获取预测年份
        Map<String,Map<String,AbilityChart>> datas = new LinkedHashMap<>();
        for(AbilityChart ac:list){
            Map<String,AbilityChart> year = datas.get(ac.getDate());
            if(year == null){
                year = new HashMap<String,AbilityChart>();
                datas.put(ac.getDate(),year);
            }
            if(year.get(ac.getcName()) == null){
                year.put(ac.getcName(),ac);
            }
            switch (ac.getLevel()){
                case "B":
                    year.get(ac.getcName()).setB(1);
                    break;
                case "C":
                    year.get(ac.getcName()).setC(1);
                    break;
                case "D":
                    year.get(ac.getcName()).setD(1);
                    break;
                default:
                    year.get(ac.getcName()).setA(1);
                    break;

            }
        }
        //获取当年能力初始值
        int a = 0,b = 0,c = 0,d = 0;
        for(Map<String,String> data:orig){
            switch (data.get("level")){
                case "A":
                    a = Integer.valueOf(data.get("total"));
                    break;
                case "B":
                    b = Integer.valueOf(data.get("total"));
                    break;
                case "C":
                    c = Integer.valueOf(data.get("total"));
                    break;
                case "D":
                    d = Integer.valueOf(data.get("total"));
                    break;
            }
        }
        //能力计算并累计
        List<Map<String,String>> years = new ArrayList<>();
        int index = 0;
        for(String year: datas.keySet()){
            Map<String,AbilityChart> acs = datas.get(year);
            index++;
            for(AbilityChart ac:acs.values()) {
                ac.setA(ac.getA() - ac.getB());
                ac.setB(ac.getB() - ac.getC());
                ac.setC(ac.getC() - ac.getD());

                if(index==1){
                    if(ac.getStart().equals("A")){
                        ac.setA(-1);
                        if(ac.getB()<0) ac.setB(0);
                    }else if(ac.getStart().equals("B")){
                        ac.setB(-1);
                    }
                    if(ac.getC()<0 && !ac.getStart().equals("C")) ac.setC(0);
                    if(ac.getD()<0) ac.setD(0);
                }

                a += ac.getA();
                b += ac.getB();
                c += ac.getC();
                d += ac.getD();
            }
            if(c<0){
                a += c;
                c = 0;
                if( a< 0){
                    b+= a;
                    a = 0;
                }
            }
            if(b<0){
                a += b;
                b = 0;
            }

            if(a<0){
                d+= a;
                a = 0;
            }
            Map<String,String> dataA = new HashMap<>();
            dataA.put("date",year);
            dataA.put("level","A");
            dataA.put("total",String.valueOf(a));
            years.add(dataA);
            Map<String,String> dataB = new HashMap<>();
            dataB.put("date",year);
            dataB.put("level","B");
            dataB.put("total",String.valueOf(b));
            years.add(dataB);
            Map<String,String> dataC = new HashMap<>();
            dataC.put("date",year);
            dataC.put("level","C");
            dataC.put("total",String.valueOf(c));
            years.add(dataC);
            Map<String,String> dataD = new HashMap<>();
            dataD.put("date",year);
            dataD.put("level","D");
            dataD.put("total",String.valueOf(d));
            years.add(dataD);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("list",years);
        result.put("xAxis",new ArrayList<>(datas.keySet()));
        return result;
    }*/


    /**
     * @Describe:当前试验室能力统计
     * @Author:WuHui
     * @Date:13:19 2020/12/15
     * @param
     * @return:com.demxs.tdm.common.dto.AbilityChart
     */
    public List<AbilityChart> getAbilitySum(){
        List<AbilityChart> list = new ArrayList<>();
        LabInfo labInfo = new LabInfo();
        //获取商飞能力统计数据
        List<Map<String,String>> company = this.dao.findCompanyAbilityLevel();
        AbilityChart result = new AbilityChart("商飞公司");
        this.buildAbilityChartItem(result,company);
        list.add(result);
        //获取单位能力统计数据
        List<SubCenter> centers = subCenterService.findList(new SubCenter());
        for(SubCenter center:centers){
            List<Map<String,String>> cl = this.dao.findCenterAbilityLevel(center.getId());
            AbilityChart cac = new AbilityChart(center.getName());
            if(cl!=null){
                this.buildAbilityChartItem(cac,cl);
            }
            list.add(cac);
            labInfo.setCenter(center.getId());
            List<LabInfo> labs = labInfoService.findList(labInfo);
            //获取各试验室统计数据
            for(LabInfo lab :labs){
                List<Map<String,String>> ll = this.dao.findLabAbilityLevel(null,lab.getId());
                AbilityChart lac = new AbilityChart(lab.getName());
                if(lac != null){
                    this.buildAbilityChartItem(lac,ll);
                }
                list.add(lac);
            }
        }
        return list;
    }

    /**
     * @Describe:获取试验室能力汇总
     * @Author:WuHui
     * @Date:18:14 2020/12/19
     * @param labId
     * @return:com.demxs.tdm.common.dto.AbilityChart
    */
    public AbilityChart getLabAbilitySum(String labId){
        List<Map<String,String>> ll = this.dao.findLabAbilityLevel(null,labId);
        AbilityChart lac = new AbilityChart();
        if(lac != null){
            this.buildAbilityChartItem(lac,ll);
        }
        return lac;
    }
    /**
     * @Describe:能力预测统计
     * @Author:WuHui
     * @Date:13:19 2020/12/15
     * @param
     * @return:com.demxs.tdm.common.dto.AbilityChart
     */
    public List<AbilityChart> getAbilityPredictSum(String year){
        List<AbilityChart> list = new ArrayList<>();
        LabInfo labInfo = new LabInfo();
        //获取商飞能力统计数据
        List<Map<String,String>> company = predictService.companyAbilityPredict(year);
        AbilityChart result = new AbilityChart("商飞公司");
        this.buildAbilityChartItem(result,company);
        list.add(result);
        //获取单位能力统计数据
        List<SubCenter> centers = subCenterService.findList(new SubCenter());
        for(SubCenter center:centers){
            List<Map<String,String>> cl = predictService.centerAbilityPredict(center.getId(),year);
            AbilityChart cac = new AbilityChart(center.getName());
            if(cl!=null){
                this.buildAbilityChartItem(cac,cl);
            }
            list.add(cac);
            labInfo.setCenter(center.getId());
            List<LabInfo> labs = labInfoService.findList(labInfo);
            //获取各试验室统计数据
            for(LabInfo lab :labs){
                List<Map<String,String>> ll = predictService.labAbilityPredict(lab.getId(),year);
                AbilityChart lac = new AbilityChart(lab.getName());
                if(lac != null){
                    this.buildAbilityChartItem(lac,ll);
                }
                list.add(lac);
            }
        }
        return list;
    }
    /**
     * @Describe:构建能力统计项
     * @Author:WuHui
     * @Date:14:54 2020/12/15
     * @param ac
     * @param datas
     * @return:void
    */
    private void buildAbilityChartItem(AbilityChart ac,List<Map<String,String>> datas){
        for(Map<String,String> data:datas){
            Integer count = Integer.valueOf(data.get("total"));
            switch (data.get("level")){
                case "A":
                    ac.setA(count);
                    break;
                case "B":
                    ac.setB(count);
                    break;
                case "C":
                    ac.setC(count);
                    break;
                case "D":
                    ac.setD(count);
                    break;
                default:
                    break;
            }
        }
    }

    public List<Map<String,String>>  getRootlabAbility(String levelName){
        return this.dao.getRootlabAbility(levelName);
    }

    public List<String>  getCenterSum(String levelName){
        return this.dao.getCenterSum(levelName);
    }

    public List<Map<String,String>>  getAblitySum(String levelName,String labName,String status){
        return this.dao.getAblitySum(levelName,labName,status);
    }
    public List<Map<String,String>>   getDataSum(TestCategory testCategory){
        return this.dao.getDataSum(testCategory);
    }

    public List<Map<String,String>>   getTestCount(TestCategory testCategory){
        return this.dao.getTestCount();
    }

    public List<Map<String,String>>   getLabLevelSum(){
        return this.dao.getLabLevelSum();
    }

    public List<Map<String,String>> getCenterTestSum(){
        return this.dao.getCenterTestSum();
    }

    public List<Map<String,String>> getCenterTestName(String centerName){
        return this.dao.getCenterTestName(centerName);
    }

    /**
     * @Describe:试验室能力迁移
     * @Author:WuHui
     * @Date:19:19 2020/12/16
     * @param removal
     * @return:void
    */
    public void labAbilityRemoval(AbilityRemoval removal){
        //遍历能力编号
        for(String id : removal.getArrIDS()){
            //获取试验室能力信息
            TestCategoryLab clab = testCategoryLabService.get(id);
            removal.setvId(clab.getvId());
            removal.setcId(clab.getcId());
            removal.setBeforeLabId(clab.getLabId());
            //判断目标试验室是否已存在能力
            clab.setLabId(removal.getAfterLabId());
            TestCategoryLab target = testCategoryLabService.getByCvlId(clab);
            if(target == null){
                //获取试验室应具备属性
                TestCategoryAttr attr = new TestCategoryAttr(clab.getcId(),clab.getvId(),removal.getBeforeLabId());
                attr = testCategoryAttrService.getByCvlId(attr);
                //获取能力自查数据
                AbilityEvaluationCheck check = checkService.getEvalCheckByCVLId(clab.getcId(),clab.getvId(),removal.getBeforeLabId());
                //获取能力评估数据
                TestCategoryAssessment assessment = new TestCategoryAssessment(clab.getcId(),removal.getBeforeLabId());
                assessment = assessmentService.getByLabIdAndCId(assessment);
                //获取评估关联设备
                List<EquipmentTest> equipments = equipmentTestService.getEquipmentTestByTestId(assessment.getId());
                //获取评估标准规范
                List<StandardTest> standards = standardTestService.getListByTestId(assessment.getId());
                //复制
                if(RemovalTypeEnum.isType(RemovalTypeEnum.COPY,removal.getType())){
                    //复制试验室能力
                    clab.setId("");
                    testCategoryLabService.save(clab);
                    //复制能力属性
                    if(attr != null){
                        attr.setId("");
                        attr.setLabId(removal.getAfterLabId());
                        testCategoryAttrService.save(attr);
                    }
                    //复制能力自查
                    if(check!=null){
                        check.setId("");
                        check.setLabId(removal.getAfterLabId());
                        checkService.save(check);
                    }
                    //复制评估设备
                    if(assessment!=null){
                        assessment.setId("");
                        assessment.setLabId(removal.getAfterLabId());
                        assessmentService.save(assessment);
                        //复制评估规范
                        if(equipments != null){
                            for(EquipmentTest equipmentTest: equipments){
                                equipmentTest.setTestId(assessment.getId());
                                equipmentTest.setIsNewRecord(true);
                                equipmentTest.setId(IdGen.uuid());
                                equipmentTestService.save(equipmentTest);
                            }
                        }
                        //复制能力评估数据
                        if(standards!=null){
                            for(StandardTest standardTest: standards){
                                standardTest.setTestId(assessment.getId());
                                standardTest.setIsNewRecord(true);
                                standardTest.setId(IdGen.uuid());
                                standardTestService.save(standardTest);
                            }
                        }
                    }
                }else{//迁移
                    //更新试验室能力
                    testCategoryLabService.save(clab);
                    //复制能力属性
                    if(attr!=null){
                        attr.setLabId(removal.getAfterLabId());
                        testCategoryAttrService.save(attr);
                    }
                    //更新能力自查
                    if(check!=null){
                        check.setLabId(removal.getAfterLabId());
                        checkService.save(check);
                    }
                    //更新能力评估
                    if(assessment!=null){
                        assessment.setLabId(removal.getAfterLabId());
                        assessmentService.save(assessment);
                    }
                }
                //保存迁移履历
                abilityRemovalService.save(removal);
            }
        }
    }

    /**
     * @Describe:构建能力预测数据
     * @Author:WuHui
     * @Date:18:34 2020/12/17
     * @param
     * @return:void
    */
    public void buildAbilityPredictData(){
        //清空表数据
        predictService.truncatePredict();
        List<AbilityLabPredict> list = new ArrayList<>();
        //获取当前版本年份
        TestCategoryVersion version = versionService.getEnabledVersion();
        if(version != null && StringUtils.isNotBlank(version.getYear())){
            Integer start = Integer.valueOf(version.getYear());
            //从次年开始推算
            start += 1;
            //获取最大规划年份
            Integer end = testCategoryLabService.getAbilityPlanMaxYear();
            //获取当前所有试验室能力数据
            TestCategoryLab category = new TestCategoryLab();
            category.setvId(version.getId());
            List<TestCategoryLab> datas = testCategoryLabService.findLabInfoAbilityByVersion(category);
            TestCategoryAssessEnum status ;
            //生成各试验室每年能力数据
            for(TestCategoryLab data : datas){
                status = TestCategoryAssessEnum.getStatus(data.getAssessStatus()) ;
                for(int i = start;i <= end ;i++){
                    switch(status){
                        case NOT_EVALUATED:
                        case A:
                            //当前年份为建设计划年份或超过建设计划年份变更能力状态
                            if(this.compareYear(i,data.getRunDate())){
                                status = TestCategoryAssessEnum.B;
                            }
                            //最后一年等级多次变更取最高等级
                            if(this.compareYear(i,data.getInitialDate())){
                                status = TestCategoryAssessEnum.C;
                            }
                            if(this.compareYear(i,data.getPossessDate())){
                                status = TestCategoryAssessEnum.D;
                            }
                            break;
                        case B:
                            if(this.compareYear(i,data.getInitialDate())){
                                status = TestCategoryAssessEnum.C;
                            }
                            //最后一年等级多次变更取最高等级
                            if(this.compareYear(i,data.getPossessDate())){
                                status = TestCategoryAssessEnum.D;
                            }
                            break;
                        case C:
                            if(this.compareYear(i,data.getPossessDate())){
                                status = TestCategoryAssessEnum.D;
                            }
                            break;
                        default:
                            break;
                    }
                    //记录每年能力
                    AbilityLabPredict predict = new AbilityLabPredict(data.getvId(),data.getcId(),data.getLabId(),status.getCode(),String.valueOf(i));
                    predict.preInsert();
                    list.add(predict);
                }
            }
        }
        this.batchSavePredict(list);
    }

    /**
     * @Describe:判断当前年份是否 已达到建设计划年份
     * @Author:WuHui
     * @Date:19:29 2020/12/17
     * @param cur
     * @param date
     * @return:boolean
     */
    private boolean compareYear(int cur,String date){
        boolean than = false;
        if(StringUtils.isNotBlank(date)){
            date = date.substring(0,4);
            than = cur >= Integer.valueOf(date);
        }
        return than;
    }

    /**
     * @Describe:预测数据批量新增
     * @Author:WuHui
     * @Date:16:06 2020/12/19
     * @param list
     * @return:void
    */
    private void batchSavePredict(List<AbilityLabPredict> list){
        int count = 500;
        int listSize = list.size();
        int runSize = (listSize/count)+1;
        List<AbilityLabPredict> newlist = null;
        for (int i = 0; i < runSize ; i++) {
            //计算每个线程执行的数据
            int startIndex = (i*count);
            int endIndex = ((i+1)==runSize) ? list.size() : ((i+1)*count);
            newlist= list.subList(startIndex, endIndex);
            SavePredictThread thead = new SavePredictThread(predictService,newlist);
            Thread t = new Thread(thead);
            t.start();
        }
    }


    public   List<Map<String,String>> getCenterTestData(String levelName,String centerName,String labName){
        return this.dao.getCenterTestData(levelName,centerName,labName);
    }

    public  String  getMaxAblity(String levelName,String centerName){
        return this.dao.getMaxAblity(levelName,centerName);
    }
    public List<ReportDataInfo> getTestMessInfo(){
        return this.dao.getTestMessInfo();
    }
}
