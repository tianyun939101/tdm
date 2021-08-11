package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.dao.dataCenter.TestInfoAttrValueDao;
import com.demxs.tdm.domain.dataCenter.DataTestInfo;
import com.demxs.tdm.domain.dataCenter.TestInfoAttrValue;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuhui
 * @date 2020/10/14 14:50
 **/
@Service
public class TestInfoAttrValueService extends CrudService<TestInfoAttrValueDao, TestInfoAttrValue> {

    @Autowired
    private TestInfoAttrValueDao testInfoAttrValueDao;


    /**
     * @Describe:批量存储测试任务属性值
     * @Author:WuHui
     * @Date:9:20 2020/10/16
     * @param testInfo
     * @return:void
    */
    public void saveAttrValues(DataTestInfo testInfo){
        List<TestInfoAttrValue> values = testInfo.getFields();
        User user = UserUtils.getUser();
        String testInfoId = "";
        if(!CollectionUtils.isEmpty(values)){
            //新增属性值
            for(TestInfoAttrValue value:values){
                //获取旧测试任务编号
                testInfoId = value.getTestInfoId();
                value.setId(IdGen.uuid());
                value.setTestInfoId(testInfo.getId());
                value.setCreateBy(user);
                value.setUpdateBy(user);
                testInfoAttrValueDao.insert(value);
            }
            //testInfoAttrValueDao.inserts(values);
            //删除属性
            testInfoAttrValueDao.deleteByTestInfoId(testInfoId);
        }

    }
}
