package com.demxs.tdm.common.sys.dao;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.Office;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
    //根据 parentId 获取子机构
    public List<Office> findChildrenByParentId(Office office);
    List<Office> findChildrenByParentIdOne(@Param("parentId") String parentId);
    public List<Office> findAllListMine(Office office);

    Office getByOfficeCode(Office office);

    /**
    * @author Jason
    * @date 2020/7/31 14:05
    * @params [office]
    * 根据名称模糊查询
    * @return java.util.List<com.demxs.tdm.common.sys.entity.Office>
    */
    List<Office> findListByNameIfExist(Office office);
    List<Office> findListByNameIfExistOne(@Param("name") String name);
    String findID(String name);

    List<String> getOfficeName();
    List<Office> getOfficeNameList();
}
