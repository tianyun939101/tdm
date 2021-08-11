package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataBackUpFile;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface DataBackUpFileDao extends CrudDao<DataBackUpFile> {

    //获取当前相差的天数
    String  getDateDay(@Param("backTime") String  backTime);

    //获取当前相差的月数
    String  getDateMonth(@Param("backTime")String  backTime);


    void insertBack(DataBackUpFile dataBackUpFile);

}