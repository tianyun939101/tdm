package com.demxs.tdm.service.business.testoutline;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.testoutline.BaseTestOutlineDao;
import com.demxs.tdm.domain.business.testoutline.BaseTestOutline;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BaseTestOutlineService extends CrudService<BaseTestOutlineDao, BaseTestOutline> {

    @Transactional(readOnly = false)
    public int changeEditVersion(BaseTestOutline baseTestOutline){
        return super.dao.changeEditVersion(baseTestOutline);
    }

    @Transactional(readOnly = false)
    public int changeAuditVersion(BaseTestOutline baseTestOutline){
        return super.dao.changeAuditVersion(baseTestOutline);
    }

    @Transactional(readOnly = false)
    public int updateBase(BaseTestOutline baseTestOutline){
        return super.dao.updateBase(baseTestOutline);
    }

    public List<BaseTestOutline> findCurVersionDetail(BaseTestOutline baseTestOutline){
        return super.dao.findCurVersionDetail(baseTestOutline);
    }

}
