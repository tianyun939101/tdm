package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.EquipmentTestDao;
import com.demxs.tdm.domain.ability.EquipmentTest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EquipmentTestService extends  CrudService<EquipmentTestDao, EquipmentTest> {

    @Autowired
    EquipmentTestDao equipmentTestDao;



    public Page<EquipmentTest> list(Page<EquipmentTest> page, EquipmentTest equipmentTest) {
        Page<EquipmentTest> Page = super.findPage(page, equipmentTest);
        return Page;
    }

    public Page<EquipmentTest> findPage(Page<EquipmentTest> page, EquipmentTest entity) {
        entity.setPage(page);
        page.setList(dao.findAllList(entity));
        return page;
    }

    public EquipmentTest get(String id) {
        EquipmentTest equipmentTest = super.dao.get(id);
        return equipmentTest;
    }

    public List<EquipmentTest> getEquipmentTest(String testId) {
        List<EquipmentTest> equipmentTest = equipmentTestDao.getEquipmentTest(testId);
        return equipmentTest;
    }


    public void save(EquipmentTest equipmentTest) {

        super.save(equipmentTest);

    }

    public void save(List<EquipmentTest> equipmentTestList) {

        for(EquipmentTest  equipmentTest:equipmentTestList){
            super.save(equipmentTest);
        }

    }

    public void deleteAllList(List<EquipmentTest> equipmentTestList) {
        for(EquipmentTest  equipmentTest:equipmentTestList){
            if(StringUtils.isNotEmpty(equipmentTest.getId())){
                equipmentTestDao.deleteInfo(equipmentTest);

            }
        }

    }

    /**
     * @Describe:获取评估设备并分组
     * @Author:WuHui
     * @Date:10:49 2020/11/3
     * @param
     * @return:java.util.Map<java.lang.String,java.util.List<com.demxs.tdm.domain.ability.EquipmentTest>>
    */
    public Map<String,List<EquipmentTest>> groupEquipmentByTestId(){
        Map<String,List<EquipmentTest>> data = new HashMap<>();
        List<EquipmentTest> list = equipmentTestDao.findAllEquipment();
        for(EquipmentTest eq : list){
            if(data.get(eq.getTestId()) == null){
                data.put(eq.getTestId(),new ArrayList<>());
            }
            data.get(eq.getTestId()).add(eq);
        }
        return data;
    }

    /**
     * @Describe:根据能力评估编号获取关联设备
     * @Author:WuHui
     * @Date:10:34 2020/12/17
     * @param testId
     * @return:java.util.List<com.demxs.tdm.domain.ability.EquipmentTest>
    */
    public List<EquipmentTest> getEquipmentTestByTestId(String testId){
        EquipmentTest equipmentTest = new EquipmentTest();
        equipmentTest.setTestId(testId);
        return this.dao.findAllList(equipmentTest);
    }
}
