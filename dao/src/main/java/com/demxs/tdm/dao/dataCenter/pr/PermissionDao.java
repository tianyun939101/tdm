package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.Permission;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/4/28 10:00
 * @Description:
 */
@MyBatisDao
public interface PermissionDao extends CrudDao<Permission> {

    /**
    * @author Jason
    * @date 2020/4/28 10:18
    * @params [permission]
    * @Description: 修改不为空的字段
    * @return int
    */
    int updateActive(Permission permission);

    /**
    * @author Jason
    * @date 2020/4/28 10:34
    * @params [id]
    * 根据部门id进行删除
    * @return int
    */
    int deleteByDeptIdAndDirId(Permission permission);

    /**
    * @author Jason
    * @date 2020/4/28 11:25
    * @params [id] 部门id
    * 根据部门id查询出可见文件夹
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.pr.Permission>
    */
    List<Permission> findByDeptId(String deptId);

    /**
    * @author Jason
    * @date 2020/4/28 15:40
    * @params [dirId]
    * 根据文件夹id查询出可见部门
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.pr.Permission>
    */
    List<Permission> findByDirId(Permission permission);

    /**
    * @author Jason
    * @date 2020/5/12 11:23
    * @params [id]
    * 根据部门id查询出可见文件id
    * @return java.util.List<java.lang.String>
    */
    List<String> findDirIdByDeptId(String id);
}
