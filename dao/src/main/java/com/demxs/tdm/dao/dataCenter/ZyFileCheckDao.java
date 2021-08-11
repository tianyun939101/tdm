package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyFileAttribute;
import com.demxs.tdm.domain.dataCenter.ZyFileCheck;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ZyFileCheckDao extends CrudDao<ZyFileCheck> {

        List<String>  getIsExist(String fileId);

        //获取每月通过科室分类后的检查级别
        List<Map<String,String>>  getSumByOfficeAndLevel();

        //获取每月通过检查级别总和
        List<Map<String,String>>  getTotalByOfficeAndLevel();

        //获取每月通过科室分类后的文件类别
        List<Map<String,String>>  getSumByOfficeAndType();

        //获取每月通过文件类别总和
        List<Map<String,String>>  getTotalByOfficeAndType();


        List<ZyFileCheck> findDataList(ZyFileCheck zyFileCheck);

        List<Map<String,String>> getSumByOfficeAndContent();


        List<Map<String,String>> getTotalByOfficeAndContent();


}