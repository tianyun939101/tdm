package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.TestCategoryModifyRecordDao;
import com.demxs.tdm.domain.ability.TestCategoryModifyRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/4 16:04
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryModifyRecordService extends CrudService<TestCategoryModifyRecordDao, TestCategoryModifyRecord> {

    public List<TestCategoryModifyRecord> findByRequestId(String rId){
        return this.dao.findByRequestId(rId);
    }

    public List<TestCategoryModifyRecord> findByCategoryId(String cId){
        return this.dao.findByCategoryId(cId);
    }
    public TestCategoryModifyRecord getByModifyId(TestCategoryModifyRecord modifyRecord){
        return this.dao.getByModifyId(modifyRecord);
    }

    public int changeStatus(TestCategoryModifyRecord record){
        return this.dao.changeStatus(record);
    }

    public int deleteByRequestId(TestCategoryModifyRecord record){
        return this.dao.deleteByRequestId(record);
    }

    public int deleteByCategoryId(TestCategoryModifyRecord record){
        return this.dao.deleteByCategoryId(record);
    }

    public int deleteByModifyId(TestCategoryModifyRecord record){
        return this.dao.deleteByModifyId(record);
    }

    public int deleteByRequestAndCategoryId(TestCategoryModifyRecord record){
        return this.dao.deleteByRequestAndCategoryId(record);
    }
}
