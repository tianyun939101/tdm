package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.TestPieceDao;
import com.demxs.tdm.dao.business.configuration.TestPieceSoftwareDao;
import com.demxs.tdm.domain.business.configuration.TestPiceSoftware;
import com.demxs.tdm.domain.business.configuration.TestPiece;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPieceSoftwareService extends CrudService<TestPieceSoftwareDao, TestPiceSoftware> {

    void saveSoftWares(String pieceId, List<TestPiceSoftware> softwares){
        for(TestPiceSoftware software:softwares){
            this.save(software);
        }
    }

    public List<TestPiceSoftware> getSoftwares(TestPiceSoftware software){
        List<TestPiceSoftware> softwares = this.findList(software);
        return softwares;
    }

    public List<TestPiceSoftware> getSoftwaresByTestPieceId(String pieceId){
        TestPiceSoftware software = new TestPiceSoftware();
        software.setPieceId(pieceId);
        List<TestPiceSoftware> softwares = this.findList(software);
        return softwares;
    }
}
