package com.demxs.tdm.dao.resource.zhishi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识信息操作DAO接口
 * @author 詹小梅
 * @version 2017-06-15
 */
@MyBatisDao
public interface ZhishiXxDao extends CrudDao<ZhishiXx> {

    /**
     * 修改知识状态
     * @param id
     * @param status
     */
    void updateZhishiStatus(@Param("id")String id,@Param("status")String status);


    List<String> getListByCreateBy(@Param("userId")String userId);


    List<ZhishiXx> getZhishixxByFile(@Param("fileId")String fileId);


    Integer countZhishiByCenter(@Param("centerId") String centerId);

    List<ZhishiXx> findZhishiByCenter(@Param("page") Page<ZhishiXx> page, @Param("centerId") String centerId);

    /**
    * @author Jason
    * @date 2020/9/3 13:25
    * @params [zhishiXx]
    * 修改流程类型和图像文件id
    * @return int
    */
    int updateTypeAndImg(ZhishiXx zhishiXx);

    /**
    * @author Jason
    * @date 2020/9/3 18:09
    * @params [zhishiXx]
    * 修改流程类型
    * @return int
    */
    int updateType(ZhishiXx zhishiXx);
}