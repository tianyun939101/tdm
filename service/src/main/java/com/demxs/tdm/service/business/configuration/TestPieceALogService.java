package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.TestPieceALogDao;
import com.demxs.tdm.domain.business.configuration.TestPieceALog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPieceALogService extends CrudService<TestPieceALogDao,TestPieceALog> {

    public List<TestPieceALog> findListByPieceId(String pieceId){
        return dao.findListByPieceId(pieceId);
    }
}
