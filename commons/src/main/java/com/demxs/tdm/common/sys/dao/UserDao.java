package com.demxs.tdm.common.sys.dao;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param user
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 根据员工号查询用户
	 * @param user
	 * @return
	 */
	public User getByEmpNo(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);

	/**
	 * 根据OfficeId(当前机构及其所有后代机构)获取用户
	 * @param user
	 * @return
	 */
	public List<User> listUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);


	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 根据参数返回用户列表
	 * @param roleeName 角色英文名
	 * @param officeId 部门ID
	 * @param userName 用户名
	 * @param zizhiids 资质ID集合
	 * @param shifouzzry 是否资质人员（该参数如果为true,则只获取资质人员）
	 * @return List<User>
	 */
	public List<User> searchUserByParam(@Param("ids") String[] ids,
										@Param("roleeName") String roleeName,
										@Param("officeId") String officeId,
										@Param("userName") String userName,
										@Param("zizhiids") String[] zizhiids,
										@Param("shifoury") Boolean shifoury,
										@Param("shifouzzry") Boolean shifouzzry);


	void updateIgnorePassword(User user);


	List<User> findByLabIdAndRoleId(@Param("labId") String labId,@Param("roleId") String roleId);


    @Override
    List<User> joinCollectionByIds(String idStr);

    List<User> getByName(String name);
    User findByName(String name);
	List<User> findByNameList(String name);

    List<User> findAllUserByEnabledLabinfo(User user);


    void   updateDelete(@Param("delFlag") String delFlag,@Param("id") String id);

	void   updateIsDelete(@Param("delFlag") String delFlag,@Param("id") String id);


	User    getUserInfoMess(User user);

	List<User> getUserByRoleName(@Param("roleName") String roleName);

	String  getUserBelongCenter(@Param("name") String name);

	User getUserByLoginName(@Param("loginName") String loginName);
}
