package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUpFile;
import com.demxs.tdm.domain.dataCenter.ZyFileAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface ZyFileAttributeDao extends CrudDao<ZyFileAttribute> {


    List<ZyFileAttribute> getFileByFileId(String fileId);

    //获取否的检查数据
    List<ZyFileAttribute> getFileIsByFileId(String fileId);


    //获取部门下数据
    List<ZyFileAttribute> getFileIsByFileIdAndOffcie(String officeId);
}