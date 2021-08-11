package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.StandardTestDao;
import com.demxs.tdm.domain.ability.StandardTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class StandardTestService extends  CrudService<StandardTestDao, StandardTest> {

    @Autowired
    StandardTestDao standardTestDao;


    public Page<StandardTest> list(Page<StandardTest> page, StandardTest standardTest) {
        Page<StandardTest> page1 = super.findPage(page, standardTest);
        return page1;
    }

    public Page<StandardTest> findPage(Page<StandardTest> page, StandardTest entity) {
        entity.setPage(page);
        page.setList(dao.findAllList(entity));
        return page;
    }

    public StandardTest get(String id) {
        StandardTest standardTest = super.dao.get(id);
        return standardTest;
    }


    public void save(StandardTest standardTest) {
        super.save(standardTest);

    }

    /**
     * 批量存储
     * @param standardTestList
     */
    public void save(List<StandardTest> standardTestList) {

        for(StandardTest  standardTest:standardTestList){
            super.save(standardTest);
        }

    }

    /**
     * 批量删除
     * @param StandardTestList
     */
    public void deleteAllList(List<StandardTest> StandardTestList) {

        for(StandardTest  standardTest : StandardTestList){
            standardTestDao.deleteInfo(standardTest);
        }

    }

    /**
     * @Describe:根据评估编号获取标准规范
     * @Author:WuHui
     * @Date:14:23 2020/10/30
     * @param testId
     * @return:java.util.List<com.demxs.tdm.domain.ability.StandardTest>
    */
    public List<StandardTest> getListByTestId(String testId){
        StandardTest stand = new StandardTest();
        stand.setTestId(testId);
        return this.dao.findAllList(stand);
    }

}
