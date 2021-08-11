package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.*;
import com.demxs.tdm.dao.lab.LabUserDao;
import com.demxs.tdm.domain.ability.*;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessEnum;
import com.demxs.tdm.domain.lab.LabUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xmind.core.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/29 10:51
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryModifyService extends CrudService<TestCategoryModifyDao, TestCategoryModify> {

    @Autowired
    private TestCategoryService testCategoryService;
    @Autowired
    private TestCategoryDao testCategoryDao;
    @Autowired
    private TestCategoryModifyRecordDao modifyRecordDao;
    @Autowired
    private TestCategoryLabService categoryLabService;
    @Autowired
    private TestCategoryAttrDao attrDao;
    @Autowired
    private LabUserDao labUserDao;
    @Autowired
    private TestCategoryAssessmentService assessmentService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    private TestCategorySubDao subDao;

    private static int defaultCollectionSize = 4000;
    private static final String ASSESS_STATUS_KEY = "ability_test_category_assess_status";
    /**
     * xmind文件根节点结构样式
     */
    private static final String STRUCTURE_CLASS_NAME = "org.xmind.ui.logic.right";
    private static final String ROOT_PARENT_ID = "0,";

    public List<TestCategory> findNewRecord(TestCategory testCategory){
        return this.dao.findNewRecord(testCategory);
    }
    public List<TestCategory> findModifyUnionTestCategory(TestCategory testCategory){
        return this.dao.findModifyUnionTestCategory(testCategory);
    }

    public List<TestCategoryModify> findByRequestIdReturnOriginal(String rId){
        return this.dao.findByRequestIdReturnOriginal(rId);
    }
    public List<TestCategoryModify> findByRequestIdDistinct(String rId){
        return this.dao.findByRequestIdDistinct(rId);
    }
    public List<TestCategory> findByRvlId(TestCategory category) {
        return this.dao.findByRvlId(category);
    }
    public List<TestCategoryModify> findByVersionAndCategoryId(TestCategoryModify modify) {
        return this.dao.findByVersionIdAndCategoryId(modify);
    }
    public List<TestCategoryModify> findObserver(TestCategoryModify modify){
        return this.dao.findObserver(modify);
    }
    public int updateLabIdByCreateBy(String create,String labId){
        return this.dao.updateLabIdByCreateBy(create,labId);
    }

    /**
    * @author Jason
    * @date 2020/8/27 17:11
    * @params [category, request, response]
    * 分页，合并数据操作已移交给dao层处理
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    public Page<TestCategory> list(TestCategory category, HttpServletRequest request, HttpServletResponse response){
        String no = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        Page<TestCategory> page = new Page<>(request, response);
        boolean isAll = false;
        //如果页码和每页大小不为数字类型，则代表查看全部
        if(StringUtils.isNumeric(no) && StringUtils.isNumeric(pageSize)){
            //否则分页
            category.setPage(page);
        }else{
            isAll = true;
        }
        List<TestCategory> list = this.dao.findModifyUnionTestCategory(category);
        if(null != list){
            for (TestCategory testCategory : list) {
                //如果为新增项，则有可能缺失父级
                if(null != testCategory && StringUtils.isNotBlank(testCategory.getIsNew())){
                    this.appendParent(testCategory);
                }
            }
        }
        page.setList(list);
        if(isAll && null != list){
            page.setCount(list.size());
            page.setPageNo(1);
            page.setPageSize(list.size());
        }
        return page;
    }

    /**
    * @author Jason
    * @date 2020/7/30 12:45
    * @params [category, request, response]
    * 分页，合并数据
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    /*public Page<TestCategory> list(TestCategory category, HttpServletRequest request, HttpServletResponse response){
        String no = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if(!StringUtils.isNumeric(no) || !StringUtils.isNumeric(pageSize)){
            return null;
        }else{
            //审核页，只看所有修改项
            if(StringUtils.isNoneBlank(category.getIsNew(),category.getHasDeleted(),category.getHasModify())){
                return this.packingData(category,request,response);
            }else{
                if(StringUtils.isNotBlank(category.getIsNew())){
                    Page<TestCategory> page = new Page<>(request, response);
                    //只查询modify
                    category.setPage(page);
                    page.setList(this.findNewRecord(category));
                    return page;
                }else if(StringUtils.isNotBlank(category.getHasDeleted()) || StringUtils.isNotBlank(category.getHasModify())){
                    Page<TestCategory> page = new Page<>(request, response);
                    //只查询category
                    category.setPage(page);
                    page.setList(testCategoryService.findLowestLevelNodeWithModify(category));
                    return page;
                }
                return this.packingData(category,request,response);
            }
        }
    }*/

    /**
    * @author Jason
    * @date 2020/8/11 17:27
    * @params [category, request, response]
    * 合并数据
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    /*public Page<TestCategory> packingData(TestCategory category, HttpServletRequest request, HttpServletResponse response){
        Page<TestCategory> page = new Page<>(request, response);
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        //合并结果集分页
        int newRecordCount = this.findNewRecordCount(category);
        int originalCount = testCategoryService.findLowestLevelNodeWithModifyCount(category);
        category.setPage(page);
        List<TestCategory> original = testCategoryService.findLowestLevelNodeWithModify(category);
        category.setPage(new Page<>(pageNo,pageSize));
        List<TestCategory> newRecord = this.findNewRecord(category);
        this.appendParent(newRecord);
        if(newRecordCount < pageNo * pageSize){
            //相等代表分页失败，应丢弃结果集
            if(newRecord.size() == pageSize){
                //直接丢弃
                newRecord = original;
            }else {
                //如果是第一页则合并结果集，否则丢弃
                if(newRecordCount < pageSize && pageNo == 1){
                    newRecord.addAll(original);
                }else{
                    newRecord = original;
                }
            }
        }else{
            newRecord.addAll(original);
        }
        page.setList(newRecord);
        page.setCount(newRecordCount + originalCount);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        return page;
    }*/

    /**
    * @author Jason
    * @date 2020/8/20 17:51
    * @params [list]
    * 查找父级
    * @return void
    */
    private void appendParent(List<TestCategory> list){
        if(null != list){
            for (TestCategory testCategory : list) {
                this.appendParent(testCategory);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/8/27 17:12
    * @params [testCategory]
    * 查找父级
    * @return void
    */
    public void appendParent(TestCategory testCategory){
        if(StringUtils.isNotBlank(testCategory.getParentIds())){
            String[] split = testCategory.getParentIds().split(",");
            if(split.length > 1){
                if(null == testCategory.getParentList()){
                    testCategory.setParentList(new ArrayList<>(split.length));
                    for (String id : split) {
                        TestCategoryModify parent = this.get(id);
                        if(null != parent){
                            TestCategory temp = new TestCategory();
                            temp.setCode(parent.getCode()).setName(parent.getName());
                            testCategory.getParentList().add(temp);
                        }
                    }
                }else{
                    if(split.length - 1 != testCategory.getParentList().size()){
                        List<String> notFoundParentIds = new ArrayList<>(split.length);
                        for (String id : split) {
                            if("0".equals(id)){
                                continue;
                            }
                            boolean founded = false;
                            for (TestCategory category : testCategory.getParentList()) {
                                if(category.getId().equals(id)){
                                    founded = true;
                                    break;
                                }
                            }
                            if(!founded){
                                notFoundParentIds.add(id);
                            }
                        }
                        boolean founded = false;
                        for (String notFoundParentId : notFoundParentIds) {
                            TestCategoryModify parent = this.get(notFoundParentId);
                            if(null != parent){
                                TestCategory temp = new TestCategory();
                                temp.setCode(parent.getCode()).setName(parent.getName());
                                temp.setParentIds(parent.getParentIds());
                                temp.setId(parent.getId());
                                temp.setParent(new TestCategory(parent.getParentId()));
                                testCategory.getParentList().add(temp);
                                founded = true;
                            }
                        }
                        if(founded){
                            //重新排序
                            testCategory.setParentList(testCategory.getParentList());
                        }
                    }
                }
            }
        }
    }

    public int findNewRecordCount(TestCategory category){
        return this.dao.findNewRecordCount(category);
    }

    public int changeStatus(TestCategoryModify modify){
        return this.dao.changeStatus(modify);
    }

    @Override
    public void save(TestCategoryModify modify) {
        TestCategoryModifyRecord modifyRecord = new TestCategoryModifyRecord();
        modifyRecord.setcId(modify.getcId());
        modifyRecord.setvId(modify.getvId());
        modifyRecord.setrId(modify.getrId());
        if(modify.isToDeleted()){
            modifyRecord.setAction(TestCategoryModifyRecord.DELETE);
            modifyRecord.setInfo("[" + modify.getCode() + "]" + modify.getName());
            //禁用该能力的其他修改项
            this.dao.disabledModifyByCvrId(modify);
        }else if(StringUtils.isNotBlank(modify.getcId())){
            //修改操作
            List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(modify.getCode()).setvId(modify.getvId()));
            if((null != list)){
                for (TestCategory temp : list) {
                    if(!modify.getcId().equals(temp.getId())){
                        throw new ServiceException("该编号已存在："+modify.getCode());
                    }
                }
            }
            List<TestCategoryModify> list1 = this.findByCodeAndRequestId(new TestCategoryModify().setCode(modify.getCode()).setrId(modify.getrId()));
            if((null != list1)){
                for (TestCategoryModify temp : list1) {
                    if(!modify.getcId().equals(temp.getcId())){
                        throw new ServiceException("该编号已存在："+modify.getCode());
                    }
                }
            }
            TestCategory parent = testCategoryDao.get(modify.getParentId());
            //从testCategory中没有找到该父级，代表选择的是新增的父级分支
            if(null == parent){
                TestCategoryModify temp = this.get(modify.getParentId());
                if(null != temp){
                    parent = new TestCategory(temp.getId());
                    parent.setParentIds(temp.getParentIds());
                }
            }
            if(null != parent){
                modify.setParentIds(parent.getParentIds() + parent.getId() + ",");
            }else {
                modify.setParentIds(ROOT_PARENT_ID);
            }
            modifyRecord.setAction(TestCategoryModifyRecord.UPDATE);
            //对比差异
            modifyRecord.setInfo(this.getDifference(modify));
        }else{
            //新增操作
            List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(modify.getCode()).setvId(modify.getvId()));
            if(null != list && !list.isEmpty()){
                throw new ServiceException("该编号已存在："+modify.getCode());
            }
            List<TestCategoryModify> list1 = this.findByCodeAndRequestId(new TestCategoryModify().setCode(modify.getCode()).setrId(modify.getrId()));
            if(null != list1 && !list1.isEmpty()){
                throw new ServiceException("该编号已存在："+modify.getCode());
            }
            TestCategory testCategory = testCategoryDao.get(modify.getParentId());
            if(null != testCategory){
                modify.setParentId(testCategory.getId());
                modify.setParentIds(testCategory.getParentIds() + testCategory.getId() + ",");
            }else{
                if(StringUtils.isNotBlank(modify.getParentId())){
                    //代表新增的分支，尚未保存的修改信息
                    TestCategoryModify parent = this.get(modify.getParentId());
                    if(null != parent){
                        modify.setParentId(parent.getId());
                        modify.setParentIds(parent.getParentIds() + parent.getId() + ",");
                    }
                }else {
                    modify.setParentIds(ROOT_PARENT_ID);
                }
            }
            modify.setIsNew("1");
            modifyRecord.setAction(TestCategoryModifyRecord.INSERT);
            //对比差异
            modifyRecord.setInfo(this.getDifference(modify));
        }
        modify.setStatus(TestCategoryModify.NOT_APPLIED);
        super.save(modify);
        modifyRecord.setmId(modify.getId());
        modifyRecord.setCreateBy(modify.getCreateBy());
        modifyRecord.setCreateDate(modify.getCreateDate());
        modifyRecord.setUpdateBy(modify.getUpdateBy());
        modifyRecord.setUpdateDate(modify.getUpdateDate());
        modifyRecord.setId(IdGen.uuid());
        modifyRecord.setStatus(modify.getStatus());
        modifyRecordDao.insert(modifyRecord);
    }

    /**
    * @author Jason
    * @date 2020/8/7 9:16
    * @params [modify]
    * 对比获取差异
    * @return java.lang.String
    */
    private String getDifference(TestCategoryModify modify){
        StringBuilder difference = new StringBuilder();
        if(StringUtils.isNotBlank(modify.getcId())){
            //修改
            TestCategory testCategory = testCategoryDao.get(modify.getcId());
            if(null != testCategory.getCode() && !testCategory.getCode().equals(modify.getCode())){
                difference.append("原编码：[").append(testCategory.getCode()).append("] -> 现编码：[")
                        .append(modify.getCode()).append("]，");
            }else{
                difference.append("编码：[").append(modify.getCode()).append("]，");
            }
            if(null != testCategory.getName() &&  !testCategory.getName().equals(modify.getName())){
                difference.append("原名称：[").append(testCategory.getName()).append("] -> 现名称：[")
                        .append(modify.getName()).append("]，");
            }else{
                difference.append("名称：[").append(modify.getName()).append("]，");
            }
            if(testCategory.getParent() == null || testCategory.getParent().getId() == null){
                TestCategory parent = testCategoryDao.get(modify.getParentId());
                if(parent == null){
                    TestCategoryModify temp = this.get(modify.getParentId());
                    if(null != temp){
                        parent = new TestCategory(temp.getId());
                        parent.setCode(temp.getCode());
                        parent.setName(temp.getName());
                    }
                }
                if(null != parent){
                    difference.append("现父级：[").append(parent.getCode()).append(parent.getName()).append("]，");
                }
            }else{
                TestCategory parent = testCategoryDao.get(testCategory.getParent().getId());
                if(null != parent){
                    if(null != parent.getId() && !parent.getId().equals(modify.getParentId())){
                        TestCategory newParent = testCategoryDao.get(modify.getParentId());
                        if(null == newParent){
                            TestCategoryModify temp = this.get(modify.getParentId());
                            if(null != temp){
                                newParent = new TestCategory(temp.getId());
                                newParent.setCode(temp.getCode());
                                newParent.setName(temp.getName());
                            }
                        }
                        if(null != newParent){
                            difference.append("原父级：[").append(parent.getCode())
                                    .append(parent.getName()).append("] -> 现父级：[")
                                    .append(newParent.getCode()).append(newParent.getName())
                                    .append("]，");
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(modify.getAssessStatus())){
                String dictValue = DictUtils.getDictLabel(modify.getAssessStatus(),ASSESS_STATUS_KEY, "");
                TestCategoryLab categoryLab = categoryLabService.getByCvlId(
                        new TestCategoryLab().setcId(modify.getcId()).setvId(modify.getvId()).setLabId(modify.getLabId()));
                if(null != categoryLab){
                    String originalValue = DictUtils.getDictLabel(categoryLab.getAssessStatus(),ASSESS_STATUS_KEY, "");
                    difference.append("原能力评估状态：[").append(originalValue).append("] ->现能力评估状态：[")
                            .append(dictValue).append("]");
                }else{
                    difference.append("能力评估状态：[").append(dictValue).append("]，");
                }
            }
            if(null != testCategory.getStandard() && !testCategory.getStandard().equals(modify.getStandard())){
                difference.append("原检测规范：[").append(testCategory.getStandard()).append("] -> 现检测规范：[")
                        .append(modify.getStandard()).append("]，");
            }else{
                difference.append("检测规范：[").append(modify.getStandard()).append("]，");
            }
            if(null != testCategory.getRemarks() && !testCategory.getRemarks().equals(modify.getRemarks())){
                difference.append("原备注：[").append(testCategory.getRemarks()).append("] -> 现备注：[")
                        .append(modify.getRemarks()).append("]，");
            }else{
                difference.append("备注：[").append(modify.getRemarks()).append("]，");
            }
            difference.deleteCharAt(difference.length() - 1);
        }else{
            //新增
            if(StringUtils.isNotBlank(modify.getCode())){
                difference.append("编码：[").append(modify.getCode()).append("]，");
            }
            if(StringUtils.isNotBlank(modify.getName())){
                difference.append("名称：[").append(modify.getName()).append("]，");
            }
            if(StringUtils.isNotBlank(modify.getParentId())){
                TestCategory parent = testCategoryDao.get(modify.getParentId());
                if(null != parent){
                    difference.append("父级编码：[").append(parent.getCode()).append("]，");
                    difference.append("父级名称：[").append(parent.getName()).append("]，");
                }else{
                    TestCategoryModify _parent = this.get(modify.getParentId());
                    if (_parent != null) {
                        difference.append("父级编码：[").append(_parent.getCode()).append("]，");
                        difference.append("父级名称：[").append(_parent.getName()).append("]，");
                    }
                }
            }
            if(StringUtils.isNotBlank(modify.getAssessStatus())){
                String dictValue = DictUtils.getDictLabel(modify.getAssessStatus(),ASSESS_STATUS_KEY, "");
                difference.append("能力评估状态：[").append(dictValue).append("]，");
            }
            if(StringUtils.isNotBlank(modify.getStandard())){
                difference.append("检测规范：[").append(modify.getStandard()).append("]，");
            }
            if(StringUtils.isNotBlank(modify.getRemarks())){
                difference.append("备注：[").append(modify.getRemarks()).append("]，");
            }
            if(difference.length() > 0){
                difference.deleteCharAt(difference.length() - 1);
            }
        }
        return difference.toString();
    }

    @Transactional(readOnly = false)
    public void batchApply(String ids){
        String[] split = ids.split(",");
        for (String id : split) {
            this.apply(new TestCategoryModify(id));
        }
    }

    @Transactional(readOnly = false)
    public void batchApply(List<TestCategoryModify> list){
        for (TestCategoryModify modify : list) {
            this.apply(modify);
        }
    }

    /**
    * @author Jason
    * @date 2020/7/30 12:45
    * @params [categoryModify]
    * 应用：删除、新增、修改
    * @return void
    */
    @Transactional(readOnly = false)
    public void apply(TestCategoryModify categoryModify){
        //TestCategoryModify originalModify = categoryModify;
        //categoryModify = this.get(categoryModify.getId());
        TestCategory record = new TestCategory();
        if(categoryModify != null && TestCategoryModify.NOT_APPLIED.equals(categoryModify.getStatus())){
            if(categoryModify.isAddAction()){
                //新增操作
                List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(categoryModify.getCode()).setvId(categoryModify.getvId()));
                if(null != list && !list.isEmpty()){
                    throw new ServiceException("该编号已存在："+categoryModify.getCode());
                }
                record.setCode(categoryModify.getCode());
                record.setName(categoryModify.getName());
                record.setStandard(categoryModify.getStandard());
                record.setRemarks(categoryModify.getRemarks());
                record.setParentIds(categoryModify.getParentIds());
                record.setParent(new TestCategory(categoryModify.getParentId()));
                record.setIsNewRecord(true);
                //使用categoryModify的id的原因是为了保持父子层级关系
                record.setId(categoryModify.getId());
                record.setvId(categoryModify.getvId());
                testCategoryService.save(record);
                TestCategoryLab categoryLab = new TestCategoryLab();
                categoryLab.setLabId(categoryModify.getLabId()).setcId(record.getId());
                categoryLab.setvId(categoryModify.getvId());
                categoryLab.setAssessStatus(categoryModify.getAssessStatus());
                categoryLabService.save(categoryLab);
            }else if(categoryModify.isToDeleted()){
                //删除操作
                record.setId(categoryModify.getcId());
                testCategoryService.delete(record);
                //清空能力验证信息
                categoryLabService.deleteByLabIdAndCategoryId(new TestCategoryLab()
                        .setLabId(categoryModify.getLabId()).setcId(categoryModify.getcId()));
            }else{
                //修改操作
                List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(categoryModify.getCode()).setvId(categoryModify.getvId()));
                if((null != list)){
                    for (TestCategory temp : list) {
                        if(!categoryModify.getcId().equals(temp.getId())){
                            throw new ServiceException("该编号已存在："+categoryModify.getCode());
                        }
                    }
                }
                record.setId(categoryModify.getcId());
                record.setCode(categoryModify.getCode());
                record.setName(categoryModify.getName());
                record.setStandard(categoryModify.getStandard());
                record.setRemarks(categoryModify.getRemarks());
                record.setvId(categoryModify.getvId());
                TestCategory parent = new TestCategory(categoryModify.getParentId());
                record.setParent(parent);
                record.setParentIds(categoryModify.getParentIds());
                testCategoryService.save(record);
                TestCategoryLab categoryLab = new TestCategoryLab();
                //清空能力验证信息重新保存
                categoryLabService.deleteByLabIdAndCategoryId(categoryLab.setLabId(categoryModify.getLabId())
                        .setcId(categoryModify.getcId()));
                categoryLab.setvId(categoryModify.getvId());
                categoryLab.setAssessStatus(categoryModify.getAssessStatus());
                categoryLabService.save(categoryLab);
            }
            //改变修改状态和修改记录状态
            this.changeModifyStatus(categoryModify.setStatus(TestCategoryModify.APPLIED));
            //this.delete(categoryModify);
        }
        /*else{
            //代表点击了根列表页的应用按钮
            TestCategory testCategory = testCategoryDao.get(originalModify.getId());
            TestCategoryModify modify = new TestCategoryModify().setcId(testCategory.getId());
            List<TestCategoryModify> modifyList = this.findList(modify);
            if(null != modifyList){
                //存在删除操作则优先执行删除
                for (TestCategoryModify testCategoryModify : modifyList) {
                    if(null != testCategoryModify && testCategoryModify.isToDeleted()){
                        this.apply(testCategoryModify);
                        return;
                    }
                }
                //否则取最后一次操作
                TestCategoryModify lastModify = modifyList.get(0);
                if(lastModify != null){
                    this.apply(lastModify);
                }
            }
        }*/
    }

    @Override
    public void delete(TestCategoryModify modify) {
        modifyRecordDao.deleteByModifyId(new TestCategoryModifyRecord().setmId(modify.getId()));
        super.delete(modify);
        //删除字节点
        List<TestCategoryModify> list = this.findByParentId(new TestCategoryModify().setParentId(modify.getId()));
        if(null != list){
            for (TestCategoryModify children : list) {
                this.delete(children);
            }
        }
    }

    public List<TestCategoryModify> findByParentId(TestCategoryModify modify){
        return this.dao.findByParentId(modify);
    }
    public List<TestCategoryModify> findByParentIdLike(TestCategoryModify modify){
        return this.dao.findByParentIdLike(modify);
    }

    public List<TestCategoryModify> findByVersionId(TestCategoryModify modify){
        return this.dao.findByVersionId(modify);
    }

    public List<TestCategoryModify> findByVcrId(TestCategoryModify modify){
        return this.dao.findByVcrId(modify);
    }

    public List<TestCategoryModify> findByCodeAndRequestId(TestCategoryModify modify){
        return this.dao.findByCodeAndRequestId(modify);
    }

    public int deleteByParentId(TestCategoryModify modify){
        return this.dao.deleteByParentId(modify);
    }

    public int deleteByRequestAndCategoryId(TestCategoryModify modify){
        return this.dao.deleteByRequestAndCategoryId(modify);
    }

    /**
    * @author Jason
    * @date 2020/8/6 16:42
    * @params [modify]
    * 清空本次申请中的指定试验能力的所有修改项
    * @return void
    */
    public void rollback(TestCategoryModify modify) {
        this.deleteByRequestAndCategoryId(modify);
        modifyRecordDao.deleteByRequestAndCategoryId(new TestCategoryModifyRecord().setrId(modify.getrId()).setcId(modify.getcId()));
    }

    /**
    * @author Jason
    * @date 2020/8/12 9:22
    * @params [modify]
    * 驳回修改项
    * @return void
    */
    public void rejectModify(TestCategoryModify modify) {
        this.changeModifyStatus(modify.setStatus(TestCategoryModify.REJECT));
    }

    /**
    * @author Jason
    * @date 2020/8/12 9:34
    * @params [modify]
    * 重新提交
    * @return void
    */
    public void reSubMit(TestCategoryModify modify) {
        this.changeModifyStatus(modify.setStatus(TestCategoryModify.NOT_APPLIED));
    }

    public void changeModifyStatus(TestCategoryModify modify){
        if(StringUtils.isBlank(modify.getId())){
            throw new ServiceException("参数为空！！");
        }
        this.dao.changeStatus(modify);
        TestCategoryModifyRecord record = modifyRecordDao.getByModifyId(new TestCategoryModifyRecord().setmId(modify.getId()));
        record.setStatus(modify.getStatus());
        modifyRecordDao.changeStatus(record);
        List<TestCategoryModify> childrenList = this.dao.findByParentIdLike(new TestCategoryModify().setParentId(modify.getId()));
        //改变修改项子级的状态
        if(null != childrenList && !childrenList.isEmpty()){
            for (TestCategoryModify children : childrenList) {
                this.dao.changeStatus(children.setStatus(modify.getStatus()));
                TestCategoryModifyRecord r = modifyRecordDao.getByModifyId(new TestCategoryModifyRecord().setmId(children.getId()));
                r.setStatus(modify.getStatus());
                modifyRecordDao.changeStatus(r);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/8/12 10:03
    * @params [modify]
    * 全部驳回（指定版本的指定申请单的指定试验能力的修改申请）
    * @return void
    */
    public void rejectAll(TestCategoryModify modify) {
        List<TestCategoryModify> list = this.findByVcrId(modify);
        if(null != list){
            for (TestCategoryModify testCategoryModify : list) {
                this.rejectModify(testCategoryModify);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/8/20 19:11
    * @params [modify]
    * 全部驳回（包含版本所有试验室的指定试验能力的所有申请）
    * @return void
    */
    public void rejectAllInEveryRequest(TestCategoryModify modify) {
        List<TestCategoryModify> list = this.dao.findByVersionIdAndCategoryId(modify);
        if(null != list){
            for (TestCategoryModify testCategoryModify : list) {
                this.rejectModify(testCategoryModify);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/8/12 10:22
    * @params [modify]
    * 撤销驳回全部（指定版本指定申请单的指定试验能力的修改申请驳回）
    * @return void
    */
    public void reSubmitAll(TestCategoryModify modify) {
        List<TestCategoryModify> list = this.findByVcrId(modify);
        if(null != list){
            for (TestCategoryModify testCategoryModify : list) {
                this.reSubMit(testCategoryModify);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/8/20 19:12
    * @params [modify]
    * 撤销全部驳回（包含版本所有试验室的指定试验能力的所有修改申请驳回）
    * @return void
    */
    public void reSubmitAllInEveryRequest(TestCategoryModify modify) {
        List<TestCategoryModify> list = this.dao.findByVersionIdAndCategoryId(modify);
        if(null != list){
            for (TestCategoryModify testCategoryModify : list) {
                this.reSubMit(testCategoryModify);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/8/21 16:50
    * @params [modify]
    * 撤销删除根
    * @return void
    */
    public void rollbackRemove(TestCategoryModify modify) {
        this.dao.rollbackRemoveByCvrId(modify);
        this.dao.delete(modify);
    }

    /**
    * @author Jason
    * @date 2020/8/24 10:14
    * @params [modify]
    * 删除前检查是否存在子项
    * @return int
    */
    public int checkChildren(TestCategoryModify modify) {
        List<TestCategory> list = testCategoryDao.findChildrenCount(modify.getcId());
        int count = null == list ? -1 : list.size();
        if(count > 0){
            boolean allIsDeleted = true;
            for (TestCategory category : list) {
                List<TestCategoryModify> l = this.dao.findByVcrId(modify.setcId(category.getId()));
                if (l != null && !l.isEmpty()) {
                    for (TestCategoryModify m : l) {
                        if(!m.isToDeleted()){
                            allIsDeleted = false;
                            break;
                        }
                    }
                }else if(l != null && l.isEmpty()){
                    allIsDeleted = false;
                    break;
                }
            }
            if(allIsDeleted){
                count = 0;
            }
        }
        if(count <= 0){
            count = this.dao.findChildrenCount(modify.setParentId(modify.getcId()));
        }
        return count;
    }

    final static String SUFFIX = "01";
    /**
    * @author Jason
    * @date 2020/8/24 13:26
    * @params [modify]
    * 获取同一个版本下的同一个父级的下一个自动编码
    * @return java.lang.String
    */
    public String getNextCodeInVersionModify(TestCategoryModify modify){
        if(StringUtils.isNotBlank(modify.getParentId())){
            //只查询新增修改，忽略修改项，以新增项为基准获取编码
            List<TestCategoryModify> sameList = this.dao.findByParentId(modify.setIsNew("1"));
            if(sameList == null || sameList.isEmpty()){
                TestCategoryModify parent = this.dao.get(modify.getParentId());
                //选择了以新增项作为父级
                if(parent != null){
                    sameList = this.dao.findByParentId(modify.setIsNew("1"));
                    //代表一个子级也没有，以自己为基准生成code
                    if(sameList == null || sameList.isEmpty()){
                        return parent.getCode() + SUFFIX;
                    }
                }
            }
            if (sameList != null && !sameList.isEmpty()) {
                for (TestCategoryModify m : sameList) {
                    String s = m.getCode();
                    if(StringUtils.isNotBlank(s)){
                        String suffix = s.substring(s.length() - 2);
                        if(StringUtils.isNumeric(suffix)){
                            int code = Integer.parseInt(suffix);
                            //判断编码是否溢出
                            if(code + 1 > 99){
                                s += SUFFIX;
                            }else{
                                s = s.substring(0,s.length() - 2);
                                s += String.format("%02d", ++code);
                            }
                            return s;
                        }
                    }
                }
                TestCategoryModify parent = this.dao.get(modify.getParentId());
                if(null != parent){
                    return parent.getCode() + SUFFIX;
                }
                TestCategory category = testCategoryDao.get(modify.getParentId());
                if(null != category){
                    return category.getCode() + SUFFIX;
                }
            }else{
                TestCategory temp = new TestCategory();
                temp.setParent(new TestCategory(modify.getParentId()));
                temp.setvId(modify.getvId());
                TestCategory original = testCategoryDao.get(modify.getParentId());
                temp.setCode(original == null ? null : original.getCode());
                return testCategoryService.getNextCode(temp);
            }
        }
        return null;
    }

    public int updateModify(TestCategoryModify modify) {
        if(StringUtils.isNotBlank(modify.getIsNew())){
            TestCategoryModifyRecord record = modifyRecordDao.getByModifyId(new TestCategoryModifyRecord().setmId(modify.getId()));
            if(record != null){
                String info = this.getDifference(modify);
                record.setInfo(info);
                record.preUpdate();
                modifyRecordDao.update(record);
            }
        }
        modify.preUpdate();
        return this.dao.updateActive(modify);
    }

    public int deleteByRequestId(TestCategoryModify modify) {
        return this.dao.deleteByRequestId(modify);
    }

    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void batchSaveLabAttr(List<TestCategoryAttr> attrList) {
        if(null != attrList && !attrList.isEmpty()){
            User curUser = UserUtils.getUser();
            String userId = curUser.getId();
            List<LabUser> lab = labUserDao.findByUserId(new LabUser().setUserId(userId));
            if(null == lab || lab.isEmpty()){
                throw new ServiceException("未分配试验室！！");
            }
            String labId = lab.get(0).getLabId();
            //解析集合数据
            for (TestCategoryAttr attr : attrList) {
                attr.setLabId(labId);
                TestCategoryAttr a = attrDao.getByCvlId(attr);
                //持久化试验能力 是否具备属性
                if(null == a){
                    attr.preInsert();
                    attrDao.insert(attr);
                }else{
                    attr.preUpdate();
                    attr.setId(a.getId());
                    attrDao.update(attr);
                }
                //持久化实验室能力数据
                TestCategoryLab testCategoryLab = new TestCategoryLab(attr.getcId(),attr.getvId(),attr.getLabId());
                testCategoryLab = categoryLabService.getByCvlId(testCategoryLab);
                if(testCategoryLab == null && attr.getAttribute().equals("1")){
                    testCategoryLab = new TestCategoryLab(attr.getcId(),attr.getvId(),attr.getLabId());
                    TestCategoryAssessEnum status = TestCategoryAssessEnum.A;
                    testCategoryLab.setAssessStatus(status.getCode());
                    categoryLabService.save(testCategoryLab);
                    //更新能力评估表
                    TestCategoryAssessment assessment = new TestCategoryAssessment(attr.getcId(),attr.getLabId());
                    assessment = assessmentService.getByLabIdAndCId(assessment);
                    if(assessment == null){
                        assessment = new TestCategoryAssessment(attr.getcId(),attr.getLabId(),attr.getvId());
                        assessment.setApplyStatus(TestCategoryAssessment.NOT_APPLIED);
                        assessment.setDataStatus(TestCategoryAssessment.NO_UPDATE);
                        assessmentService.save(assessment);
                    }else{
                        if(StringUtils.isNotBlank(assessment.getEnclosure())){
                            assessment.setDataStatus(TestCategoryAssessment.UPLOAED);
                        }
                        assessment.setStatus(status.getCode());
                        assessment.setUpdateBy(UserUtils.getUser());
                        assessmentService.save(assessment);
                    }
                }
            }
        }
    }

    /**
     * 能力订阅
     * @param attrList
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void batchSaveSubAbility(List<TestCategorySub> attrList) {
        if(null != attrList && !attrList.isEmpty()){
            User curUser = UserUtils.getUser();
            String userId = curUser.getId();
            List<LabUser> lab = labUserDao.findByUserId(new LabUser().setUserId(userId));
            if(null == lab || lab.isEmpty()){
                throw new ServiceException("未分配试验室！！");
            }
            String labId = lab.get(0).getLabId();
            //解析集合数据
            for (TestCategorySub sub : attrList) {
                sub.setLabId(labId);
                TestCategorySub a = subDao.getByCvlId(sub);
                //持久化试验能力 是否具备属性
                if(null == a){
                    sub.setUserId(userId);
                    sub.preInsert();
                    subDao.insert(sub);
                }else{
                    sub.preUpdate();
                    sub.setId(a.getId());
                    subDao.update(sub);
                }
                //持久化实验室能力数据
                TestCategoryLab testCategoryLab = new TestCategoryLab(sub.getcId(),sub.getvId(),sub.getLabId());
                testCategoryLab = categoryLabService.getByCvlId(testCategoryLab);
                if(testCategoryLab == null && sub.getSubscribe().equals("1")){
                    testCategoryLab = new TestCategoryLab(sub.getcId(),sub.getvId(),sub.getLabId());
                    TestCategoryAssessEnum status = TestCategoryAssessEnum.A;
                    testCategoryLab.setAssessStatus(status.getCode());
                    categoryLabService.save(testCategoryLab);
                    //更新能力评估表
                    TestCategoryAssessment assessment = new TestCategoryAssessment(sub.getcId(),sub.getLabId());
                    assessment = assessmentService.getByLabIdAndCId(assessment);
                    if(assessment == null){
                        assessment = new TestCategoryAssessment(sub.getcId(),sub.getLabId(),sub.getvId());
                        assessment.setApplyStatus(TestCategoryAssessment.NOT_APPLIED);
                        assessment.setDataStatus(TestCategoryAssessment.NO_UPDATE);
                        assessmentService.save(assessment);
                    }else{
                        if(StringUtils.isNotBlank(assessment.getEnclosure())){
                            assessment.setDataStatus(TestCategoryAssessment.UPLOAED);
                        }
                        assessment.setStatus(status.getCode());
                        assessment.setUpdateBy(UserUtils.getUser());
                        assessmentService.save(assessment);
                    }
                }
            }
        }
    }
    /**
    * @author Jason
    * @date 2020/9/16 10:26
    * @params [category, request, response]
    * 导出xmind格式文件
    * @return void
    */
    public void exportXMind(TestCategory category, HttpServletRequest request, HttpServletResponse response) {
        IWorkbookBuilder builder = Core.getWorkbookBuilder();
        IWorkbook workbook = builder.createWorkbook();
        try {
            List<TestCategory> data = testCategoryDao.findAllList(category);
            if(null != data){
                data.addAll(this.dao.findByRvlId(category));
            }
            if(null != data && !data.isEmpty()){
                ISheet primarySheet = workbook.getPrimarySheet();
                ITopic rootNode = primarySheet.getRootTopic();
                rootNode.setStructureClass(STRUCTURE_CLASS_NAME);
                rootNode.setTitleText("试验能力");
                logger.info("导出试验能力xmind开始");
                for (TestCategory testCategory : data) {
                    //识别出根节点
                    if(testCategory.getParent() == null && ROOT_PARENT_ID.equals(testCategory.getParentIds())){
                        ITopic primaryNode = workbook.createTopic();
                        primaryNode.setTitleText("[" + testCategory.getCode() + "]" + testCategory.getName());
                        rootNode.add(primaryNode);
                        this.appendChildren(primaryNode,testCategory,workbook,data,0);
                    }
                }
                workbook.save(response.getOutputStream());
                logger.info("导出试验能力xmind成功");
            }else{
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition","attachment; filename="+new String(("数据集为空.txt").getBytes("gb2312"), "ISO8859-1"));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("导出试验能力xmind失败：" + e.getMessage());
        }
    }

    public void appendChildren(ITopic parentNode,TestCategory parent,IWorkbook workbook,List<TestCategory> data,int depth){
        for (TestCategory children : data) {
            if(null != children.getParent() && null != children.getParent().getId()
                    && children.getParent().getId().equals(parent.getId())){
                ITopic node = workbook.createTopic();
                node.setTitleText("[" + children.getCode() + "]" + children.getName());
                if(depth > 0){
                    node.setFolded(true);
                }
                parentNode.add(node);
                this.appendChildren(node,children,workbook,data,depth + 1);
            }
        }
    }
    @Async
    public void remindSub(TestCategorySub sub){
        String fromMail = Global.getConfig("mail.from");
        MimeMessage message = javaMailSenderImpl.createMimeMessage();
        try {
            TestCategorySub a = subDao.getByCvlId(sub);
                if(a!= null && "1".equals(a.getSubscribe())){
                    TestCategory testCategory = testCategoryDao.get(a.getcId());
                    String userId = a.getUserId();
                    String[] split = userId.split(",");
                    for (String str : split) {
                        User user = userDao.get(str);
                        String email = user.getEmail();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true);
                        //发送方
                        helper.setFrom(fromMail);
                        //接收方
                        helper.setTo(email);
                        //主题
                        helper.setSubject("试验能力更新提示");
                        //邮件内容
                        String content = "您关注的"+testCategory.getName()+"试验能力进行了更新！";
                        helper.setText(content, true);
                        javaMailSenderImpl.send(message);
                    }
                }
        }catch (MessagingException e) {
            e.printStackTrace();
        }



    }


}
