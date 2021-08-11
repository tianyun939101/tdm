package com.demxs.tdm.dao.sys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.sys.LabRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色人员关系DAO接口
 * @author 詹小梅
 * @version 2017-06-26
 */
@MyBatisDao
public interface LabRoleDao extends CrudDao<LabRole> {


    public List<LabInfo> getLabInfosByUserId(@Param("roleId") String roleId,@Param("userId") String userId);

    public List<User> findUserByLabId(@Param("roleId") String roleId, @Param("labId") String labId);


    void deleteByUserId(@Param("userId")String userId,@Param("roleId")String roleId);

    void saveUserLabRole(@Param("userId")String userId,
                         @Param("roleId")String roleId,
                         @Param("labId")String labId);

}