package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.TestCategoryLabDao;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.domain.ability.TestCategory;
import com.demxs.tdm.domain.ability.TestCategoryLab;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessEnum;
import com.demxs.tdm.domain.lab.LabInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/10 09:31
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryLabService extends CrudService<TestCategoryLabDao, TestCategoryLab> {

    @Autowired
    private LabInfoDao labInfoDao;
    @Autowired
    private TestCategoryAttrService testCategoryAttrService;

    public TestCategoryLab getByCvlId(TestCategoryLab lab){
        return this.dao.getByCvlId(lab);
    }

    public List<TestCategoryLab> findByVersionIdAndLabId(TestCategoryLab lab){
        return this.dao.findByVersionIdAndLabId(lab);
    }

    public List<TestCategoryLab> findByVersionId(TestCategoryLab lab){
        return this.dao.findByVersionId(lab);
    }

    public List<TestCategoryLab> findListWithCategoryId(TestCategoryLab lab){
        return this.dao.findListWithCategoryId(lab);
    }

    public int deleteByLabIdAndVersionId(TestCategoryLab lab){
        return this.dao.deleteByLabIdAndVersionId(lab);
    }

    public int deleteByLabIdAndCategoryId(TestCategoryLab lab){
        return this.dao.deleteByLabIdAndCategoryId(lab);
    }

    @Override
    public void save(TestCategoryLab lab) {
        //this.deleteByLabIdAndCategoryId(lab);
        super.save(lab);
    }

    public void addNewLabAbility(List<TestCategoryLab> labList) {
        if(null != labList && !labList.isEmpty()){
            for (TestCategoryLab lab : labList) {
                if(null == this.dao.getByCvlId(lab)){
                    lab.preInsert();
                    //新增固定为未评估状态
                    lab.setAssessStatus(TestCategoryAssessEnum.NOT_EVALUATED.getCode());
                    this.dao.insert(lab);
                }
            }
        }
    }


    /**
     * @Describe:实验室能力编辑
     * @Author:WuHui
     * @Date:9:06 2020/10/13
     * @param lab
     * @return:com.demxs.tdm.domain.ability.TestCategoryLab
    */
    public TestCategoryLab edit(TestCategoryLab lab){
        TestCategoryLab testCategoryLab = this.getByCvlId(lab);
        if(testCategoryLab == null){
            LabInfo labInfo = labInfoDao.get(lab.getLabId());
            if(null != labInfo){
                testCategoryLab = new TestCategoryLab().setLabInfo(labInfo);
                testCategoryLab.setLabId(labInfo.getId());
                testCategoryLab.setcId(lab.getcId());
                testCategoryLab.setvId(lab.getvId());
            }
        }
        return testCategoryLab;
    }

    /**
     * @Describe:获取建设计划做大年度
     * @Author:WuHui
     * @Date:18:56 2020/12/17
     * @param
     * @return:java.lang.Integer
    */
    public Integer getAbilityPlanMaxYear(){
        return this.dao.getAbilityPlanMaxYear();
    }

    /**
     * @Describe:更具版本号获试验室应具备能力
     * @Author:WuHui
     * @Date:14:43 2020/12/19
     * @param testCategoryLab
     * @return:java.util.List<com.demxs.tdm.domain.ability.TestCategoryLab>
    */
    public List<TestCategoryLab> findLabInfoAbilityByVersion(TestCategoryLab testCategoryLab){
        return this.dao.findLabInfoAbilityByVersion(testCategoryLab);
    }
}
