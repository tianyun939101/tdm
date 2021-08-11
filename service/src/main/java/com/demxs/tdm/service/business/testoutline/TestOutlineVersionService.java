package com.demxs.tdm.service.business.testoutline;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.testoutline.TestOutlineVersionDao;
import com.demxs.tdm.domain.business.testoutline.BaseTestOutline;
import com.demxs.tdm.domain.business.testoutline.TestOutlineVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TestOutlineVersionService extends CrudService<TestOutlineVersionDao, TestOutlineVersion> {

    @Autowired
    private BaseTestOutlineService baseTestOutlineService;

    @Transactional(readOnly = false)
    public int changeStatus(TestOutlineVersion testOutlineVersion){
        return super.dao.changeStatus(testOutlineVersion);
    }

    @Transactional(readOnly = false)
    public int changeLocalReport(TestOutlineVersion testOutlineVersion){
        BaseTestOutline baseTestOutline = new BaseTestOutline(testOutlineVersion.getBaseId());
        baseTestOutline.setEditVersion(testOutlineVersion.getId());
        baseTestOutlineService.changeEditVersion(baseTestOutline);
        return super.dao.changeLocalReport(testOutlineVersion);
    }

    @Transactional(readOnly = false)
    public int changeAuditReport(TestOutlineVersion testOutlineVersion){
        BaseTestOutline baseTestOutline = new BaseTestOutline(testOutlineVersion.getBaseId());
        baseTestOutline.setAuditVersion(testOutlineVersion.getId());
        baseTestOutlineService.changeAuditVersion(baseTestOutline);
        return super.dao.changeAuditReport(testOutlineVersion);
    }

    @Transactional(readOnly = false)
    public int changeCurVersion(TestOutlineVersion testOutlineVersion){
        return super.dao.changeCurVersion(testOutlineVersion);
    }

    public List<TestOutlineVersion> findByBaseId(String baseId){
        return super.dao.findByBaseId(baseId);
    }

    public TestOutlineVersion getCurVersionDetail(String id){
        return super.dao.getCurVersionDetail(id);
    }
}
