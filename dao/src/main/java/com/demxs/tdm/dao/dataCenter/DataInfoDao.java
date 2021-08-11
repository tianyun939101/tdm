package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataInfo;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * 数据中心数据DAO
 */
@MyBatisDao
public interface DataInfoDao extends CrudDao<DataInfo> {

    void deleteByTestId(@Param("testId") String testId);

    List<DataInfo> getByTestId(@Param("testId") String testId);

    List<DataInfo> getDataList(@Param("name") String name);
    
    /**
     * @Describe:根据产品进行数据统计
     * @Author:WuHui
     * @Date:18:13 2020/9/1
     * @param 
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> countByProductModel();
    /**
     * @Describe:根据实验类型进行数据统计
     * @Author:WuHui
     * @Date:18:14 2020/9/1
     * @param 
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> countByTestNature();
    
    /**
     * @Describe:根据条件查询数据
     * @Author:WuHui
     * @Date:18:14 2020/9/1
     * @param page
     * @param dataInfo
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.DataInfo>
    */
    List<DataInfo> findListByCondition(@Param("page") Page<DataInfo> page, @Param("dataInfo")DataInfo dataInfo);

    /**
     * @Describe:测试数据上传量统计
     * @Author:WuHui
     * @Date:15:06 2020/9/2
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    List<Map<String,String>> countTestData();
}