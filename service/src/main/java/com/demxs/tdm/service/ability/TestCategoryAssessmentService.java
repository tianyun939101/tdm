package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.TestCategoryAssessmentDao;
import com.demxs.tdm.domain.ability.TestCategoryAssessment;
import com.demxs.tdm.domain.ability.TestCategoryLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/9/17 10:34
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryAssessmentService extends CrudService<TestCategoryAssessmentDao, TestCategoryAssessment> {

    @Autowired
    private StandardTestService standardTestService;
    @Autowired
    private EquipmentTestService equipmentTestService;
    @Autowired
    private TestCategoryLabService testCategoryLabService;

    @Transactional(readOnly = true)
    public TestCategoryAssessment getByLabIdAndCId(TestCategoryAssessment assessment){
        return this.dao.getByLabIdAndCId(assessment);
    }

    public List<TestCategoryAssessment> findByVersionId(TestCategoryAssessment assessment){
        return this.dao.findByVersionId(assessment);
    }

    public int apply(TestCategoryAssessment assessment){
        return this.dao.apply(assessment);
    }



    public int deleteByVersionId(TestCategoryAssessment assessment){
        return this.dao.deleteByVersionId(assessment);
    }

    public int deleteByRequestId(TestCategoryAssessment assessment){
        return this.dao.deleteByRequestId(assessment);
    }

    @Override
    public void save(TestCategoryAssessment assessment) {
        /*TestCategoryAssessment record = this.dao.getByLabIdAndCId(assessment).setApplyStatus(TestCategoryAssessment.APPLIED);
        if(null != record){
            assessment.preUpdate();
            assessment.setId(record.getId());
            this.dao.update(assessment);
        }else {
            assessment.preInsert();
            this.dao.insert(assessment);
        }*/
        if(StringUtils.isEmpty(assessment.getId())){
            assessment.preInsert();
            assessment.setId(IdGen.uuid());
            this.dao.insert(assessment);
        }else{
            assessment.preUpdate();
            this.dao.update(assessment);
        }
    }

    public void saveAssessment(TestCategoryAssessment assessment) {
        TestCategoryAssessment record = this.dao.getByLabIdAndCId(assessment.setFilterApplyStatus(TestCategoryAssessment.APPLIED));
        if(null != record){
            assessment.preUpdate();
            assessment.setId(record.getId());
            assessment.setDataStatus(TestCategoryAssessment.UPDATED);
            this.dao.update(assessment);
        }else {
            assessment.preInsert();
            assessment.setDataStatus(TestCategoryAssessment.UPDATED);
            this.dao.insert(assessment);
        }
        //保存标准
        if(assessment.getStandardTests() != null){
            standardTestService.save(assessment.getStandardTests());
        }
        //删除设备
        if(assessment.getStandardDel()!=null){
            standardTestService.deleteAllList(assessment.getStandardDel());
        }
        //保存设备
        if(assessment.getEquipmentTest()!= null){
            equipmentTestService.save(assessment.getEquipmentTest());
        }
        //删除设备
        if(assessment.getEquipmentDel()!=null){
            equipmentTestService.deleteAllList(assessment.getEquipmentDel());
        }
        //更新操作者
        TestCategoryLab lab = new TestCategoryLab(assessment.getcId(),assessment.getvId(),assessment.getLabId());
        lab = testCategoryLabService.getByCvlId(lab);
        if(lab != null){
            lab.setUpdateBy(UserUtils.getUser());
            lab.setUpdateDate(new Date());
            testCategoryLabService.save(lab);
        }
    }

    /**
     * @Describe:获取试验室上一版本能力状态
     * @Author:WuHui
     * @Date:19:17 2020/11/10
     * @param cId
     * @param labId
     * @return:java.lang.String
    */
    public String getPreVersionAbilityStatus( String cId, String labId){
        return this.dao.findPreVersionAbilityStatus(cId,labId);
    }
}
