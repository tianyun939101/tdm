package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.TestPieceSoftwareDao;
import com.demxs.tdm.dao.business.configuration.TestPieceTestDao;
import com.demxs.tdm.domain.business.configuration.TestPiceSoftware;
import com.demxs.tdm.domain.business.configuration.TestPiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPieceTestService extends CrudService<TestPieceTestDao, TestPiceTest> {

    @Autowired
    private  TestPieceTestDao  dao;


    public List<TestPiceTest> getSoftwares(TestPiceTest software){
        List<TestPiceTest> softwares = dao.findList(software);
        return softwares;
    }

    public void  insert(TestPiceTest  test){
        dao.insert(test);
    }

    public  void  delete(TestPiceTest  test){
        dao.delete(test);
    }
    public List<TestPiceTest> getByPieceId(String pieceId){
        return dao.getByPieceId(pieceId);
    }
}
