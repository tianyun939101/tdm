package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.ability.TestCategoryAttrDao;
import com.demxs.tdm.domain.ability.TestCategoryAttr;
import com.demxs.tdm.domain.lab.LabUser;
import com.demxs.tdm.service.lab.LabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/*
 * @Describe:
 * @Author:WuHui
 * @Date:15:52 2020/9/5
*/
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestCategoryAttrService extends CrudService<TestCategoryAttrDao, TestCategoryAttr> {
    @Autowired
    private LabUserService labUserService;

    @Override
    public TestCategoryAttr get(String id) {
        return super.get(id);
    }

    @Override
    public TestCategoryAttr get(TestCategoryAttr entity) {
        return super.get(entity);
    }

    @Override
    public List<TestCategoryAttr> findList(TestCategoryAttr entity) {
        return super.findList(entity);
    }

    @Override
    public Page<TestCategoryAttr> findPage(Page<TestCategoryAttr> page, TestCategoryAttr entity) {
        return super.findPage(page, entity);
    }

    public int deleteByVersionId(TestCategoryAttr attr){
        return this.dao.deleteByVersionId(attr);
    }

    @Override
    public void save(TestCategoryAttr entity) {
        super.save(entity);
    }

    @Override
    public void delete(TestCategoryAttr entity) {
        super.delete(entity);
    }

    @Transactional
    public void batchSave(List<TestCategoryAttr> testCategoryAttrs){
        User user = UserUtils.getUser();
        LabUser labUser = new LabUser();
        labUser.setUserId(user.getId());
        List<LabUser> labUsers = labUserService.findByUserId(labUser);
        if(!CollectionUtils.isEmpty(labUsers)){
            labUser = labUsers.get(0);
            for(TestCategoryAttr testCategoryAttr : testCategoryAttrs){
                if(StringUtils.isEmpty(testCategoryAttr.getLabId())){
                    testCategoryAttr.setLabId(labUser.getLabId());
                }
                super.save(testCategoryAttr);
            }
        }
    }

    public List<TestCategoryAttr> findAttrByCategoryId(TestCategoryAttr testCategoryAttr) {

        return this.dao.findAttrByCategoryId(testCategoryAttr);
    }

    public TestCategoryAttr getByCvlId(TestCategoryAttr testCategoryAttr){
        return dao.getByCvlId(testCategoryAttr);
    }
}
