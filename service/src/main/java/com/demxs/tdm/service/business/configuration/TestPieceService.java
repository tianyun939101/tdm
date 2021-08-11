package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.TestPieceDao;
import com.demxs.tdm.dao.business.configuration.TestPieceTestDao;
import com.demxs.tdm.domain.business.configuration.TestPiceSoftware;
import com.demxs.tdm.domain.business.configuration.TestPiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPieceService extends CrudService<TestPieceDao, TestPiece> {

    @Autowired
    private TestPieceSoftwareService softwareService;

    @Autowired
    private TestPieceDao testDao;

    @Autowired
    private TestPieceTestService testPieceTestService;

    public void softwareSave(TestPiece testPiece){
        softwareService.saveSoftWares(testPiece.getId(),testPiece.getSoftwares());
    }

    public  List<String> getDept(String parentId){
          return testDao.getDept(parentId);
    }

    public List<TestPiece> findAllList(TestPiece testPiece){
        return testDao.findAllList(testPiece);
    }

    public Page<TestPiece> findAllPage(Page<TestPiece> page, TestPiece entity) {
        entity.setPage(page);
        page.setList(testDao.findAllList(entity));
        return page;
    }

    public  Page<TestPiece> findListByDeptId(Page<TestPiece> page, TestPiece entity){
        entity.setPage(page);
        page.setList(testDao.findListByDeptId(entity));
        return page;
    }

    public List<TestPiece> getTestPieceByTestPieceId(String pieceId){
        TestPiece software = new TestPiece();
    //    software.setPieceId(pieceId);
        List<TestPiece> softwares = this.findList(software);
        return softwares;
    }
}
