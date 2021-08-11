package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.*;
import com.demxs.tdm.domain.ability.*;
import com.demxs.tdm.domain.ability.constant.TestCategoryModifyRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: Jason
 * @Date: 2020/8/4 09:25
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryVersionService extends CrudService<TestCategoryVersionDao, TestCategoryVersion> {

    @Autowired
    private TestCategoryDao categoryDao;
    @Autowired
    private TestCategoryModifyRequestDao requestDao;
    @Autowired
    private TestCategoryLabDao categoryLabDao;
    @Autowired
    private TestCategoryModifyDao modifyDao;
    @Autowired
    private TestCategoryModifyRecordDao modifyRecordDao;
    @Autowired
    private TestCategoryAssessmentDao assessmentDao;
    @Autowired
    private TestCategoryAssessRequestDao assessRequestDao;
    @Autowired
    private TestCategoryAttrDao attrDao;

    public int enabled(TestCategoryVersion version){
        this.allOutOfEnabled(version);
        return this.dao.enabled(version);
    }

    private int allOutOfEnabled(TestCategoryVersion version){
        return this.dao.allOutOfEnabled(version);
    }

    public int issued(TestCategoryVersion version){
        return this.dao.issued(version);
    }

    public int outOfIssued(TestCategoryVersion version){
        return this.dao.outOfIssued(version);
    }

    public TestCategoryVersion getByCode(TestCategoryVersion version){
        return this.dao.getByCode(version);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(TestCategoryVersion version) {
        if(this.getByCode(version) != null){
            throw new ServiceException("已存在该版本号!!");
        }
        super.save(version);
        //是否点击了复制
        if(null != version.getCopy() && StringUtils.isNotBlank(version.getCopy().getId())){
            List<TestCategory> list = categoryDao.findByVersionId(new TestCategory().setvId(version.getCopy().getId()));
            if (list != null) {
                User user = UserUtils.getUser();
                Map<String, String> idConverterMap = new HashMap<>(list.size());
                for (TestCategory testCategory : list) {
                    String originalId = testCategory.getId();
                    idConverterMap.put(originalId,IdGen.uuid());
                }
                //复制各个试验室试验能力评估等级
                List<TestCategoryLab> copyLab = categoryLabDao.findByVersionId(new TestCategoryLab().setvId(version.getCopy().getId()));
                if (copyLab != null) {
                    for (TestCategoryLab lab : copyLab) {
                        lab.setcId(idConverterMap.get(lab.getcId()));
                        lab.setvId(version.getId());
                        lab.setId(null);
                        this.preInsert(user,lab);
                        categoryLabDao.insert(lab);
                    }
                }
                for (TestCategory testCategory : list) {
                    if(idConverterMap.get(testCategory.getId()) != null){
                        testCategory.setId(idConverterMap.get(testCategory.getId()));
                    }else{
                        testCategory.setId(IdGen.uuid());
                    }
                    testCategory.setvId(version.getId());
                    this.preInsert(user,testCategory);
                    String parentIds = testCategory.getParentIds();
                    if(StringUtils.isNotBlank(parentIds)){
                        String[] split = parentIds.split(",");
                        StringBuilder sb = new StringBuilder(parentIds.length());
                        sb.append("0").append(",");
                        for (String s : split) {
                            if(!"0".equals(s)){
                                String parentId = idConverterMap.get(s);
                                if(null != parentId){
                                    sb.append(parentId).append(",");
                                }
                            }
                        }
                        testCategory.setParentIds(sb.toString());
                    }
                    if(null != testCategory.getParent()){
                        testCategory.setParent(new TestCategory(idConverterMap.get(testCategory.getParent().getId())));
                    }
                    categoryDao.insert(testCategory);
                }
            }
        }
    }

    @Override
    public void delete(TestCategoryVersion version) {
        categoryDao.deleteByVersionId(new TestCategory().setvId(version.getId()));
        requestDao.deleteByVersionId(new TestCategoryModifyRequest().setvId(version.getId()));
        categoryLabDao.deleteByVersionId(new TestCategoryLab().setvId(version.getId()));
        assessmentDao.deleteByVersionId(new TestCategoryAssessment().setvId(version.getId()));
        assessRequestDao.deleteByVersionId(new TestCategoryAssessRequest().setvId(version.getId()));
        attrDao.deleteByVersionId(new TestCategoryAttr().setvId(version.getId()));
        super.delete(version);
    }

    /**
    * @author Jason
    * @date 2020/8/19 17:39
    * @params [version]
    * 构建一个新版本
    * @return void
    */
    public void buildNewVersion(TestCategoryVersion version) {
        if(this.getByCode(version) != null){
            throw new ServiceException("已存在该版本号!!");
        }
        String originalVersionId = version.getId();
        version.setId(IdGen.uuid());
        version.setIsNewRecord(true);
        super.save(version);
        String vId = version.getId();
        //查询版本所有的试验能力
        List<TestCategory> categoryList = categoryDao.findByVersionId(new TestCategory().setvId(originalVersionId));
        //复制各个试验室试验能力评估等级
        List<TestCategoryLab> categoryLabList = categoryLabDao.findByVersionId(new TestCategoryLab().setvId(originalVersionId));
        List<TestCategoryAssessment> assessmentList = assessmentDao.findByVersionId(new TestCategoryAssessment().setvId(originalVersionId));
        if (categoryList != null) {
            //已修改过的记录
            Map<String,Boolean> modifiedMap = new HashMap<>(categoryList.size());
            User user = UserUtils.getUser();
            //ID转换map
            Map<String, String> idConverterMap = new HashMap<>(categoryList.size());
            for (TestCategory category : categoryList) {
                idConverterMap.put(category.getId(),IdGen.uuid());
            }
            //查询版本所有批准后的有效修改申请
            List<TestCategoryModifyRequest> list = requestDao.findByVersionId(new TestCategoryModifyRequest()
                    .setvId(originalVersionId).setEffectiveness(TestCategoryModifyRequest.EFFECTIVE)
                    .setStatus(TestCategoryModifyRequestStatus.APPROVED.getCode()));
            //所有有效修改记录
            List<TestCategory> modifyRecordList = new ArrayList<>(categoryList.size());
            if (list != null) {
                for (TestCategoryModifyRequest request : list) {
                    if (request != null) {
                        List<TestCategoryModify> modifyList = modifyDao.findByRequestIdReturnOriginal(request.getId());
                        if (modifyList != null) {
                            Map<String, String> modifyIdConverterMap = new HashMap<>(modifyList.size());
                            for (TestCategoryModify modify : modifyList) {
                                modifyIdConverterMap.put(modify.getId(),IdGen.uuid());
                            }
                            for (TestCategoryModify modify : modifyList) {
                                //忽略驳回的修改条目
                                if(TestCategoryModify.NOT_APPLIED.equals(modify.getStatus())
                                        || TestCategoryModify.APPLIED.equals(modify.getStatus())){
                                    if(!modify.isAddAction()){
                                        //从列表中移除相同项
                                        categoryList.removeIf(o -> o.getId().equals(modify.getcId()));
                                    }
                                    //改变应用状态
                                    this.changeModifyStatus(modify.setStatus(TestCategoryModify.APPLIED));
                                    if(modify.isToDeleted()){
                                        //从列表中移除相同项
                                        categoryLabList.removeIf(o -> o.getcId().equals(modify.getcId()));
                                        assessmentList.removeIf(o -> o.getcId().equals(modify.getcId()));
                                        continue;
                                    }
                                    TestCategory category = new TestCategory();
                                    category.setvId(vId);
                                    category.setCode(modify.getCode());
                                    category.setName(modify.getName());
                                    category.setStandard(modify.getStandard());
                                    category.setRemarks(modify.getRemarks());
                                    if(modify.isAddAction()){
                                        category.setId(modifyIdConverterMap.get(modify.getId()));
                                    }else{
                                        //修改操作使用转换后的id
                                        category.setId(idConverterMap.get(modify.getcId()));
                                    }
                                    TestCategory originalRecord = null;
                                    if(!modify.isAddAction()){
                                        //查询出原始能力，对比父级
                                        originalRecord = categoryDao.get(modify.getcId());
                                        //如果不是第一次修改此项
                                        if(modifiedMap.get(category.getId()) != null){
                                            resetVal(originalRecord,modify,category);
                                        }else{
                                            //保存上一版本信息主键
                                            category.setBeforeUpCId(modify.getcId());
                                            category.setBeforeUpVId(originalVersionId);
                                        }
                                    }
                                    //只有修改过层级的操作和第一次修改此项时才设置父级id
                                    if(modify.isAddAction() || changedLevel(originalRecord,modify)
                                            || modifiedMap.get(category.getId()) == null){
                                        String parentIds = modify.getParentIds();
                                        //使用转换后的父级id
                                        if(StringUtils.isNotBlank(parentIds)){
                                            String[] split = parentIds.split(",");
                                            StringBuilder sb = new StringBuilder(parentIds.length());
                                            sb.append("0").append(",");
                                            for (String s : split) {
                                                if(!"0".equals(s)){
                                                    String parentId = modifyIdConverterMap.get(s);
                                                    if(null == parentId){
                                                        parentId = idConverterMap.get(s);
                                                    }
                                                    if(null != parentId){
                                                        sb.append(parentId).append(",");
                                                    }
                                                }
                                            }
                                            category.setParentIds(sb.toString());
                                        }
                                        String parentId = modifyIdConverterMap.get(modify.getParentId());
                                        if(parentId == null){
                                            parentId = idConverterMap.get(modify.getParentId());
                                        }
                                        category.setParent(new TestCategory(parentId));
                                        if(!modify.isAddAction() && changedLevel(originalRecord,modify)){
                                            //保存层级修改操作
                                            modifyRecordList.add(category);
                                        }
                                    }
                                    this.preInsert(user,category);
                                    //允许多个试验室、多个申请单对同一试验能力修改
                                    if(modifiedMap.get(category.getId()) == null){
                                        categoryDao.insert(category);
                                        modifiedMap.put(category.getId(),true);
                                    }else{
                                        categoryDao.updateActive(category);
                                    }
                                    //能力评估等级、能力属性
                                    if(modify.isAddAction()){
                                        TestCategoryLab lab = new TestCategoryLab();
                                        lab.setcId(category.getId());
                                        lab.setvId(vId);
                                        lab.setLabId(modify.getLabId());
                                        lab.setAssessStatus(modify.getAssessStatus());
                                        this.preInsert(user,lab);
                                        categoryLabDao.insert(lab);
                                        TestCategoryAttr attr = new TestCategoryAttr();
                                        attr.setcId(category.getId());
                                        attr.setvId(vId);
                                        attr.setLabId(modify.getLabId());
                                        //新增项默认设置未应具备能力
                                        attr.setAttribute(TestCategoryAttr.POSSESS);
                                        this.preInsert(user,attr);
                                        attrDao.insert(attr);
                                    }
                                }
                            }
                        }
                        requestDao.effect(request.setEffectVersionId(vId));
                    }
                }
                //移除相同项后的list再做复制操作
                for (TestCategory category : categoryList) {
                    //保存上一版本主键
                    category.setBeforeUpCId(category.getId());
                    category.setBeforeUpVId(originalVersionId);
                    category.setId(idConverterMap.get(category.getId()));
                    category.setvId(vId);
                    //转换id操作
                    String parentIds = category.getParentIds();
                    if(StringUtils.isNotBlank(parentIds)){
                        String[] split = parentIds.split(",");
                        StringBuilder sb = new StringBuilder(parentIds.length());
                        sb.append("0").append(",");
                        for (String s : split) {
                            if(!"0".equals(s)){
                                String parentId = idConverterMap.get(s);
                                if(null != parentId){
                                    sb.append(parentId).append(",");
                                }
                            }
                        }
                        category.setParentIds(sb.toString());
                    }
                    if(null != category.getParent()){
                        category.setParent(new TestCategory(idConverterMap.get(category.getParent().getId())));
                    }
                    this.preInsert(user,category);
                    categoryDao.insert(category);
                }
                for (TestCategoryLab lab : categoryLabList) {
                    lab.setcId(idConverterMap.get(lab.getcId()));
                    lab.setvId(vId);
                    lab.setId(IdGen.uuid());
                    this.preInsert(user,lab);
                    categoryLabDao.insert(lab);
                }
                for (TestCategoryAssessment assessment : assessmentList) {
                    assessment.setcId(idConverterMap.get(assessment.getcId()));
                    assessment.setvId(vId);
                    assessment.setId(IdGen.uuid());
                    //复制后的评估文件设置为未填写内容状态
                    assessment.setApplyStatus(TestCategoryAssessment.NOT_APPLIED);
                    this.preInsert(user,assessment);
                    assessmentDao.insert(assessment);
                }
                //更新子级的父级ids字符串
                for (TestCategory category : modifyRecordList) {
                    TestCategory parent = new TestCategory();
                    parent.setParent(new TestCategory(category.getId()));
                    parent.setvId(category.getvId());
                    List<TestCategory> childrenList = categoryDao.findByParentIdReturnObj(parent);
                    if(null != childrenList && !childrenList.isEmpty()){
                        this.changeParentIds(category,childrenList);
                    }
                }
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/9/9 13:45
    * @params [originalRecord, modify]
    * 对比原数据，字段不同时保持该值，相同时设为空
    * @return void
    */
    private static void resetVal(TestCategory originalRecord, TestCategoryModify modify,TestCategory instance) {
        if(originalRecord.getCode().equals(modify.getCode())){
            instance.setCode(null);
        }
        if(originalRecord.getName().equals(modify.getName())){
            instance.setName(null);
        }
        if(StringUtils.isNotBlank(originalRecord.getStandard()) && originalRecord.getStandard().equals(modify.getStandard())){
            instance.setStandard(null);
        }
        if(StringUtils.isNotBlank(originalRecord.getRemarks()) && originalRecord.getRemarks().equals(modify.getRemarks())){
            instance.setRemarks(null);
        }
    }

    private void preInsert(User user,DataEntity entity){
        if (StringUtils.isBlank(entity.getId())){
            entity.setId(IdGen.uuid());
        }
        entity.setCreateBy(user);
        entity.setCreateDate(new Date());
        entity.setUpdateBy(user);
        entity.setUpdateDate(new Date());
    }

    private void changeModifyStatus(TestCategoryModify modify){
        if(StringUtils.isBlank(modify.getId())){
            throw new ServiceException("参数为空！！");
        }
        modifyDao.changeStatus(modify);
        TestCategoryModifyRecord record = modifyRecordDao.getByModifyId(new TestCategoryModifyRecord().setmId(modify.getId()));
        record.setStatus(modify.getStatus());
        modifyRecordDao.changeStatus(record);
    }

    private void changeParentIds(TestCategory parent,List<TestCategory> childrenList){
        for (TestCategory testCategory : childrenList) {
            testCategory.setParentIds(parent.getParentIds() + parent.getId() + ",");
            categoryDao.updateActive(testCategory);
            TestCategory p = new TestCategory();
            p.setParent(new TestCategory(testCategory.getId()));
            p.setvId(parent.getvId());
            List<TestCategory> childrenList2 = categoryDao.findByParentIdReturnObj(p);
            if(null != childrenList2 && !childrenList2.isEmpty()){
                this.changeParentIds(testCategory,childrenList2);
            }
        }
    }

    private static boolean changedLevel(TestCategory category,TestCategoryModify modify){
        if(null == category || null == modify){
            return false;
        }
        if(!category.getParentId().equals(modify.getParentId())){
            return true;
        }
        if(!category.getParentIds().equals(modify.getParentIds())){
            return true;
        }
        return false;
    }

    public int effectedRequestCount(TestCategoryVersion version) {
        return this.dao.effectedRequestCount(version);
    }

    public TestCategoryVersion getEnabledVersion(){
        return this.dao.getEnabledVersion();
    }
}
