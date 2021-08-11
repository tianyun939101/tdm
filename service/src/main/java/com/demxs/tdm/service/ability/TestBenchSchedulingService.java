package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.ability.TestBenchSchedulingDao;
import com.demxs.tdm.domain.ability.TestBenchScheduling;
import org.springframework.stereotype.Service;


@Service
public class TestBenchSchedulingService extends CrudService<TestBenchSchedulingDao, TestBenchScheduling> {

    public Page<TestBenchScheduling> findPage1(Page<TestBenchScheduling> page, TestBenchScheduling entity) {
        entity.setPage(page);
        page.setList(dao.findCount(entity));
        return page;
    }



}
