package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.Directory;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/4/28 09:59
 * @Description:
 */
@MyBatisDao
public interface DirectoryDao extends TreeDao<Directory> {

    /**
    * @author Jason
    * @date 2020/4/28 10:29
    * @params [directory]
    * 修改不为空的字段
    * @return int
    */
    int updateActive(Directory directory);

    /**
    * @author Jason
    * @date 2020/5/12 11:26
    * @params [directory]
    * 查询文件夹
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.pr.Directory>
    */
    List<Directory> findDirList(Directory directory);

    /**
    * @author Jason
    * @date 2020/5/12 11:26
    * @params [directory]
    * 查询文件
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.pr.Directory>
    */
    List<Directory> findFileList(Directory directory);
}
