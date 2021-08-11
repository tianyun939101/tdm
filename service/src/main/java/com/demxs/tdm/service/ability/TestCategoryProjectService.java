package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.TestCategoryProjectDao;
import com.demxs.tdm.domain.ability.TestCategoryProject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/9/29 16:39
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryProjectService extends CrudService<TestCategoryProjectDao, TestCategoryProject> {

    public TestCategoryProject getByLabIdAndCId(TestCategoryProject project){
        return this.dao.getByLabIdAndCId(project);
    }

    public int deleteByLabIdAndCId(TestCategoryProject project){
        return this.dao.deleteByLabIdAndCId(project);
    }

    @Override
    public void save(TestCategoryProject project) {
        if(StringUtils.isNoneBlank(project.getLabId(),project.getcId())){
            this.dao.deleteByLabIdAndCId(project);
        }
        super.save(project);
    }
}
