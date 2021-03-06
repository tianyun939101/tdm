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
     * xmind???????????????????????????
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
    * ???????????????????????????????????????dao?????????
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    public Page<TestCategory> list(TestCategory category, HttpServletRequest request, HttpServletResponse response){
        String no = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        Page<TestCategory> page = new Page<>(request, response);
        boolean isAll = false;
        //?????????????????????????????????????????????????????????????????????
        if(StringUtils.isNumeric(no) && StringUtils.isNumeric(pageSize)){
            //????????????
            category.setPage(page);
        }else{
            isAll = true;
        }
        List<TestCategory> list = this.dao.findModifyUnionTestCategory(category);
        if(null != list){
            for (TestCategory testCategory : list) {
                //?????????????????????????????????????????????
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
    * ?????????????????????
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    /*public Page<TestCategory> list(TestCategory category, HttpServletRequest request, HttpServletResponse response){
        String no = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        if(!StringUtils.isNumeric(no) || !StringUtils.isNumeric(pageSize)){
            return null;
        }else{
            //?????????????????????????????????
            if(StringUtils.isNoneBlank(category.getIsNew(),category.getHasDeleted(),category.getHasModify())){
                return this.packingData(category,request,response);
            }else{
                if(StringUtils.isNotBlank(category.getIsNew())){
                    Page<TestCategory> page = new Page<>(request, response);
                    //?????????modify
                    category.setPage(page);
                    page.setList(this.findNewRecord(category));
                    return page;
                }else if(StringUtils.isNotBlank(category.getHasDeleted()) || StringUtils.isNotBlank(category.getHasModify())){
                    Page<TestCategory> page = new Page<>(request, response);
                    //?????????category
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
    * ????????????
    * @return com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.ability.TestCategory>
    */
    /*public Page<TestCategory> packingData(TestCategory category, HttpServletRequest request, HttpServletResponse response){
        Page<TestCategory> page = new Page<>(request, response);
        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        //?????????????????????
        int newRecordCount = this.findNewRecordCount(category);
        int originalCount = testCategoryService.findLowestLevelNodeWithModifyCount(category);
        category.setPage(page);
        List<TestCategory> original = testCategoryService.findLowestLevelNodeWithModify(category);
        category.setPage(new Page<>(pageNo,pageSize));
        List<TestCategory> newRecord = this.findNewRecord(category);
        this.appendParent(newRecord);
        if(newRecordCount < pageNo * pageSize){
            //?????????????????????????????????????????????
            if(newRecord.size() == pageSize){
                //????????????
                newRecord = original;
            }else {
                //???????????????????????????????????????????????????
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
    * ????????????
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
    * ????????????
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
                            //????????????
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
            //?????????????????????????????????
            this.dao.disabledModifyByCvrId(modify);
        }else if(StringUtils.isNotBlank(modify.getcId())){
            //????????????
            List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(modify.getCode()).setvId(modify.getvId()));
            if((null != list)){
                for (TestCategory temp : list) {
                    if(!modify.getcId().equals(temp.getId())){
                        throw new ServiceException("?????????????????????"+modify.getCode());
                    }
                }
            }
            List<TestCategoryModify> list1 = this.findByCodeAndRequestId(new TestCategoryModify().setCode(modify.getCode()).setrId(modify.getrId()));
            if((null != list1)){
                for (TestCategoryModify temp : list1) {
                    if(!modify.getcId().equals(temp.getcId())){
                        throw new ServiceException("?????????????????????"+modify.getCode());
                    }
                }
            }
            TestCategory parent = testCategoryDao.get(modify.getParentId());
            //???testCategory??????????????????????????????????????????????????????????????????
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
            //????????????
            modifyRecord.setInfo(this.getDifference(modify));
        }else{
            //????????????
            List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(modify.getCode()).setvId(modify.getvId()));
            if(null != list && !list.isEmpty()){
                throw new ServiceException("?????????????????????"+modify.getCode());
            }
            List<TestCategoryModify> list1 = this.findByCodeAndRequestId(new TestCategoryModify().setCode(modify.getCode()).setrId(modify.getrId()));
            if(null != list1 && !list1.isEmpty()){
                throw new ServiceException("?????????????????????"+modify.getCode());
            }
            TestCategory testCategory = testCategoryDao.get(modify.getParentId());
            if(null != testCategory){
                modify.setParentId(testCategory.getId());
                modify.setParentIds(testCategory.getParentIds() + testCategory.getId() + ",");
            }else{
                if(StringUtils.isNotBlank(modify.getParentId())){
                    //???????????????????????????????????????????????????
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
            //????????????
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
    * ??????????????????
    * @return java.lang.String
    */
    private String getDifference(TestCategoryModify modify){
        StringBuilder difference = new StringBuilder();
        if(StringUtils.isNotBlank(modify.getcId())){
            //??????
            TestCategory testCategory = testCategoryDao.get(modify.getcId());
            if(null != testCategory.getCode() && !testCategory.getCode().equals(modify.getCode())){
                difference.append("????????????[").append(testCategory.getCode()).append("] -> ????????????[")
                        .append(modify.getCode()).append("]???");
            }else{
                difference.append("?????????[").append(modify.getCode()).append("]???");
            }
            if(null != testCategory.getName() &&  !testCategory.getName().equals(modify.getName())){
                difference.append("????????????[").append(testCategory.getName()).append("] -> ????????????[")
                        .append(modify.getName()).append("]???");
            }else{
                difference.append("?????????[").append(modify.getName()).append("]???");
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
                    difference.append("????????????[").append(parent.getCode()).append(parent.getName()).append("]???");
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
                            difference.append("????????????[").append(parent.getCode())
                                    .append(parent.getName()).append("] -> ????????????[")
                                    .append(newParent.getCode()).append(newParent.getName())
                                    .append("]???");
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
                    difference.append("????????????????????????[").append(originalValue).append("] ->????????????????????????[")
                            .append(dictValue).append("]");
                }else{
                    difference.append("?????????????????????[").append(dictValue).append("]???");
                }
            }
            if(null != testCategory.getStandard() && !testCategory.getStandard().equals(modify.getStandard())){
                difference.append("??????????????????[").append(testCategory.getStandard()).append("] -> ??????????????????[")
                        .append(modify.getStandard()).append("]???");
            }else{
                difference.append("???????????????[").append(modify.getStandard()).append("]???");
            }
            if(null != testCategory.getRemarks() && !testCategory.getRemarks().equals(modify.getRemarks())){
                difference.append("????????????[").append(testCategory.getRemarks()).append("] -> ????????????[")
                        .append(modify.getRemarks()).append("]???");
            }else{
                difference.append("?????????[").append(modify.getRemarks()).append("]???");
            }
            difference.deleteCharAt(difference.length() - 1);
        }else{
            //??????
            if(StringUtils.isNotBlank(modify.getCode())){
                difference.append("?????????[").append(modify.getCode()).append("]???");
            }
            if(StringUtils.isNotBlank(modify.getName())){
                difference.append("?????????[").append(modify.getName()).append("]???");
            }
            if(StringUtils.isNotBlank(modify.getParentId())){
                TestCategory parent = testCategoryDao.get(modify.getParentId());
                if(null != parent){
                    difference.append("???????????????[").append(parent.getCode()).append("]???");
                    difference.append("???????????????[").append(parent.getName()).append("]???");
                }else{
                    TestCategoryModify _parent = this.get(modify.getParentId());
                    if (_parent != null) {
                        difference.append("???????????????[").append(_parent.getCode()).append("]???");
                        difference.append("???????????????[").append(_parent.getName()).append("]???");
                    }
                }
            }
            if(StringUtils.isNotBlank(modify.getAssessStatus())){
                String dictValue = DictUtils.getDictLabel(modify.getAssessStatus(),ASSESS_STATUS_KEY, "");
                difference.append("?????????????????????[").append(dictValue).append("]???");
            }
            if(StringUtils.isNotBlank(modify.getStandard())){
                difference.append("???????????????[").append(modify.getStandard()).append("]???");
            }
            if(StringUtils.isNotBlank(modify.getRemarks())){
                difference.append("?????????[").append(modify.getRemarks()).append("]???");
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
    * ?????????????????????????????????
    * @return void
    */
    @Transactional(readOnly = false)
    public void apply(TestCategoryModify categoryModify){
        //TestCategoryModify originalModify = categoryModify;
        //categoryModify = this.get(categoryModify.getId());
        TestCategory record = new TestCategory();
        if(categoryModify != null && TestCategoryModify.NOT_APPLIED.equals(categoryModify.getStatus())){
            if(categoryModify.isAddAction()){
                //????????????
                List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(categoryModify.getCode()).setvId(categoryModify.getvId()));
                if(null != list && !list.isEmpty()){
                    throw new ServiceException("?????????????????????"+categoryModify.getCode());
                }
                record.setCode(categoryModify.getCode());
                record.setName(categoryModify.getName());
                record.setStandard(categoryModify.getStandard());
                record.setRemarks(categoryModify.getRemarks());
                record.setParentIds(categoryModify.getParentIds());
                record.setParent(new TestCategory(categoryModify.getParentId()));
                record.setIsNewRecord(true);
                //??????categoryModify???id??????????????????????????????????????????
                record.setId(categoryModify.getId());
                record.setvId(categoryModify.getvId());
                testCategoryService.save(record);
                TestCategoryLab categoryLab = new TestCategoryLab();
                categoryLab.setLabId(categoryModify.getLabId()).setcId(record.getId());
                categoryLab.setvId(categoryModify.getvId());
                categoryLab.setAssessStatus(categoryModify.getAssessStatus());
                categoryLabService.save(categoryLab);
            }else if(categoryModify.isToDeleted()){
                //????????????
                record.setId(categoryModify.getcId());
                testCategoryService.delete(record);
                //????????????????????????
                categoryLabService.deleteByLabIdAndCategoryId(new TestCategoryLab()
                        .setLabId(categoryModify.getLabId()).setcId(categoryModify.getcId()));
            }else{
                //????????????
                List<TestCategory> list = testCategoryService.findByCode(new TestCategory().setCode(categoryModify.getCode()).setvId(categoryModify.getvId()));
                if((null != list)){
                    for (TestCategory temp : list) {
                        if(!categoryModify.getcId().equals(temp.getId())){
                            throw new ServiceException("?????????????????????"+categoryModify.getCode());
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
                //????????????????????????????????????
                categoryLabService.deleteByLabIdAndCategoryId(categoryLab.setLabId(categoryModify.getLabId())
                        .setcId(categoryModify.getcId()));
                categoryLab.setvId(categoryModify.getvId());
                categoryLab.setAssessStatus(categoryModify.getAssessStatus());
                categoryLabService.save(categoryLab);
            }
            //???????????????????????????????????????
            this.changeModifyStatus(categoryModify.setStatus(TestCategoryModify.APPLIED));
            //this.delete(categoryModify);
        }
        /*else{
            //??????????????????????????????????????????
            TestCategory testCategory = testCategoryDao.get(originalModify.getId());
            TestCategoryModify modify = new TestCategoryModify().setcId(testCategory.getId());
            List<TestCategoryModify> modifyList = this.findList(modify);
            if(null != modifyList){
                //???????????????????????????????????????
                for (TestCategoryModify testCategoryModify : modifyList) {
                    if(null != testCategoryModify && testCategoryModify.isToDeleted()){
                        this.apply(testCategoryModify);
                        return;
                    }
                }
                //???????????????????????????
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
        //???????????????
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
    * ????????????????????????????????????????????????????????????
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
    * ???????????????
    * @return void
    */
    public void rejectModify(TestCategoryModify modify) {
        this.changeModifyStatus(modify.setStatus(TestCategoryModify.REJECT));
    }

    /**
    * @author Jason
    * @date 2020/8/12 9:34
    * @params [modify]
    * ????????????
    * @return void
    */
    public void reSubMit(TestCategoryModify modify) {
        this.changeModifyStatus(modify.setStatus(TestCategoryModify.NOT_APPLIED));
    }

    public void changeModifyStatus(TestCategoryModify modify){
        if(StringUtils.isBlank(modify.getId())){
            throw new ServiceException("??????????????????");
        }
        this.dao.changeStatus(modify);
        TestCategoryModifyRecord record = modifyRecordDao.getByModifyId(new TestCategoryModifyRecord().setmId(modify.getId()));
        record.setStatus(modify.getStatus());
        modifyRecordDao.changeStatus(record);
        List<TestCategoryModify> childrenList = this.dao.findByParentIdLike(new TestCategoryModify().setParentId(modify.getId()));
        //??????????????????????????????
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
    * ????????????????????????????????????????????????????????????????????????????????????
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
    * ?????????????????????????????????????????????????????????????????????????????????
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
    * ?????????????????????????????????????????????????????????????????????????????????????????????
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
    * ???????????????????????????????????????????????????????????????????????????????????????????????????
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
    * ???????????????
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
    * ?????????????????????????????????
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
    * ??????????????????????????????????????????????????????????????????
    * @return java.lang.String
    */
    public String getNextCodeInVersionModify(TestCategoryModify modify){
        if(StringUtils.isNotBlank(modify.getParentId())){
            //???????????????????????????????????????????????????????????????????????????
            List<TestCategoryModify> sameList = this.dao.findByParentId(modify.setIsNew("1"));
            if(sameList == null || sameList.isEmpty()){
                TestCategoryModify parent = this.dao.get(modify.getParentId());
                //?????????????????????????????????
                if(parent != null){
                    sameList = this.dao.findByParentId(modify.setIsNew("1"));
                    //??????????????????????????????????????????????????????code
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
                            //????????????????????????
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
                throw new ServiceException("????????????????????????");
            }
            String labId = lab.get(0).getLabId();
            //??????????????????
            for (TestCategoryAttr attr : attrList) {
                attr.setLabId(labId);
                TestCategoryAttr a = attrDao.getByCvlId(attr);
                //????????????????????? ??????????????????
                if(null == a){
                    attr.preInsert();
                    attrDao.insert(attr);
                }else{
                    attr.preUpdate();
                    attr.setId(a.getId());
                    attrDao.update(attr);
                }
                //??????????????????????????????
                TestCategoryLab testCategoryLab = new TestCategoryLab(attr.getcId(),attr.getvId(),attr.getLabId());
                testCategoryLab = categoryLabService.getByCvlId(testCategoryLab);
                if(testCategoryLab == null && attr.getAttribute().equals("1")){
                    testCategoryLab = new TestCategoryLab(attr.getcId(),attr.getvId(),attr.getLabId());
                    TestCategoryAssessEnum status = TestCategoryAssessEnum.A;
                    testCategoryLab.setAssessStatus(status.getCode());
                    categoryLabService.save(testCategoryLab);
                    //?????????????????????
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
     * ????????????
     * @param attrList
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void batchSaveSubAbility(List<TestCategorySub> attrList) {
        if(null != attrList && !attrList.isEmpty()){
            User curUser = UserUtils.getUser();
            String userId = curUser.getId();
            List<LabUser> lab = labUserDao.findByUserId(new LabUser().setUserId(userId));
            if(null == lab || lab.isEmpty()){
                throw new ServiceException("????????????????????????");
            }
            String labId = lab.get(0).getLabId();
            //??????????????????
            for (TestCategorySub sub : attrList) {
                sub.setLabId(labId);
                TestCategorySub a = subDao.getByCvlId(sub);
                //????????????????????? ??????????????????
                if(null == a){
                    sub.setUserId(userId);
                    sub.preInsert();
                    subDao.insert(sub);
                }else{
                    sub.preUpdate();
                    sub.setId(a.getId());
                    subDao.update(sub);
                }
                //??????????????????????????????
                TestCategoryLab testCategoryLab = new TestCategoryLab(sub.getcId(),sub.getvId(),sub.getLabId());
                testCategoryLab = categoryLabService.getByCvlId(testCategoryLab);
                if(testCategoryLab == null && sub.getSubscribe().equals("1")){
                    testCategoryLab = new TestCategoryLab(sub.getcId(),sub.getvId(),sub.getLabId());
                    TestCategoryAssessEnum status = TestCategoryAssessEnum.A;
                    testCategoryLab.setAssessStatus(status.getCode());
                    categoryLabService.save(testCategoryLab);
                    //?????????????????????
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
    * ??????xmind????????????
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
                rootNode.setTitleText("????????????");
                logger.info("??????????????????xmind??????");
                for (TestCategory testCategory : data) {
                    //??????????????????
                    if(testCategory.getParent() == null && ROOT_PARENT_ID.equals(testCategory.getParentIds())){
                        ITopic primaryNode = workbook.createTopic();
                        primaryNode.setTitleText("[" + testCategory.getCode() + "]" + testCategory.getName());
                        rootNode.add(primaryNode);
                        this.appendChildren(primaryNode,testCategory,workbook,data,0);
                    }
                }
                workbook.save(response.getOutputStream());
                logger.info("??????????????????xmind??????");
            }else{
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition","attachment; filename="+new String(("???????????????.txt").getBytes("gb2312"), "ISO8859-1"));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("??????????????????xmind?????????" + e.getMessage());
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
                        //?????????
                        helper.setFrom(fromMail);
                        //?????????
                        helper.setTo(email);
                        //??????
                        helper.setSubject("????????????????????????");
                        //????????????
                        String content = "????????????"+testCategory.getName()+"??????????????????????????????";
                        helper.setText(content, true);
                        javaMailSenderImpl.send(message);
                    }
                }
        }catch (MessagingException e) {
            e.printStackTrace();
        }



    }


}
